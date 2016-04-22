app.controller('ManageClassCourseTopicListController', function ($scope) {
    const COURSE_TOPIC_LIST_ITEM_PREFIX = 'manage-course-topic-list-item-';
    const COURSE_TOPIC_LIST_ELEMENT = document.getElementById('manage-course-topic-list-body');

    $scope.courseTopics = new Map();

    $scope.toElementId = function (id) {
        return COURSE_TOPIC_LIST_ITEM_PREFIX + id;
    };

    $scope.addCourseTopic = function (courseTopic) {
        $scope.removeClassFrontend(courseTopic.id);
        $scope.courseTopics.set(courseTopic.id, courseTopic);

        var html = '<tr id="' + $scope.toElementId(addclass.id) + '">' +
            '<td>' + courseTopic.name + '</td>' +
            '<td class="text-info"  ng-click="location.setParameter(location.PARAM_MANAGE_COURSE_TOPIC_ID,'+courseTopic.id+')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" ng-click="removeCourseTopicBackend(' +  courseTopic.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        var element = document.createElement('tr');
        element.outerHTML = html;

    };

    $scope.removeCourseTopicBackend = function (id) {
        $scope.removeCourseTopicFrontend(id);
        //$http.delete('http://localhost:8080/class/'+id);
    };

    $scope.removeCourseTopicFrontend = function (id) {
        $scope.classes.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    }

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(COURSE_TOPIC_LIST_ELEMENT)($scope);
    };

   /* $http.get('http://localhost:8080/class/teacher/' + $scope.backend.getUser().id).success(function (response) {
        response.forEach(function (item) {
            $scope.addCourseTopic(item.classEntity)
        });
        $scope.refresh();
    });*/
    
});
