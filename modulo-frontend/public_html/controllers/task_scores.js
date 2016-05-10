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
        score.checked = false;  // default checked state
        $scope.scores.set(score.id, score);

        var html = '<tr id="' + $scope.toElementId(score.id) + '">' +
            '<td align="center"><input type="checkbox" ng-model="scores.get(' + score.id + ').checked"></td>' +
            '<td>' + score.user.firstName + ' ' + score.user.lastName + '</td>' +
            '<td align="center"><span class="glyphicon glyphicon-download"></span></td>';

        // check if score already set
        if(score.score == null)
            html += '<td>' + "" + '</td>';
        else if(score.remarks == null  ||  score.remarks == "")
            html += '<td align="center">' + score.score + '</td>';
        else
            html += '<td align="center"><span tooltip-class="customClass" tooltip-placement="top" uib-tooltip="Commentaar:\n' + score.remarks +'">' + score.score + '</span></td>';

        html += '</tr>';
        var element = document.createElement('tr');
        SCORES_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.toggleAll = function () {
        var cntChecked = 0;
        $scope.scores.forEach(function (score) {
            if(score.checked)
                cntChecked++;
        });
        if(cntChecked == $scope.scores.size)
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


    // ON SUBMIT
    $scope.submitForm = function () {
        console.log($scope.scores)
        console.log($scope.scoreStrs[$scope.scoreStr])
        console.log($scope.remarks)
    };
});
