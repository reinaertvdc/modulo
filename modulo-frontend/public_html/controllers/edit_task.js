app.controller('EditTaskController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    var paramVal = $scope.location.getParameter($scope.location.PARAM_EDIT_TASK_ID);


    // TODO: set to actual default values
    $scope.task = {
        name: 'Taak 1',
        deadline: '1995-07-25',
        description: 'Dit is de eerste taak die jullie moetn maken.',
        clazz: null
    };


    // PAV classes
    $scope.PAVClasses = {};  // gets filled up at bottom of script
    $scope.setSelectedPAVClass = function (PAVClass) {
        $scope.selectedPAVClass = PAVClass.name;
        $scope.task.clazz = PAVClass.id;
    };


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
        $scope.task.clazz = $scope.PAVClasses[firstKey].id;
    });


    // NEW USER
    if (paramVal == 'nieuw') {
        $scope.btnText = 'Aanmaken';
        $scope.panelCaption = 'Nieuwe taak aanmaken';
    }


    // EDIT USER
    else if (paramVal) {
        $scope.btnText = 'Opslaan';

        $http.get('http://localhost:8080/task/id/' + paramVal, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            $scope.task = response;
            $scope.panelCaption = 'Taak bewerken: ' + $scope.task.name;


        });
    }
});
