app.controller('ManageClassController', function ($scope) {
    // TODO implement controller
    if ($scope.location.getParameter($scope.location.PARAM_CREATE_NEW_CLASS)) {
        $scope.panelCaption = 'Nieuwe klas aanmaken';
    } else {
        $scope.panelCaption = 'Klas bewerken';
    }
});
