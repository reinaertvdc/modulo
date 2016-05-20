app.controller('ManageClassStudentListController', function ($scope, $http, $compile, $cookies) {
    const STUDENT_LIST_ITEM_PREFIX = 'manage-student-list-item-';
    const STUDENT_LIST_ELEMENT = document.getElementById('manage-student-list-body');

    $scope.students = new Map();
    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);

    $scope.toElementId = function (id) {
        return STUDENT_LIST_ITEM_PREFIX + id;
    };


    $scope.addStudent = function (student) {
        $http.get($scope.SERVER_ADDRESS + 'user/id/'+student.id+'/certificate', {headers: {'X-Auth': $cookies.get("auth")}}).success(function (response) {
            $scope.removeStudentFrontend(student.id);
            student.certificate = response;
            $scope.students.set(student.id, student);

            var html =  '<tr id="' + $scope.toElementId(student.id) + '">' +
                '<td>' + student.firstName + ' ' + student.lastName + '</td>' +
                '<td>' + student.certificate.name + '</td>' +
                '<td class="text-danger" ng-click="removeStudentBackend(' + student.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td></tr>';

            /*---------------------------------------------------------------------------------*/
            var element = document.createElement('tr');
            STUDENT_LIST_ELEMENT.appendChild(element);

            element.outerHTML = html;

            $scope.certificatesReady--;
            $scope.refresh();
        });
    };

    $scope.removeStudentBackend = function (id) {
        $http({
            method: 'DELETE', url: $scope.SERVER_ADDRESS + 'class/id/' + $scope.classId + '/student/' + id,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.removeStudentFrontend(id);
            $scope.createAlertCookie('Leerling verwijderd.');
        });
    };

    $scope.removeStudentFrontend = function (id) {
        $scope.students.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    }

// Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        if($scope.studentsReady && $scope.certificatesReady == 0) {
            $compile(STUDENT_LIST_ELEMENT)($scope);
        }
    };

    $http.get($scope.SERVER_ADDRESS + 'class/id/'+$scope.classId+'/students', {headers: {'X-Auth': $cookies.get("auth")}}).success(function (response) {
        $scope.certificatesReady = response.length;
        response.forEach(function (item) {
            $scope.addStudent(item);
        });
        $scope.studentsReady = true;
    });
});

