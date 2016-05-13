app.controller('NewClassController', function ($scope, $http, $cookies) {
        // TODO implement controller

    $scope.classType = $scope.location.getParameter($scope.location.PARAM_CLASS_TYPE);

    if($scope.classType == "BGV") {
        // CERTIFICATES
        $scope.certificates = {};
        $scope.setSelectedCert = function (cert) {
            $scope.selectedCert = cert.name;
            $scope.certificateId = cert.id;

            $scope.getCertificateStudents();
        };

        $http.get('http://localhost:8080/certificate/enabled/' + true, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            response.forEach(function (item) {
                $scope.certificates[item.id] = item;
            });

            $scope.certificateId = null;
            $scope.selectedCert = "Selecteer een certificaat";
        });
    }else if($scope.classType == "PAV"){
        // GRADES
        $scope.grades = {};
        $scope.setSelectedGrade = function (grade) {
            $scope.selectedGrade = grade.name;
            $scope.gradeId = grade.id;

            $scope.getGradeStudents();
        };

        $http.get('http://localhost:8080/grade/all', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            response.forEach(function (item) {
                $scope.grades[item.id] = item;
            });

            $scope.gradeId = null;
            $scope.selectedGrade = "Selecteer een graad";
        });
    }

    $scope.submitForm = function () {
        if($scope.classType=="BGV" && $scope.checkCertificateId()){

        }
        else if($scope.classType=="PAV" && $scope.checkGradeId()){

        }
    }

    $scope.checkCertificateId = function(){
        var valid = true;

        if($scope.certificateId == null) {
            valid = false;
            $scope.createAlertCookie('Certificaat is niet ingevuld.');
        }

        return valid;
    }

    $scope.checkGradeId = function(){
        var valid = true;

        if($scope.gradeId == null) {
            valid = false;
            $scope.createAlertCookie('Graad is niet ingevuld.');
        }

        return valid;
    }

    $scope.students = new Map();
    $scope.jsonStudents = "";

    $scope.getCertificateStudents = function(){
        $http.get('http://localhost:8080/certificate/id/' + $scope.certificateId + '/students', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            response.forEach(function (item) {
                $scope.students.set(item.id, item);
            });
            $scope.categorizeStudentsByCertificate();
        });
    }

    $scope.getGradeStudents = function(){
        $http.get('http://localhost:8080/grade/id/' + $scope.gradeId + '/students', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            response.forEach(function (item) {
                $scope.students.set(item.id, item);
            });
            $scope.categorizeStudentsByCertificate();
        });
    }

    $scope.categorizeStudentsByCertificate = function () {
        $scope.certificateStudents = new Map();
        $scope.certificates = new Map();
        var i = 0;
        $scope.students.forEach(function (student, key) {

            $http.get('http://localhost:8080/user/id/'+student.id+'/certificate', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
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

            $scope.addStudents(students, certificate);
            $scope.jsonStudents += '], ' +
                '"state":{"checked": false}}';

            if (i < $scope.certificateStudents.size - 1) {
                $scope.jsonStudents += ',';
            }
            i++;
        }, $scope.students);
        $scope.jsonStudents += ']';
    }

    $scope.addStudents = function (students, certificate) {
        for (var i = 0; i < students.length; i++) {
            $scope.jsonStudents += '{"text": "' + students[i].firstName + ' ' + students[i].lastName + '", ' +
                '"parent": "' +  certificate.id + '", ' +
                '"state":{"checked": false}, ' +
                '"studentId": "' + students[i].id + '"}';

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
                    node.nodes.forEach(function(node){
                        if(!node.state.checked){
                            $('#treeview-checkable').treeview('checkNode', [ node.nodeId, { silent: true } ]);
                        }
                    });
                }
            },
            onNodeUnchecked: function (event, node) {
                if(node.parent != null) {
                    if(!$scope.allSiblingsChecked(node)){
                        var parent = $('#treeview-checkable').treeview('getParent', node);
                        $('#treeview-checkable').treeview('uncheckNode', [ parent.nodeId , { silent: true } ]);
                    }
                }
                else{
                    node.nodes.forEach(function(node){
                        if(node.state.checked){
                            $('#treeview-checkable').treeview('uncheckNode', [ node.nodeId, { silent: true } ]);
                        }
                    });
                }
            }
        });

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
        });

        //Expand all
        $scope.checkableTree.treeview('expandAll', {silent: true});
    }

});