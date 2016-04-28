app.controller('ManageClassMembersController', function ($scope, $http) {
    // TODO implement controller
    /*var json = "" +
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
        ']';*/

    $scope.students = new Map();
    $scope.studentsInClass = [];
    $scope.jsonStudents = "";

    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);

    $http.get('http://localhost:8080/class/'+$scope.classId+'/students').success(function (response) {
        response.forEach(function (item) {
            $scope.studentsInClass.push(item.studentInfoEntity.id);
        });

        $http.get('http://localhost:8080/student/all').success(function (response) {
            response.forEach(function (item) {
                $scope.students.set(item.studentInfoEntity.id, item);
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

        $scope.jsonStudents += '[';
        var i = 0;
        certificateStudents.forEach(function (students, key) {

            $scope.jsonStudents += '{"text": "' + key + '", "nodes": [';

            var allStudentChecked = $scope.addStudents(students, key);
            $scope.jsonStudents += '], ' +
                '"state":{"checked": ' + allStudentChecked + '}}';

            if (i < certificateStudents.size - 1) {
                $scope.jsonStudents += ',';
            }
            i++;
        }, $scope.students);
        $scope.jsonStudents += ']';
    }

    $scope.addStudents = function (students, certificate) {
        var allChecked = true;
        for (var i = 0; i < students.length; i++) {
            var inClass = false;

            if($scope.studentInClass(students[i].studentInfoEntity.id)){
                inClass = true;
            }
            else if(allChecked){
                allChecked = false;
            }

            $scope.jsonStudents += '{"text": "' + students[i].userEntity.firstName + ' ' + students[i].userEntity.lastName + '", ' +
                    '"parent": "' +  certificate + '", ' +
                '"state":{"checked": ' + inClass + '}, ' +
                '"studentInfoId": "' + students[i].studentInfoEntity.id + '"}';

            if (i < students.length - 1) {
                $scope.jsonStudents += ',';
            }
        }
        return allChecked;
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
            data: $scope.jsonStudents,
            levels: 1,
            showIcon: false,
            showCheckbox: true,
            onNodeChecked: function (event, node) {
                if(node.parent != null){
                    $http.post('http://localhost:8080/class/' + $scope.classId + '/student/' + node.studentInfoId).success(function (response) {
                        //TODO verwijder console log stuff
                        if(response) {
                            console.log("Post succes!");
                            if($scope.allSiblingsChecked(node)){
                                var parent = $('#treeview-checkable').treeview('getParent', node);
                                $('#treeview-checkable').treeview('checkNode', [ parent.nodeId, { silent: true } ]);
                            }
                        }
                        else {
                            console.log("Post fail!");
                        }
                    });
                }
                else{
                    $scope.checkChildNodes(node);
                }
            },
            onNodeUnchecked: function (event, node) {
                if(node.parent != null) {
                    $http.delete('http://localhost:8080/class/' + $scope.classId + '/student/' + node.studentInfoId).success(function (response) {
                        //TODO verwijder console log stuff
                        if (response) {
                            console.log("Delete succes!");
                            if($scope.allSiblingsChecked(node)){
                                var parent = $('#treeview-checkable').treeview('getParent', node);
                                $('#treeview-checkable').treeview('uncheckNode', [ parent.nodeId , { silent: true } ]);
                            }
                        }
                        else {
                            console.log("Delete fail!");
                        }
                    });
                }
                else{
                    $scope.uncheckChildNodes(node);
                }
            }
        });

        $scope.checkChildNodes = function(parentNode){
            var allNodes = $('#treeview-checkable').treeview('getEnabled', parentNode.nodeId);
            allNodes.forEach(function(node){
                if(node.parent == parentNode.text){
                    $('#treeview-checkable').treeview('checkNode', [ node.nodeId ]);
                }
            });
        }

        $scope.uncheckChildNodes = function(parentNode){
            var allNodes = $('#treeview-checkable').treeview('getEnabled', parentNode.nodeId);
            allNodes.forEach(function(node){
                if(node.parent == parentNode.text){
                    $('#treeview-checkable').treeview('uncheckNode', [ node.nodeId ]);
                }
            });
        }

        $scope.allSiblingsChecked = function(node){
            var allChecked = true;
            var siblings = $('#treeview-checkable').treeview('getSiblings', node);

            for(var i = 0; allChecked && i < siblings.length; i++){
                if(!siblings[i].state.checked){
                    allChecked = false;
                }
            };

            return allChecked;
        }

         var findCheckableNodess = function() {
         return $scope.checkableTree.treeview('search', [ $('#input-check-node').val(), { ignoreCase: true, exactMatch: false } ]);
         };

        var checkableNodes = findCheckableNodess();
        $('#input-check-node').on('keyup', function (e) {
         checkableNodes = findCheckableNodess();
         //$('.check-node').prop('disabled', !(checkableNodes.length >= 1));
         });
/*
         // Check/uncheck/toggle nodes
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


