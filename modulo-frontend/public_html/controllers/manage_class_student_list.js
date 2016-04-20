app.controller('ManageClassStudentListController', function ($scope) {
    const STUDENT_LIST_ITEM_PREFIX = 'manage-class-list-item-';
    const STUDENT_LIST_ELEMENT = document.getElementById('manage-class-topic-list-body');

    $scope.students = new Map();


    $scope.toElementId = function (id) {
        return STUDENT_LIST_ITEM_PREFIX + id;
    };


    $scope.addStudent = function (student) {
        $scope.removeStudentFrontend(addclass.id);
        $scope.classes.set(student.id, student);


        /*----------------------------------------------------------------------------*/
        // TODO eventueel opleidingen toevoegen in de 2de iteratie

        var html =  '<td>' + student.firstName + ' ' + student.lastName + '</td>' +
            '<td class="text-danger" ng-click="removeClassBackend(' + addclass.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        /*---------------------------------------------------------------------------------*/
        var element = document.createElement('tr');
        STUDENT_LIST_ELEMENT.appendChild(element);

        element.outerHTML = html;


    };

    $scope.removeStudentBackend = function (id) {
        $scope.removeClassFrontend(id);
        var paramVal = $scope.location.getParameter($scope.location.PARAM_EDIT_USER_ID);
        $http.delete('http://localhost:8080/class=' + paramVal + '/' + id);
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

    $http.get('http://localhost:8080/classes/teacherId=' + backend.getUser().id).success(function (response) {

        response.forEach(function (item) {
            $scope.addClass(item.clasEntity)
        });
        $scope.refresh();
    });
});

