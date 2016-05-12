app.controller('TaskScoresController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    const SCORES_LIST_ITEM_PREFIX = 'scores-list-item-';
    const SCORES_LIST_ELEMENT = document.getElementById('table-scores-list-body');
    var taskId = $scope.location.getParameter($scope.location.PARAM_TASK_SCORES);

    $scope.panelCaption = 'Taak quoteren: ';
    $scope.scores = new Map();
    $scope.scoreStrs = {"Aangeboden": "A", "Ingeoefend": "I", "Verworven": "V"};

    $scope.scoreStr = "Aangeboden";
    $scope.remarks = "";


    $scope.toElementId = function (id) {
        return SCORES_LIST_ITEM_PREFIX + id;
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(SCORES_LIST_ELEMENT)($scope);
    };

    $scope.addScore = function (score) {
        var scoreObj = {'scoreObj': score, 'checked': false};
        $scope.scores.set(score.id, scoreObj);

        var html = '<tr id="' + $scope.toElementId(score.id) + '">' +
            '<td align="center"><input type="checkbox" ng-model="scores.get(' + score.id + ').checked"></td>' +
            '<td>' + score.user.firstName + ' ' + score.user.lastName + '</td>';

        // download icon
        if (score.fileName == null || score.fileName == '')
            html += '<td align="center"></td>';
        else
            html += '<td align="center"><span role="button" class="glyphicon glyphicon-download" ng-click="downloadFile(' + score.id + ')"></span></td>';

        // check if score already set
        if (score.score == null)
            html += '<td>' + "" + '</td>';
        else if (score.remarks == null || score.remarks == "")
            html += '<td align="center">' + score.score + '</td>';
        else
            html += '<td align="center"><div tooltip-class="customClass" tooltip-placement="top" uib-tooltip="Commentaar:\n' + score.remarks + '">' + score.score + '</div></td>';

        html += '</tr>';
        var element = document.createElement('tr');
        SCORES_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.toggleAll = function () {
        var cntChecked = 0;
        $scope.scores.forEach(function (score) {
            if (score.checked)
                cntChecked++;
        });
        if (cntChecked == $scope.scores.size)
            $scope.selectNone();
        else
            $scope.selectAll();
    };

    $scope.selectNone = function () {
        $scope.scores.forEach(function (score) {
            score.checked = false;
        });
    };
    $scope.selectAll = function () {
        $scope.scores.forEach(function (score) {
            score.checked = true;
        });
    };

    $scope.downloadFile = function (id) {
        $scope.execDownload('http://localhost:8080/task/download/' + id,
            $scope.scores.get(id).scoreObj.fileName);
    }
    $scope.downloadAll = function () {
        $scope.execDownload('http://localhost:8080/task/downloadAll/' + taskId,
            $scope.task.name + '.zip');
    };

    $scope.execDownload = function (url, destName) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.setRequestHeader('X-auth', $cookies.get("auth"));
        xhr.responseType = "blob";
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var blobURL = (window.URL || window.webkitURL).createObjectURL(xhr.response);
                var anchor = document.createElement("a");
                anchor.download = destName;
                anchor.href = blobURL;
                anchor.click();
            }
        };
        xhr.send();
    };


// ON SUBMIT
    $scope.submitForm = function () {
        console.log($scope.scores)
        console.log($scope.scoreStrs[$scope.scoreStr])
        console.log($scope.remarks)
    };


// ACTUAL ACTIONS ON LOADED PAGE
// get task info
    $http({
        method: 'GET', url: 'http://localhost:8080/task/id/' + taskId,
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        $scope.task = response;
        $scope.panelCaption = 'Taak quoteren: ' + $scope.task.name + ' (' + $scope.task.deadline + ')';
    });

// get all taskscores
    $http({
        method: 'GET', url: 'http://localhost:8080/task/scores/' + taskId,
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        response.forEach(function (item) {
            $scope.addScore(item);
        });
        $scope.refresh();
    });





    $scope.setFile = function (file) {
        $scope.fd = new FormData();
        $scope.fd.append("file", file);
        $scope.$apply(function () {
            $scope.fileNameStr = file.name;
        });
    };


    $scope.uploadFile = function () {
        $http({
            method: 'POST', url: 'http://localhost:8080/task/upload/335', data: $scope.fd,
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            console.log("succes: " + JSON.stringify(response));
        }).error(function (response) {
            console.log("error: " + JSON.stringify(response));
        });
    };
})
;
