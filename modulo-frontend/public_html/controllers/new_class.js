app.controller('NewClassController', function ($scope, $http) {
        // TODO implement controller
    $scope.students = new Map();
    $scope.jsonStudents = "";

        $http.get('http://localhost:8080/student/all').success(function (response) {
            response.forEach(function (item) {
                $scope.students.set(item.studentInfoEntity.id, item);
            });
            $scope.fillJsonStudents();
            $scope.buildTree();
        });

    $scope.fillJsonStudents = function () {
        var certificateStudents = new Map();
        $scope.students.forEach(function (student, key) {
            var certificate = student.certificateEntity;
            if (certificateStudents.has(certificate.name)) {
                certificateStudents.get(certificate.name).push(student);
            }
            else {
                certificateStudents.set(certificate.name, []);
                certificateStudents.get(certificate.name).push(student);
            }
        }, $scope.students);

        $scope.jsonStudents += '[';
        var i = 0;
        certificateStudents.forEach(function (students, key) {
            $scope.jsonStudents += '{"text": "' + key + '", "nodes": [';
            $scope.addStudents(students);
            //add student

            if (i < certificateStudents.size - 1) {
                $scope.jsonStudents += ']},'
            }
            else {
                $scope.jsonStudents += ']}'
            }
            i++;
        }, $scope.students);
        $scope.jsonStudents += ']';
    }

    $scope.addStudents = function (students) {
        for (var i = 0; i < students.length; i++) {
            if (i < students.length - 1) {
                $scope.jsonStudents += '{"text": "' + students[i].userEntity.firstName + ' ' + students[i].userEntity.lastName + '", "studentInfoId": "' + students[i].studentInfoEntity.id + '"},';
            }
            else {
                $scope.jsonStudents += '{"text": "' + students[i].userEntity.firstName + ' ' + students[i].userEntity.lastName + '", "studentInfoId": "' + students[i].studentInfoEntity.id + '"}';
            }
        }
    }

    $scope.buildTree = function () {
        //source: https://github.com/jonmiles/bootstrap-treeview/blob/master/public/index.html
        $scope.checkableTree = $('#treeview-checkable').treeview({
            data: $scope.jsonStudents,
            levels: 1,
            showIcon: false,
            showCheckbox: true,
            onNodeChecked: function (event, node) {
                //Todo

            },
            onNodeUnchecked: function (event, node) {
                //Todo
            }
        });

        var findCheckableNodess = function() {
            return $scope.checkableTree.treeview('search', [ $('#input-check-node').val(), { ignoreCase: false, exactMatch: false } ]);
        };

        //Expand all
        $scope.checkableTree.treeview('expandAll', {silent: true});
    }
});

app.controller('DropdownCtrl', function ($scope, $log) {
    $scope.items = [
        'The first choice!',
        'And another choice for you.',
        'but wait! A third!'
    ];

    $scope.status = {
        isopen: false
    };

    $scope.toggled = function(open) {
        $log.log('Dropdown is now: ', open);
    };

    $scope.toggleDropdown = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.status.isopen = !$scope.status.isopen;
    };

    $scope.appendToEl = angular.element(document.querySelector('#dropdown-long-content'));
});
