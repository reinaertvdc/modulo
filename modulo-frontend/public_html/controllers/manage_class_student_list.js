
const MANAGE_CLASS_LIST_ITEM_PREFIX = 'manage-class-list-item-';
const COURSE_TOPIC_LIST_ELEMENT = document.getElementById('manage-class-topic-list-body');
const COURSE_TOPIC_LIST_HEADER_ELEMENT = document.getElementById('manage-class-topic-list-header');

$scope.students = new Map();


$scope.toElementId = function (id) {
    return CLASS_LIST_ITEM_PREFIX + id;
};


$scope.addStudent = function (student) {
    $scope.removeStudentFrontend(addclass.id);
    $scope.classes.set(student.id, student);

    var html = '<tr id="' + $scope.toElementId(addclass.id) + '">' +
        '<td>' + addclass.name + '</td>' +
        '<td class="text-info"><span role="button" class="glyphicon glyphicon-edit" ng-click="location.setParamater(location.PARAM_MANAGE_CLASS_ID, addclass.id)"></span></td>' +
        '<td class="text-danger" ng-click="removeClassBackend(' + addclass.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
        '</tr>';

    var element = document.createElement('tr');

    if(addclass.type.equals("BGV"))
        BGVCLASS_LIST_ELEMENT.appendChild(element);
    if(addclass.type.equals("PAV"))
        PAVCLASS_LIST_ELEMENT.appendChild(element);

    element.outerHTML = html;

};

$scope.removeStudentBackend = function (id) {
    $scope.removeClassFrontend(id);
    $http.delete('http://localhost:8080/class/'+id);
};

$scope.removeStudentFrontend = function (id) {
    $scope.classes.delete(id);
    var element = document.getElementById($scope.toElementId(id));
    if (element !== null)
        element.parentElement.removeChild(element);
}

// Update the Angular controls that have been added in the HTML
$scope.refresh = function () {
    $compile(PAVCLASS_LIST_ELEMENT)($scope);
    $compile(BGVCLASS_LIST_ELEMENT)($scope);
};

$http.get('http://localhost:8080/classes/teacherId=' + backend.getUser().id).success(function (response) {
        
    response.forEach(function (item) {
        $scope.addClass(item.clasEntity)
    });
    $scope.refresh();
});