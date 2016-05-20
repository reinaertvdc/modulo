app.controller('ListTasksController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    const TASK_LIST_ITEM_PREFIX = 'task-list-item-';
    const TASK_LIST_ELEMENT = document.getElementById('table-task-list-body');

    $scope.tasks = new Map();
    $scope.originalTasks = new Map();
    $scope.removeId;


    $scope.toElementId = function (id) {
        return TASK_LIST_ITEM_PREFIX + id;
    };

    $scope.addTask = function (task) {
        $scope.removeTaskFrontend(task.id);
        $scope.tasks.set(task.id, task);

        var html = '<tr id="' + $scope.toElementId(task.id) + '">' +
            '<td><a href="" ng-click="location.setParameter(location.PARAM_TASK_SCORES,' + task.id + ')">' + task.name + '</a></td><td>' + task.clazz.name + '</td><td>' + task.deadline + '</td>' +
            '<td class="text-info" ng-click="duplicate(' + task.id + ')"><span role="button" class="glyphicon glyphicon-duplicate"></span></td>' +
            '<td class="text-info" ng-click="location.setParameter(location.PARAM_EDIT_TASK_ID,' + task.id + ')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" ng-click="openRemoveModal(' + task.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td></tr>';

        var element = document.createElement('tr');
        TASK_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.removeTaskBackend = function (id) {
        $http({
            method: 'DELETE', url: $scope.SERVER_ADDRESS + 'task/id/' + id,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.removeTaskFrontend(id);
        });
    };

    $scope.removeTaskFrontend = function (id) {
        $scope.tasks.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    };

    $scope.openRemoveModal = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/remove_modal.html',
            controller: 'RemoveTaskModalInstanceCtrl',
            resolve: {}
        });
        $scope.removeId = id;
        modalInstance.result.then(function () {
            $scope.removeTaskBackend($scope.removeId)
        }, function () {
        });
    };

    $scope.duplicate = function (id) {
        $scope.location.setParameter($scope.location.PARAM_EDIT_TASK_ID, 'nieuw-' + id);
    };


    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(TASK_LIST_ELEMENT)($scope);
    };


    // ACTUAL ACTIONS ON LOADED PAGE
    // get all tasks associated with current teacher
    $http({
        method: 'GET', url: $scope.SERVER_ADDRESS + 'task/teacher/' + $cookies.getObject("user").id,
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        response.forEach(function (item) {
            $scope.addTask(item);
        });
        $scope.refresh();
    });
});


app.controller('RemoveTaskModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalObject = "taak";

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
