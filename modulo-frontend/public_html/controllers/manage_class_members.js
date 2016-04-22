app.controller('ManageClassMembersController', function ($scope, $http, $compile) {
    // TODO implement controller
    var json = "" +
        '[' +
        '{"text": "Parent 1",' +
        '"nodes": [' +
        '{"text": "Child 1",' +
        '"nodes": [' +
        '{"text": "Grandchild 1"},' +
        '{"text": "Grandchild 2"}' +
        ']' +
        '},' +
        '{"text": "Child 2"}' +
        ']' +
        '},' +
        '{"text": "Parent 2"},' +
        '{"text": "Parent 3"},' +
        '{"text": "Parent 4"},' +
        '{"text": "Parent 5"}' +
        ']';

    var jsonSmall = '[' +
        '{"text": "Parent 1", "id": "1"},' +
        '{"text": "Parent 1", "id": "2"},' +
        '{"text": "Parent 1", "id": "3"},' +
        '{"text": "Parent 1", "id": "4"},' +
        '{"text": "Parent 1", "id": "5"}' +
        ']';

    $scope.students = new Map();
    $scope.studentsInClass = [];

    $scope.jsonUsers = "";

    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);

    $http.get('http://localhost:8080/class/'+$scope.classId+'/students').success(function (response) {
        response.forEach(function (item) {
            $scope.studentsInClass.push(item.studentInfoEntity.id);
        });

        $http.get('http://localhost:8080/student/all').success(function (response) {
            response.forEach(function (item) {
                $scope.students.set(item.studentInfoEntity.id, item);
                //console.log("Certificate of student " + item.userEntity.firstName + " " + item.userEntity.lastName + ": " + item.certificateEntity.name);
            });
            $scope.fillJsonUsers();
            $scope.buildTree();
        });
    });

    $scope.fillJsonUsers = function () {
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

        $scope.jsonUsers += '[';
        var i = 0;
        certificateStudents.forEach(function (students, key) {

            $scope.jsonUsers += '{"text": "' + key + '", "nodes": [';
            $scope.addStudents(students);
            //add student

            if (i < certificateStudents.size - 1) {
                $scope.jsonUsers += ']},'
            }
            else {
                $scope.jsonUsers += ']}'
            }
            i++;
        }, $scope.students);
        $scope.jsonUsers += ']';
    }

    $scope.addStudents = function (students) {
        for (var i = 0; i < students.length; i++) {
            var inClass = "";

            if($scope.studentInClass(students[i].studentInfoEntity.id)){
                inClass = "true";
            }

            if (i < students.length - 1) {
                $scope.jsonUsers += '{"text": "' + students[i].userEntity.firstName + ' ' + students[i].userEntity.lastName + '", "state":{"checked": "' + inClass + '"}, "studentInfoId": "' + students[i].studentInfoEntity.id + '"},';
            }
            else {
                $scope.jsonUsers += '{"text": "' + students[i].userEntity.firstName + ' ' + students[i].userEntity.lastName + '", "state":{"checked": "' + inClass + '"}, "studentInfoId": "' + students[i].studentInfoEntity.id + '"}';
            }
        }
    }

    $scope.studentInClass = function(studentInfoEntityId){
        for(var i = 0; i < $scope.studentsInClass.length; i++){
            if($scope.studentsInClass[i] == studentInfoEntityId){
                return true;
            }
        }
        return false;
    }

    $scope.buildTree = function () {
        //source: https://github.com/jonmiles/bootstrap-treeview/blob/master/public/index.html
        $scope.checkableTree = $('#treeview-checkable').treeview({
            data: $scope.jsonUsers,
            levels: 1,
            showIcon: false,
            showCheckbox: true,
            onNodeChecked: function (event, node) {
                //Todo add student to class
                //$('#checkable-output').prepend('<p>' + node.text + ' Checked with id: ' + node.studentInfoId + '</p>');
                $http.post('http://localhost:8080/class/' + $scope.classId + '/student/' + node.studentInfoId).success(function (response) {
                    if(response)
                        console.log("Post succes!");
                    else
                        console.log("Post fail!");
                });

                //console.log("Test1 " + node.text + " Checked with id: " + node.studentInfoId);
            },
            onNodeUnchecked: function (event, node) {
                //Todo remove student from class
                //$('#checkable-output').prepend('<p>' + node.text + ' Unchecked with id: ' + node.studentInfoId + '</p>');
                $http.delete('http://localhost:8080/class/' + $scope.classId + '/student/' + node.studentInfoId).success(function (response) {
                    if(response)
                        console.log("Delete succes!");
                    else
                        console.log("Delete fail!");
                });
                //console.log("Test2 " + node.text + " Unchecked with id: " + node.studentInfoId);
            }
        });

         var findCheckableNodess = function() {
         return $scope.checkableTree.treeview('search', [ $('#input-check-node').val(), { ignoreCase: false, exactMatch: false } ]);
         };
        /*
         var checkableNodes = findCheckableNodess();
         // Check/uncheck/toggle nodes
         $('#input-check-node').on('keyup', function (e) {
         checkableNodes = findCheckableNodess();
         $('.check-node').prop('disabled', !(checkableNodes.length >= 1));
         });

         $('#btn-check-node.check-node').on('click', function (e) {
         $scope.checkableTree.treeview('checkNode', [ checkableNodes, { silent: $('#chk-check-silent').is(':checked') }]);
         });
         $('#btn-uncheck-node.check-node').on('click', function (e) {
         $scope.checkableTree.treeview('uncheckNode', [ checkableNodes, { silent: $('#chk-check-silent').is(':checked') }]);
         });
         $('#btn-toggle-checked.check-node').on('click', function (e) {
         $scope.checkableTree.treeview('toggleNodeChecked', [ checkableNodes, { silent: $('#chk-check-silent').is(':checked') }]);
         });

        // Check/uncheck all
        $('#btn-check-all').on('click', function (e) {
            $scope.checkableTree.treeview('checkAll');//, { silent: $('#chk-check-silent').is(':checked') });
        });
        $('#btn-uncheck-all').on('click', function (e) {
            $scope.checkableTree.treeview('uncheckAll', {silent: $('#chk-check-silent').is(':checked')});
        });
        */
        //Expand all
        $scope.checkableTree.treeview('expandAll', {silent: true});
    }

});


