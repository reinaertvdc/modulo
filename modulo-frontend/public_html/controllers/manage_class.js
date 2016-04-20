app.controller('ManageClassController', function ($scope) {
    // TODO implement controller
    
    
    if ($scope.location.getParameter($scope.location.PARAM_CREATE_NEW_CLASS)) {
        $scope.panelCaption = 'Nieuwe klas aanmaken';
    } else {
        $scope.panelCaption = 'Klas bewerken';
    }

    $scope.Tab = Object.freeze({
        STUDENT: 'Student',
        CLASS_TOPIC: 'Class_Topic'
    });

    $scope.currentTab = $scope.Tab.STUDENT;
    $scope.switchTab = function (tab) {
        $scope.currentTab = tab;
        console.log(tab);
        console.log($scope.currentTab);
    };
    console.log($scope.currentTab);
    console.log($scope.currentTab === $scope.Tab.CLASS_TOPIC);
    

});
