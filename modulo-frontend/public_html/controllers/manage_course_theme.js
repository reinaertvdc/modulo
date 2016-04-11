app.controller('ManageCourseThemeController', function ($scope) {
    // TODO implement controller
    if ($scope.location.getParameter($scope.location.PARAM_CREATE_NEW_COURSE_THEME)) {
        $scope.panelCaption = 'Nieuwe vakthema aanmaken';
    } else {
        $scope.panelCaption = 'Vakthema bewerken';
    }
});
