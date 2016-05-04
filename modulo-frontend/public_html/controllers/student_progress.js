app.controller('StudentProgressController', function ($scope, $http, $cookies, $compile) {
    const HEADER_ELEMENT = document.getElementById('table-header');
    const SCORE_ELEMENT = document.getElementById('table-score-body');
    const WEEKS = 43;

    $scope.selectedValues = {
        class: null,
        student: null,
        subcertificate: null
    };

    $scope.scores = null;
    $scope.goals = null;
    $scope.tableRows = "";

    // Classes
    $scope.classes = {};
    $scope.setSelectedClass = function (clazz) {
        $scope.selectedClass = clazz.name;
        $scope.selectedValues.class = clazz;
        $scope.loadStudents(clazz.id);

        if(clazz.type === "BGV")
            $scope.loadSubcertificates($scope.selectedValues.class.id)

    };

    $http({
        method: 'GET', url: 'http://localhost:8080/user/id/' + $cookies.getObject('user').id + '/teaching',
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        response.forEach(function (item) {
            $scope.classes[item.id] = item;
        });
        var firstKey = (Object.keys($scope.classes)[0]);
        $scope.selectedClass = $scope.classes[firstKey].name;
    });


    // Student
    $scope.students = {};
    $scope.setSelectedStudent = function (student) {
        $scope.selectedStudent = student.firstName + " " + student.lastName;
        $scope.selectedValues.student = student;

        if ($scope.selectedValues.class.type === "PAV")
            $scope.initTable();
        else{
            if($scope.selectedValues.subcertificate !== null)
                $scope.initTable();
        }


    };

    $scope.loadStudents = function (classId) {
        $http({
            method: 'GET', url: 'http://localhost:8080/class/id/' + classId + '/students',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            response.forEach(function (item) {
                $scope.students[item.id] = item;
            });
            var firstKey = (Object.keys($scope.students)[0]);
            $scope.selectedStudent = $scope.students[firstKey].firstName + " " + $scope.students[firstKey].lastName;
        });
    };

    // subcertificates
    $scope.subcertificates = {};
    $scope.setSelectedSubcertificate = function (subcertificate) {
        $scope.selectedSubcertificate = subcertificate.name;
        $scope.selectedValues.subcertificate = subcertificate;
        $scope.initTable();
    };

    $scope.loadSubcertificates = function (classId) {
        $http({
            method: 'GET', url: 'http://localhost:8080/class/id/' + classId + '/certificate',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            response.subCertificates.forEach(function (item) {
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
        while (HEADER_ELEMENT.firstChild) {
            HEADER_ELEMENT.removeChild(HEADER_ELEMENT.firstChild);
        }
        HEADER_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.initTable = function () {
        $scope.initHeader();
        $scope.getScores();
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(SCORE_ELEMENT)($scope);
    };



    $scope.loadBGVScoreMatrix = function () {
        while (SCORE_ELEMENT.firstChild) {
            SCORE_ELEMENT.removeChild(SCORE_ELEMENT.firstChild);
        }
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
    };


    $scope.createCategoryRow = function (name) {
        $scope.tableRows += '<tr>' +
            '<td style="color:blue;">' + name + '</td>';

        for (i = 0; i < WEEKS; i++) {
             $scope.tableRows += '<td></td>';
        }

        $scope.tableRows += '</tr>'
    }


    $scope.loadPAVScoreMatrix = function () {
        while (SCORE_ELEMENT.firstChild) {
            SCORE_ELEMENT.removeChild(SCORE_ELEMENT.firstChild);
        }
        $scope.tableRows = "";

        $scope.goals.forEach(function (objective) {
            $scope.createPAVRow(objective);
        });

        var element = document.createElement('tr');
        SCORE_ELEMENT.appendChild(element);
        element.outerHTML = $scope.tableRows;
        $scope.refresh();
    };

    $scope.createBGVRow = function (competence) {
        $scope.tableRows += '<tr>' +
            '<td>' + competence.name + '</td>';

        if ($scope.scores === null) {
            for (i = 0; i < WEEKS; i++) {
                $scope.tableRows += '<td></td>';

            }
        } else {
            for (i = 0; i < WEEKS; i++) {
                $scope.tableRows += '<td>';
                $scope.scores.forEach(function (item) {
                    if (competence.name === item.competence.name && i === parseInt(item.week)) {
                        $scope.tableRows += '<span tooltip-class="customClass" tooltip-placement="top" uib-tooltip="Beschrijving: ' + item.remarks +'" >' + item.score + '</span>';
                    }
                });
                $scope.tableRows += '</td>';
            }
        }
        $scope.tableRows += '</tr>'
    };


    $scope.createPAVRow = function (objective) {
        $scope.tableRows += '<tr>' +
            '<td>' + objective.name + '</td>';

        if ($scope.scores === null) {
            for (i = 0; i < WEEKS; i++) {
                $scope.tableRows += '<td></td>';

            }
        } else {
            for (i = 0; i < WEEKS; i++) {
                $scope.tableRows += '<td>';
                $scope.scores.forEach(function (item) {
                    if (objective.name === item.objective.name && i === parseInt(item.week)) {
                        $scope.tableRows += '<span tooltip-class="customClass" tooltip-placement="top" uib-tooltip="Vakthema: Taal \nBeschrijving: ' + item.remarks +'" >' + item.score + '</span>';
                    }
                });
                $scope.tableRows += '</td>';
            }
        }
        $scope.tableRows += '</tr>'
    };

    $scope.getGoals = function () {
        if ($scope.selectedValues.subcertificate === null) {
            $http({
                method: 'GET', url: 'http://localhost:8080/class/id/' + $scope.selectedValues.class.id + '/grade',
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.goals = response.objectives;
                $scope.loadPAVScoreMatrix();
            })
        } else {
            $scope.goals = $scope.selectedValues.subcertificate.subCertificateCategories;
            $scope.loadBGVScoreMatrix();
        }
    };

    $scope.getScores = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/score/id/' + $scope.selectedValues.student.id + '/' + $scope.selectedValues.class.type.toLowerCase(),
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.scores = response;
            $scope.getGoals();
        }).error(function (response, code) {
            $scope.getGoals();
        });
    }
});
