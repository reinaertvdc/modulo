app.controller('StudentProgressController', function ($scope, $http, $cookies, $compile, $timeout) {
    const HEADER_ELEMENT = document.getElementById('table-header');
    const SCORE_ELEMENT = document.getElementById('table-score-body');
    const WEEKS = 43;

    $scope.scores = null;
    $scope.goals = null;
    $scope.tableRows = "";

    $scope.selectedValues = {
        class: null,
        student: null,
        subcertificate: null
    };

    $scope.loadClassData = function () {
        var requestURL = "";
        if($cookies.getObject("user").role === UserType.STUDENT){
            $scope.selectedValues.student = $cookies.getObject("user");
            requestURL = $scope.SERVER_ADDRESS + '/user/id/' + $cookies.getObject('user').id + '/classes';
        }else if($cookies.getObject("user").role === UserType.PARENT){
            if($cookies.getObject("child") != null) {
                requestURL = $scope.SERVER_ADDRESS + '/user/id/' + $cookies.getObject("child").id + '/classes';
                $scope.selectedValues.student = $cookies.getObject("child");
            }
        }else{
            requestURL = $scope.SERVER_ADDRESS + '/user/id/' + $cookies.getObject('user').id + '/teaching';
        }
        $http({
            method: 'GET', url: requestURL,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            response.forEach(function (item) {
                $scope.classes[item.id] = item;
            });
            var firstKey = (Object.keys($scope.classes)[0]);
            $scope.selectedClass = $scope.classes[firstKey].name;
        });
    };

    $scope.loadClassData();

    $scope.$watch(function() { if($cookies.getObject("child") != undefined) {
                                    return $cookies.getObject("child").id;
                                }else
                                    return;
                                }, function(newValue) {
        if($cookies.getObject("child") != undefined) {
            $scope.refreshValues();
            $scope.loadClassData();
        }
    });

    $scope.refreshValues = function () {
        $scope.selectedValues.class = null;
        $scope.selectedValues.student = null;
        $scope.selectedValues.subcertificate = null;
        document.getElementById("subcertificateForm").style.display = "none";
        document.getElementById("tableContainer").style.overflow = "hidden";
        $scope.removeTableContent();
    };

    // Classes
    $scope.classes = {};
    $scope.setSelectedClass = function (clazz) {
        $scope.removeTableContent();
        $scope.selectedClass = clazz.name;
        $scope.selectedValues.class = clazz;

        if(clazz.type === "BGV") {
            document.getElementById("subcertificateForm").style.display = "inline-block";
            $scope.loadCertificates($scope.selectedValues.class.id)
        }else{
            $scope.subcertificates = {};
            $scope.selectedValues.subcertificate = null;
            document.getElementById("subcertificateForm").style.display = "none";
        }

        if($cookies.getObject("user").role === UserType.TEACHER) {
            $scope.loadStudents(clazz.id);
            document.getElementById("studentForm").style.display = "inline-block";
        }else
            $scope.getNewContent();


    };

    // Student
    $scope.students = {};
    $scope.setSelectedStudent = function (student) {
        $scope.selectedStudent = student.firstName + " " + student.lastName;
        $scope.selectedValues.student = student;
        $scope.getNewContent();

    };

    $scope.getNewContent = function () {
        $scope.removeTableContent();
        if ($scope.selectedValues.class.type === "PAV") {
            $scope.getScores();
        }else{
            if($scope.selectedValues.subcertificate !== null)
                $scope.getScores();
        }
    };

    $scope.loadStudents = function (classId) {
        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'class/id/' + classId + '/students',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.students = {};
            response.forEach(function (item) {
                $scope.students[item.id] = item;
            });
            var firstKey = (Object.keys($scope.students)[0]);
            $scope.selectedStudent = $scope.students[firstKey].firstName + " " + $scope.students[firstKey].lastName;
        });
    };

    $scope.removeTableContent = function () {
        while (HEADER_ELEMENT.firstChild) {
            HEADER_ELEMENT.removeChild(HEADER_ELEMENT.firstChild);
        }
        while (SCORE_ELEMENT.firstChild) {
            SCORE_ELEMENT.removeChild(SCORE_ELEMENT.firstChild);
        }

        $scope.scores = null;
    };

    // subcertificates
    $scope.subcertificates = {};
    $scope.setSelectedSubcertificate = function (subcertificate) {
        $scope.selectedSubcertificate = subcertificate.name;
        $scope.selectedValues.subcertificate = subcertificate;
        $scope.removeTableContent();
        $scope.getScores();
    };

    $scope.loadCertificates = function (classId) {
        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'class/id/' + classId + '/certificate',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.loadSubcertificates(response.id);
        });
    };

    $scope.loadSubcertificates = function (certificateId) {
        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'certificate/id/' + certificateId + '/subcertificates',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.subcertificates = {};
            response.forEach(function (item) {
                $scope.subcertificates[item.id] = item;
            });
            var firstKey = (Object.keys($scope.subcertificates)[0]);
            $scope.selectedSubcertificates = $scope.subcertificates[firstKey].name;
        });
    };


    $scope.initHeader = function () {
        var html = '<tr><th></th>';

        for (i = 0; i < WEEKS; i++) {
            html += '<th>Week ' + (i + 1) + '</th>';
        }

        html += '</tr>';
        var element = document.createElement('tr');
        HEADER_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    // BGV
    $scope.loadBGVScoreMatrix = function () {
        console.log("bgv");
        document.getElementById("tableContainer").style.overflow = "scroll";
        $scope.tableRows = "";

        $scope.goals.forEach(function (category) {
            if(category.name !== "ROOT") {
                $scope.createCategoryRow(category.name);
            }

            category.competences.forEach(function (item) {
                $scope.createBGVRow(item);
            })
        });

        var element = document.createElement('tr');
        SCORE_ELEMENT.appendChild(element);
        element.outerHTML = $scope.tableRows;
        $scope.refresh();
        $scope.initHeader();

    };


    $scope.createCategoryRow = function (name) {
        $scope.tableRows += '<tr>' +
            '<td colspan="{{'+WEEKS+1+'}}" style="background-color: rgba(0,0,0,0.06); font-weight: bold; text-align: left;">' + name + '</td>';

        $scope.tableRows += '</tr>'
    };

    $scope.createBGVRow = function (competence) {
        $scope.tableRows += '<tr>' +
            '<td class="fixed">' + competence.name + '</td>';

        if ($scope.scores === null) {
            for (i = 0; i < WEEKS; i++) {
                $scope.tableRows += '<td></td>';

            }
        } else {
            for (i = 0; i < WEEKS; i++) {
                var hulp = '';
                $scope.scores.forEach(function (item) {
                    if (competence.id === item.competence.id && i === parseInt(item.week)) {
                        hulp = '<td id="tableContent">'+ '<div tooltip-class="customClass" tooltip-placement="top" uib-tooltip="Beschrijving: ' + item.remarks +'">';
                        hulp += item.score + '</div>';
                        return;
                    }
                });
                if(hulp === '')
                    hulp = '<td></td>';
                $scope.tableRows += hulp;
                $scope.tableRows += '</td>';
            }
        }
        $scope.tableRows += '</tr>';
    };

    // PAV
    $scope.loadPAVScoreMatrix = function () {
        console.log("PAV");
        document.getElementById("tableContainer").style.overflow = "auto";
        $scope.tableRows = "";

        $scope.goals.forEach(function (objective) {
            $scope.createPAVRow(objective);
        });

        var element = document.createElement('tr');
        SCORE_ELEMENT.appendChild(element);
        element.outerHTML = $scope.tableRows;
        $scope.refresh();
        $scope.initHeader();

    };

    $scope.createPAVRow = function (objective) {
        $scope.tableRows += '<tr>' +
            '<td  class="fixed">' + objective.name + '</td>';
        
        if ($scope.scores === null) {
            for (i = 0; i < WEEKS; i++) {
                $scope.tableRows += '<td></td>';

            }
        } else {
            for (i = 0; i < WEEKS; i++) {
                var hulp = '';
                $scope.scores.forEach(function (item) {
                    if (objective.name === item.objective.name && i === parseInt(item.week)) {

                        hulp = '<td id="tableContent">'+ '<div tooltip-class="customClass" tooltip-placement="top" uib-tooltip="Vakthema: ';
                        if(item.courseTopic != null)
                             hulp += item.courseTopic.name+ ' \nBeschrijving: ' + item.remarks +'">';
                        else
                            hulp +=  'onbekend \nBeschrijving: onbekend">';
                        hulp += item.score + '</div>';
                    }
                });
                if(hulp === '')
                    hulp = '<td></td>';
                $scope.tableRows += hulp;
                $scope.tableRows += '</td>';
            }
        }
        $scope.tableRows += '</tr>'
    };

    $scope.getGrade = function () {
        if ($scope.selectedValues.subcertificate === null) {
            $http({
                method: 'GET', url: $scope.SERVER_ADDRESS + 'class/id/' + $scope.selectedValues.class.id + '/grade',
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $http({
                    method: 'GET', url: $scope.SERVER_ADDRESS + 'grade/id/' + response.id+ '/objectives',
                    headers: {'X-auth': $cookies.get("auth")}
                }).success(function (response) {
                    $scope.goals = response;
                    $scope.loadPAVScoreMatrix();
                })
            })
        } else {
            $scope.goals = $scope.selectedValues.subcertificate.subCertificateCategories;
            $scope.loadBGVScoreMatrix();
        }
    };

    $scope.getScores = function () {
        $scope.scores = null;
        if ($scope.selectedValues.student !== null) {
            $http({
                method: 'GET',
                url: $scope.SERVER_ADDRESS + 'score/id/' + $scope.selectedValues.student.id + '/' + $scope.selectedValues.class.type.toLowerCase(),
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.scores = response;
                $scope.getGrade();
            }).error(function (response, code) {
                $scope.getGrade();
            });
        }
    }

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(SCORE_ELEMENT)($scope);
    };


});
