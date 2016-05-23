app.controller('ManageClassMembersController', function ($scope, $http, $cookies) {
    $scope.students = new Map();
    $scope.studentsInClass = [];
    $scope.jsonStudents = "";

    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);

    $scope.prevPage = function(){
        $scope.location.setParameter("leerlingen", null);
    }


    $http.get($scope.SERVER_ADDRESS + 'class/id/'+$scope.classId+'/students', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
            $scope.studentsInClass.push(item.id);
        });

        $scope.getStudents();
    }).error(function(error, code){
        if(code == 404){
            $scope.getStudents();
        }
    });

    $scope.getStudents = function(){
        $http.get($scope.SERVER_ADDRESS + 'class/id/'+$scope.classId+'/type', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            $scope.classType = response;

            if($scope.classType == "BGV") {
                $http.get($scope.SERVER_ADDRESS + 'class/id/' + $scope.classId + '/certificate', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                    var classCertificate = response;

                    $http.get($scope.SERVER_ADDRESS + 'certificate/id/' + classCertificate.id + '/students', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                        response.forEach(function (item) {
                            $scope.students.set(item.id, item);
                        });
                        $scope.categorizeStudentsByCertificate();
                    });
                });
            }
            else if($scope.classType == "PAV"){
                $http.get($scope.SERVER_ADDRESS + 'class/id/' + $scope.classId + '/grade', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                    var classGrade = response;

                    $http.get($scope.SERVER_ADDRESS + 'grade/id/' + classGrade.id + '/students', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                        response.forEach(function (item) {
                            $scope.students.set(item.id, item);
                        });
                        $scope.categorizeStudentsByCertificate();
                    });
                });
            }
        });
    }

    $scope.categorizeStudentsByCertificate = function () {
        $scope.certificateStudents = new Map();
        $scope.certificates = new Map();
        var i = 0;
        $scope.students.forEach(function (student, key) {

            $http.get($scope.SERVER_ADDRESS + 'user/id/'+student.id+'/certificate', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                var certificate = response;
                if ($scope.certificateStudents.has(certificate.id)) {
                    $scope.certificateStudents.get(certificate.id).push(student);
                }
                else {
                    $scope.certificates.set(certificate.id, certificate);
                    $scope.certificateStudents.set(certificate.id, []);

                    $scope.certificateStudents.get(certificate.id).push(student);
                }

                //laatste student dus start met de json string te vullen
                if (i == $scope.students.size - 1) {
                    $scope.fillJsonStudents();
                    $scope.buildTree();
                }
                i++;
            });
        });
    }

    $scope.fillJsonStudents = function () {
        $scope.jsonStudents += '[';
        var i = 0;
        $scope.certificateStudents.forEach(function (students, key) {
            var certificate = $scope.certificates.get(key);
            $scope.jsonStudents += '{"text": "' + certificate.name + '",' +
            '"certificateId": "' +  certificate.id + '", ' + ' "nodes": [';

            var allStudentChecked = $scope.addStudents(students, certificate);
            $scope.jsonStudents += '], ' +
                '"state":{"checked": ' + allStudentChecked + '}}';

            if (i < $scope.certificateStudents.size - 1) {
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

            if($scope.studentInClass(students[i].id)){
                inClass = true;
            }
            else if(allChecked){
                allChecked = false;
            }

            $scope.jsonStudents += '{"text": "' + students[i].firstName + ' ' + students[i].lastName + '", ' +
                    '"parent": "' +  certificate.id + '", ' +
                '"state":{"checked": ' + inClass + '}, ' +
                '"studentId": "' + students[i].id + '"}';

            if (i < students.length - 1) {
                $scope.jsonStudents += ',';
            }
        }
        return allChecked;
    }

    $scope.studentInClass = function(studentId){
        for(var i = 0; i < $scope.studentsInClass.length; i++){
            if($scope.studentsInClass[i] == studentId){
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
                    $http({
                        method: 'POST', url: $scope.SERVER_ADDRESS + 'class/id/' + $scope.classId + '/student/' + node.studentId,
                        headers: {'X-auth': $cookies.get("auth")}
                    }).success(function (response) {
                            if($scope.allSiblingsChecked(node)){
                                var parent = $('#treeview-checkable').treeview('getParent', node);
                                $('#treeview-checkable').treeview('checkNode', [ parent.nodeId, { silent: true } ]);
                            }

                            $scope.createAlertCookie('Student toegevoegd.');
                    });
                }
                else{
                    $http({
                        method: 'POST', url: $scope.SERVER_ADDRESS + 'class/id/' + $scope.classId + '/students/certificate/id/' + node.certificateId,
                        headers: {'X-auth': $cookies.get("auth")}
                    }).success(function (response) {

                        $scope.checkChildNodes(node);
                        $scope.createAlertCookie('Studenten toegevoegd.');
                    });
                }
            },
            onNodeUnchecked: function (event, node) {
                if(node.parent != null) {
                    $http({
                        method: 'DELETE', url: $scope.SERVER_ADDRESS + 'class/id/' + $scope.classId + '/student/' + node.studentId,
                        headers: {'X-auth': $cookies.get("auth")}
                    }).success(function (response) {
                            if($scope.allSiblingsChecked(node)){
                                var parent = $('#treeview-checkable').treeview('getParent', node);
                                $('#treeview-checkable').treeview('uncheckNode', [ parent.nodeId , { silent: true } ]);
                            }

                        $scope.createAlertCookie('Student verwijderd.');
                    });
                }
                else{
                    $http({
                        method: 'DELETE', url: $scope.SERVER_ADDRESS + 'class/id/' + $scope.classId + '/students/certificate/id/' + node.certificateId,
                        headers: {'X-auth': $cookies.get("auth")}
                    }).success(function (response) {

                        $scope.uncheckChildNodes(node);
                        $scope.createAlertCookie('Studenten verwijderd.');
                    });
                }
            }
        });

        $scope.checkChildNodes = function(parentNode){
            parentNode.nodes.forEach(function(node){
                if(!node.state.checked){
                    $('#treeview-checkable').treeview('checkNode', [ node.nodeId, { silent: true } ]);
                }
            });
        }

        $scope.uncheckChildNodes = function(parentNode){
            parentNode.nodes.forEach(function(node){
                if(node.state.checked){
                    $('#treeview-checkable').treeview('uncheckNode', [ node.nodeId, { silent: true } ]);
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


