app.controller('ListClassesController', function ($scope) {
    // TODO implement controller
    $scope.currentTab = ClassType.PAV;
    $scope.switchTab = function (tab) {
        $scope.currentTab = tab;
        console.log(tab);
        console.log($scope.currentTab);
    };
});

