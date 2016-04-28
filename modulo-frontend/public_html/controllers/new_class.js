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
            $scope.addStudents(students, key);
            $scope.jsonStudents += ']}';

            if (i < certificateStudents.size - 1) {
                $scope.jsonStudents += ',';
            }
            i++;
        }, $scope.students);
        $scope.jsonStudents += ']';
    }

    $scope.addStudents = function (students, certificate) {
        for (var i = 0; i < students.length; i++) {
            $scope.jsonStudents += '{"text": "' + students[i].userEntity.firstName + ' ' + students[i].userEntity.lastName + '", ' +
                '"parent": "' +  certificate + '", ' +
                '"studentInfoId": "' + students[i].studentInfoEntity.id + '"}';

            if (i < students.length - 1) {
                $scope.jsonStudents += ',';
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
                if(node.parent != null){
                    if($scope.allSiblingsChecked(node)){
                        var parent = $('#treeview-checkable').treeview('getParent', node);
                        $('#treeview-checkable').treeview('checkNode', [ parent.nodeId, { silent: true } ]);
                    }
                }
                else{
                    $scope.checkChildNodes(node);
                }
            },
            onNodeUnchecked: function (event, node) {
                if (node.parent != null) {
                    if ($scope.allSiblingsChecked(node)) {
                        var parent = $('#treeview-checkable').treeview('getParent', node);
                        $('#treeview-checkable').treeview('uncheckNode', [parent.nodeId, {silent: true}]);
                    }
                }
                else {
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
