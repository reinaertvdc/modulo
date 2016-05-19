app.controller('ManageClassController', function ($scope) {
    // TODO implement controller
    $scope.classType = $scope.location.getParameter($scope.location.PARAM_CLASS_TYPE);

    $scope.Tab = Object.freeze({
        STUDENT: 'Student',
        CLASS_TOPIC: 'Class_Topic',
        DETAIL: 'Detail'
    });

    $scope.currentTab = $scope.Tab.STUDENT;

    $scope.switchTab = function (tab) {
        $scope.currentTab = tab;
    };

    $scope.getActiveClassComponent = function() {
        if($scope.currentTab == $scope.Tab.STUDENT){
            return 'views/components/manage_class_student_list.html'
        }else if($scope.currentTab == $scope.Tab.CLASS_TOPIC){
            return 'views/components/manage_class_course_topic_list.html'
        }else if($scope.currentTab == $scope.Tab.DETAIL){
            return 'views/components/manage_class_details.html'
        }
    };

});
