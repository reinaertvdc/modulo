app.controller('StudentTasksController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    const SCORES_LIST_ITEM_PREFIX = 'task-list-item-';
    const SCORES_LIST_ELEMENT = document.getElementById('table-scores-list-body');

    $scope.scores = new Map();
    $scope.originalScores = new Map();
    $scope.removeId;

    $scope.searchScores = function () {
        // make $scope.scores the original scores
        $scope.scores = new Map($scope.originalScores);
        $scope.originalScores.forEach(function (item) {
            $scope.addScore(item.scoreObj);
        });

        if ($scope.searchKeyword) {
            var search = $scope.searchKeyword.toLowerCase();

            // remove all the tasks that don't match
            $scope.scores.forEach(function (item) {

                if (item.scoreObj.task.name.toLowerCase().indexOf(search) < 0)
                    $scope.removeTaskFrontend(item.scoreObj.id);
            });
        }
        $scope.refresh();
    };

    $scope.toElementId = function (id) {
        return SCORES_LIST_ITEM_PREFIX + id;
    };

    $scope.addScore = function (score) {
        $scope.removeTaskFrontend(score.id);
        var scoreObj = {'scoreObj': score, 'opened': false, 'fd': null, 'filenameStr': ''};
        $scope.scores.set(score.id, scoreObj);

        var html = '<tr id="' + $scope.toElementId(score.id) + '"></td>';

        // check if description set
        if (score.task.description == null || score.task.description == "")
            html += '<td></td>';
        else
            html += '<td><span role="button" ng-class="getOpenedClass(' + score.id + ')" ng-click="scores.get(' + score.id + ').opened = !scores.get(' + score.id + ').opened""></span></td>';

        html += '<td>' + score.task.name + '</td>' +
            '<td>' + score.task.clazz.name + '</td>';

        // check upload and deadline
        if (score.fileName == null && (new Date() < new Date(score.task.deadline)))
            html += '<td class="text-danger"><strong>' + score.task.deadline + '</strong></td>';
        else
            html += '<td>' + score.task.deadline + '</td>';

        // upload
        if (score.fileName == null || score.fileName == '') {
            html += '<td><div class="row"> ' +
                '<em class="col-xs-7 text-muted">{{getFilenameStr(' + score.id + ')}}</em>';
            if ($cookies.getObject("user").role === UserType.STUDENT  &&  new Date() < new Date(score.task.deadline)) {  // before deadline -> enable 'upload'
                html += '<span class="col-xs-2" align="center" input-group-btn"><span class=" btn btn-primary btn-file btn-sm">•••' +
                    '<input type="file" onchange="angular.element(this).scope().setFile(' + score.id + ',this.files[0])"/></span></span>' +
                    '<span role="button" ng-show="isVisible(' + score.id + ')" class="text-info glyphicon glyphicon-upload col-xs-2" ng-click="uploadFile(' + score.id + ')"></span>';
            }
            html += '</div></td>';
        }
        // download
        else {
            html += '<td><div class="row"> ' +
                '<em class="col-xs-7 text-muted">' + score.fileName + '</em>' +
                '<span align="center" role="button" class="text-info glyphicon glyphicon-download col-xs-2" ng-click="downloadFile(' + score.id + ')"></span>';
            if ($cookies.getObject("user").role === UserType.STUDENT  &&  new Date() < new Date(score.task.deadline))  // before deadline -> enable 'remove'
                html += '<span role="button" class="text-danger glyphicon glyphicon-remove col-xs-2" ng-click="openRemoveModal(' + score.id + ')"></span>';
            html += '</div></td>';
        }

        // check if score already set
        if (score.score == null)
            html += '<td>' + "" + '</td>';
        else if (score.remarks == null || score.remarks == "")
            html += '<td align="center">' + score.score + '</td>';
        else
            html += '<td align="center"><div tooltip-class="customClass" tooltip-placement="top" uib-tooltip="Commentaar:\n' + score.remarks + '">' + score.score + '</div></td>';

        // task description
        if (score.task.description != null && score.task.description != "")
            html += '<tr id="' + $scope.toElementId(score.id) + '"></tr><tr id="' + $scope.toElementId(score.id) + '" ng-show="scores.get(' + score.id + ').opened"><td></td><td colspan="5"><pre>' + score.task.description + '</pre></td></tr>';

        html += '</tr>';
        var element = document.createElement('tr');
        SCORES_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(SCORES_LIST_ELEMENT)($scope);
    };

    $scope.getOpenedClass = function (id) {
        if($scope.scores.get(id) == undefined)
            return;
        if ($scope.scores.get(id).opened)
            return 'glyphicon glyphicon-chevron-down';
        else
            return 'glyphicon glyphicon-chevron-right'
    };


    $scope.downloadFile = function (id) {
        $scope.execDownload($scope.SERVER_ADDRESS + 'task/download/' + id,
            $scope.scores.get(id).scoreObj.fileName);
    };

    $scope.openRemoveModal = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/remove_modal.html',
            controller: 'RemoveUploadModalInstanceCtrl',
            resolve: {}
        });
        $scope.removeId = id;
        modalInstance.result.then(function () {
            $scope.removeUploadBackend($scope.removeId)
        });
    };

    $scope.removeUploadBackend = function (id) {
        $http({
            method: 'PUT', url: $scope.SERVER_ADDRESS + 'task/score/' + id + '/reset',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $window.location.reload(true);
            $scope.createAlertCookie('Upload verwijderd.');
        }).error(function (response) {
            $scope.createAlertCookie('Upload verwijderen mislukt.', "danger");
        });
    };


    $scope.setFile = function (id, file) {
        // if 'cancel' clicked
        if (typeof file === 'undefined') {
            $scope.scores.get(id).fd = null;
            $scope.$apply(function () {
                $scope.scores.get(id).filenameStr = '';
            });
            return;
        }

        // if file selected
        var fd = new FormData();
        fd = new FormData();
        fd.append("file", file);
        $scope.scores.get(id).fd = fd;
        $scope.$apply(function () {
            $scope.scores.get(id).filenameStr = file.name;
        });
    };

    $scope.getFilenameStr = function (id) {
        if($scope.scores.get(id) == undefined)
            return;
        return $scope.scores.get(id).filenameStr;
    };

    $scope.isVisible = function (id) {
        if($scope.scores.get(id) == undefined)
            return;
        return $scope.scores.get(id).fd != null;
    };


    $scope.uploadFile = function (id) {
        $http({
            method: 'POST', url: $scope.SERVER_ADDRESS + 'task/upload/' + id,
            data: $scope.scores.get(id).fd,
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $window.location.reload(true);
            $scope.createAlertCookie('Uploaden gelukt.');
        }).error(function (response) {
            $scope.createAlertCookie('Uploaden mislukt.', "danger");
        });
    };


    $scope.removeTaskFrontend = function (id) {
        $scope.scores.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        while (element != null) {
            element.parentElement.removeChild(element);
            element = document.getElementById($scope.toElementId(id));
        }
    };
    $scope.removeTasksFrontend = function () {
        while (SCORES_LIST_ELEMENT.firstChild)
            SCORES_LIST_ELEMENT.removeChild(SCORES_LIST_ELEMENT.firstChild);
        $scope.scores.clear();
    };

    // get all tasks associated with student
    $scope.loadTasks = function () {
        // create requestURL
        var requestURL = $scope.SERVER_ADDRESS + 'score/id/';
        if ($cookies.getObject("user").role === UserType.STUDENT)
            requestURL += $cookies.getObject('user').id;
        else if ($cookies.getObject("user").role === UserType.PARENT && $cookies.getObject("child") != null)
            requestURL += $cookies.getObject("child").id;
        else
            requestURL += $cookies.getObject('user').id;
        requestURL += '/tasks';

        // make request
        $http({
            method: 'GET', url: requestURL,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            response.forEach(function (item) {
                $scope.addScore(item);
            });

            $scope.originalScores = new Map($scope.scores);
            $scope.refresh();
        });
    };


    // ACTUAL ACTIONS ON LOADED PAGE
    $scope.removeTasksFrontend();
    $scope.loadTasks();

    $scope.$watch(function () {
        if ($cookies.getObject("child") != undefined) {
            return $cookies.getObject("child").id;
        } else
            return;
    }, function (newValue) {
        if ($cookies.getObject("child") != undefined) {
            $scope.removeTasksFrontend();
            $scope.loadTasks();
        }
    });

});


app.controller('RemoveUploadModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalTitle = "upload";
    $scope.modalObject = "deze upload";

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});