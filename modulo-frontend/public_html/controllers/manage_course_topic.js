app.controller('ManageCourseTopicController', function ($scope) {
    // TODO implement controller
    if ($scope.location.getParameter($scope.location.PARAM_MANAGE_COURSE_TOPIC_ID) == $scope.location.PARAM_CREATE_NEW_COURSE_TOPIC_ID) {
        $scope.panelCaption = 'Nieuwe vakthema aanmaken';
    } else {
        $scope.panelCaption = 'Vakthema bewerken';
    }
});
