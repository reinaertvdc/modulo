app.controller('ManageClassCourseTopicListController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    const COURSE_TOPIC_LIST_ITEM_PREFIX = 'manage-course-topic-list-item-';
    const COURSE_TOPIC_LIST_ELEMENT = document.getElementById('manage-course-topic-list-body');
    const paramClass = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);
    $scope.courseTopics = new Map();

    $http({
        method: 'GET', url: $scope.SERVER_ADDRESS + 'class/id/' + paramClass + '/coursetopics',
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        response.forEach(function (item) {
            $scope.addCourseTopic(item)
        });
        $scope.refresh();
    });

    $scope.toElementId = function (id) {
        return COURSE_TOPIC_LIST_ITEM_PREFIX + id;
    };

    $scope.addCourseTopic = function (courseTopic) {
        $scope.removeCourseTopicFrontend(courseTopic.id);
        $scope.courseTopics.set(courseTopic.id, courseTopic);

        var html = '<tr id="' + $scope.toElementId(courseTopic.id) + '">' +
            '<td>' + courseTopic.name + '</td>' +
            '<td class="text-info"  ng-click="location.setParameter(location.PARAM_MANAGE_COURSE_TOPIC_ID,'+courseTopic.id+')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" ng-click="openRemoveModal(' +  courseTopic.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        var element = document.createElement('tr');
        COURSE_TOPIC_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;

    };

    $scope.removeCourseTopicBackend = function (id) {
        $scope.removeCourseTopicFrontend(id);
        $http({
            method: 'DELETE', url: $scope.SERVER_ADDRESS + 'coursetopic/id/' + id,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.refresh();
        });

    };

    $scope.removeCourseTopicFrontend = function (id) {
        $scope.courseTopics.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(COURSE_TOPIC_LIST_ELEMENT)($scope);
    };

    $scope.openRemoveModal = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/remove_modal.html',
            controller: 'RemoveCourseTopicModalInstanceCtrl',
            resolve: {}
        });
        $scope.removeId = id;
        modalInstance.result.then(function () {
            $scope.removeCourseTopicBackend($scope.removeId)
        }, function () {
        });
    };

});

app.controller('RemoveCourseTopicModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalObject = "vakthema";

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
