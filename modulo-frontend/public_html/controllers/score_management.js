updateTableHeaders = function() {
    var tables = document.getElementsByClassName('fixed-head');
    for (var i = 0; i < tables.length; i++) {
        var head = tables[i].getElementsByTagName('thead')[0];
        head.style.marginLeft = '-' + (tables[i].scrollLeft + 0) + 'px';
    }
};

app.controller('ScoreManagementController', function ($scope, $http, $cookies, $window) {
    // TODO implement controller
    $scope.bgvClasses = [];
    $scope.pavClasses = [];

    $scope.visibleScores = {
        schoolClass: null,
        module: null,
        week: 1
    };

    $scope.previousModule = null;

    $scope.studentScoresTable = [];

    $scope.resetstudentScoresTable = function () {
        $scope.studentScoresTable = [];
        if ($scope.visibleScores.module == null) {
            return;
        }
        var subCertificateCategories = $scope.visibleScores.module.subCertificateCategories;
        var goalIndex = 0;
        if ($scope.visibleScores.schoolClass.type == 'BGV') {
            for (var categoryIndex = 0; categoryIndex < subCertificateCategories.length; categoryIndex++) {
                var competences = subCertificateCategories[categoryIndex].competences;
                for (var competenceIndex = 0; competenceIndex < competences.length; competenceIndex++) {
                    $scope.studentScoresTable[goalIndex] = [];
                    for (var studentIndex = 0; studentIndex < $scope.visibleScores.schoolClass.students.length; studentIndex++) {
                        $scope.studentScoresTable[goalIndex][studentIndex] = {};
                        if (!$scope.visibleScores.schoolClass.students[studentIndex].scores[$scope.visibleScores.week]) {
                            $scope.visibleScores.schoolClass.students[studentIndex].scores[$scope.visibleScores.week] = [];
                        }
                        $scope.studentScoresTable[goalIndex][studentIndex].score = $scope.visibleScores.schoolClass.students[studentIndex].scores[$scope.visibleScores.week][competences[competenceIndex].id];
                        $scope.studentScoresTable[goalIndex][studentIndex].competence = competences[competenceIndex].id;
                        $scope.studentScoresTable[goalIndex][studentIndex].selected = false;
                    }
                    goalIndex++;
                }
            }
        } else {

        }
    };

    $http({
        method: 'GET', url: 'http://localhost:8080/user/id/'+ $cookies.getObject('user').id +'/teaching',
        headers: {'X-auth': $cookies.get('auth')}
    }).success(function (schoolClasses) {
        schoolClasses.forEach(function (schoolClass) {
            var clazz = {};
            clazz.type = schoolClass.type;
            clazz.schoolClass = schoolClass;
            clazz.modules = [];
            if (schoolClass.type == 'BGV') {
                $http({
                    method: 'GET', url: 'http://localhost:8080/class/id/'+ schoolClass.id +'/certificate',
                    headers: {'X-auth': $cookies.get('auth')}
                }).success(function (certificate) {
                    $http({
                        method: 'GET', url: 'http://localhost:8080/certificate/id/'+ certificate.id +'/subcertificates',
                        headers: {'X-auth': $cookies.get('auth')}
                    }).success(function (subCertificates) {
                        subCertificates.forEach(function (subCertificate) {
                            clazz.modules.push(subCertificate);
                        });
                    });
                });
            } else {
                $http({
                    method: 'GET', url: 'http://localhost:8080/class/id/'+ schoolClass.id +'/coursetopics',
                    headers: {'X-auth': $cookies.get('auth')}
                }).success(function (courseTopics) {
                    courseTopics.forEach(function (courseTopic) {
                        $http({
                            method: 'GET', url: 'http://localhost:8080/coursetopic/id/' + courseTopic.id + '/objectives',
                            headers: {'X-auth': $cookies.get('auth')}
                        }).success(function (objectives) {
                            objectives.forEach(function (objective) {
                                clazz.modules.push(courseTopic);
                            });
                        });
                    });
                });
            }
            $http({
                method: 'GET', url: 'http://localhost:8080/class/id/'+ schoolClass.id +'/students',
                headers: {'X-auth': $cookies.get('auth')}
            }).success(function (students) {
                clazz.students = [];
                students.forEach(function (student) {
                    var tempStudent = {};
                    tempStudent.student = student;
                    tempStudent.scores = [];
                    $http({
                        method: 'GET', url: 'http://localhost:8080/score/id/' + student.id + '/' + clazz.type.toLowerCase(),
                        headers: {'X-auth': $cookies.get('auth')}
                    }).success(function (scores) {
                        scores.forEach(function (score) {
                            if (!tempStudent.scores[score.week]) {
                                tempStudent.scores[score.week] = [];
                            }
                            if (schoolClass.type == 'BGV') {
                                tempStudent.scores[score.week][score.competence.id] = score;
                            } else {
                                tempStudent.scores[score.week][score.objective.id] = score;
                            }
                        });
                    });
                    clazz.students.push(tempStudent);
                })
            });
            if (clazz.type == 'BGV') {
                $scope.bgvClasses.push(clazz);
            } else if (clazz.type == 'PAV') {
                $scope.pavClasses.push(clazz);
            }
        })
    });

    $scope.getWeeks = function() {
        var result = [];
        for (var i = 1; i <= 38; i++) {
            result.push(i);
        }
        return result;
    };
    
    $scope.updateTableHeaders = function() {
        var tables = document.getElementsByClassName('fixed-head');
        for (var i = 0; i < tables.length; i++) {
            var head = tables[i].getElementsByTagName('thead')[0];
            var stubHead = tables[i].getElementsByClassName('stub-head')[0];
            var headCells = head.getElementsByTagName('th');
            var stubHeadCells = stubHead.getElementsByTagName('th');
            for (var j = 0; j < headCells.length; j++) {
                headCells[j].style.width = stubHeadCells[j].offsetWidth + 'px';
            }
        }
    };

    angular.element($window).bind('resize', function() {$scope.updateTableHeaders()});
    setInterval(function() {
        if ($scope.previousModule == $scope.visibleScores.module) {
            return;
        }
        $scope.previousModule = $scope.visibleScores.module;
        $scope.updateTableHeaders();
    }, 200);

    $scope.updateScores = function() {
        var subCertificateCategories = $scope.visibleScores.module.subCertificateCategories;
        var goalIndex = 0;
        var model = {};
        model.score = $scope.selectedScoreId;
        model.week = $scope.visibleScores.week;
        model.remarks = $scope.comment;
        model = JSON.stringify(model);
        for (var categoryIndex = 0; categoryIndex < subCertificateCategories.length; categoryIndex++) {
            var competences = subCertificateCategories[categoryIndex].competences;
            for (var competenceIndex = 0; competenceIndex < competences.length; competenceIndex++) {
                for (var studentIndex = 0; studentIndex < $scope.visibleScores.schoolClass.students.length; studentIndex++) {
                    if ($scope.studentScoresTable[goalIndex][studentIndex].selected) {
                        $http({
                            method: 'POST', url: 'http://localhost:8080/score/id/' + $scope.visibleScores.schoolClass.students[studentIndex].student.id + '/bgv/' + $scope.studentScoresTable[goalIndex][studentIndex].competence, data: model,
                            headers: {'X-auth': $cookies.get("auth")}
                        }).success(function (response) {});
                        if (!$scope.studentScoresTable[goalIndex][studentIndex].score) {
                            $scope.studentScoresTable[goalIndex][studentIndex].score = {};
                        }
                        $scope.studentScoresTable[goalIndex][studentIndex].score.score = $scope.selectedScoreId;
                        $scope.studentScoresTable[goalIndex][studentIndex].score.remarks = model.remarks;
                        $scope.studentScoresTable[goalIndex][studentIndex].selected = false;
                    }
                }
                goalIndex++;
            }
        }
    }
});
