app.controller('ListTasksController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    // TODO finish controller
    const TASK_LIST_ITEM_PREFIX = 'task-list-item-';
    const TASK_LIST_ELEMENT = document.getElementById('table-task-list-body');

    $scope.tasks = new Map();
    $scope.originalTasks = new Map();
    $scope.removeId;


    // TODO: set to actual default values
    $scope.task = {
        name: 'Taak 1',
        dueDate: '1995-07-25',
        description: 'Dit is de eerste taak die jullie moetn maken.',
        pavClassId: null
    };


    // PAV classes
    $scope.PAVClasses = {};  // gets filled up at bottom of script
    $scope.setSelectedPAVClass = function (PAVClass) {
        $scope.selectedPAVClass = PAVClass.name;
        $scope.task.pavClassId = PAVClass.id;
    };

    $scope.toElementId = function (id) {
        return TASK_LIST_ITEM_PREFIX + id;
    };

    $scope.addTask = function (task) {
        $scope.removeTaskFrontend(task.id);
        $scope.tasks.set(task.id, task);

        var html = '<tr id="' + $scope.toElementId(task.id) + '">' +
            '<td>' + task.name + '</td><td>' + task.deadline + '</td>' +
            '<td class="text-info" ng-click="location.setParameter(location.PARAM_EDIT_TASK_ID,' + task.id + ')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" ng-click="openRemoveModal(' + task.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td></tr>';

        var element = document.createElement('tr');
        TASK_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.removeTaskBackend = function (id) {
        $http.delete('http://localhost:8080/task/id/' + id, {headers: {'X-Auth': $cookies.get("auth")}}).success(function (response) {
            $scope.removeTaskFrontend(id);
        });
    };

    $scope.removeTaskFrontend = function (id) {
        $scope.tasks.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    };

    // $scope.openRemoveModal = function (id) {
    //     var modalInstance = $uibModal.open({
    //         animation: true,
    //         templateUrl: 'views/panels/remove_modal.html',
    //         controller: 'RemoveModalInstanceCtrl',
    //         resolve: {}
    //     });
    //     $scope.removeId = id;
    //     modalInstance.result.then(function () {
    //         $scope.removeUserBackend($scope.removeId)
    //     }, function () {
    //     });
    // };


    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(TASK_LIST_ELEMENT)($scope);
    };


    // ACTUAL ACTIONS ON LOADED PAGE
    // load all PAV classes for this teacher
    $http({
        method: 'GET', url: 'http://localhost:8080/user/id/' + $cookies.getObject("user").id + '/teaching/type/PAV',
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        response.forEach(function (item) {
            $scope.PAVClasses[item.id] = item;
        });
        var firstKey = (Object.keys($scope.PAVClasses)[0]);
        $scope.selectedPAVClass = $scope.PAVClasses[firstKey].name;  // get first element in map  (anywhere else the Map is needed; array not feasible)
        $scope.task.pavClassId = $scope.PAVClasses[firstKey].id;

        $http.get('http://localhost:8080/task/all', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            response.forEach(function (item) {
                $scope.addTask(item);
            });
            $scope.originalTasks = new Map($scope.tasks);
            $scope.refresh();
        });
    });
});


// app.controller('RemoveModalInstanceCtrl', function ($scope, $uibModalInstance) {
//     $scope.modalTitle = "Verwijder gebruiker";
//
//     $scope.ok = function () {
//         $uibModalInstance.close();
//     };
//     $scope.cancel = function () {
//         $uibModalInstance.dismiss('cancel');
//     };
// });
