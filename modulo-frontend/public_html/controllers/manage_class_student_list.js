app.controller('ManageClassStudentListController', function ($scope, $http, $compile) {
    const STUDENT_LIST_ITEM_PREFIX = 'manage-student-list-item-';
    const STUDENT_LIST_ELEMENT = document.getElementById('manage-student-list-body');

    $scope.students = new Map();
    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);

    $scope.toElementId = function (id) {
        return STUDENT_LIST_ITEM_PREFIX + id;
    };


    $scope.addStudent = function (student) {
        $scope.removeStudentBackend(student.studentInfoEntity.id);
        $scope.students.set(student.studentInfoEntity.id, student);


        /*----------------------------------------------------------------------------*/
        // TODO eventueel certificaten toevoegen in de 2de iteratie
        var html =  '<tr id="' + $scope.toElementId(student.studentInfoEntity.id) + '">' +
            '<td>' + student.userEntity.firstName + ' ' + student.userEntity.lastName + '</td>' +
            '<td>' + student.certificateEntity.name + '</td>' +
            '<td class="text-danger" ng-click="removeStudentBackend(' + student.studentInfoEntity.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        /*---------------------------------------------------------------------------------*/
        var element = document.createElement('tr');
        STUDENT_LIST_ELEMENT.appendChild(element);

        element.outerHTML = html;
    };

    $scope.removeStudentBackend = function (id) {
        $scope.removeStudentFrontend(id);
       // $http.delete('http://localhost:8080/class=' + paramVal + '/' + id);
    };

    $scope.removeStudentFrontend = function (id) {
        $scope.students.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    }

// Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(STUDENT_LIST_ELEMENT)($scope);
    };

    $http.get('http://localhost:8080/class/'+$scope.classId+'/students').success(function (response) {
        response.forEach(function (item) {
            $scope.addStudent(item);
        });
        $scope.refresh();
    });
});

