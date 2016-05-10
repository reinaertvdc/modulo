app.controller('ScoreManagementController', function ($scope, $http, $cookies) {
    // TODO implement controller
    $scope.bgvClasses = [];
    $scope.pavClasses = [];

    $scope.visibleScores = {
        schoolClass: null,
        module: null,
        week: null
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
                        })
                    });
                });
            } else {
                // TODO download vakthema's
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
                    if (schoolClass.type == 'BGV') {
                        $http({
                            method: 'GET', url: 'http://localhost:8080/score/id/' + student.id + '/bgv',
                            headers: {'X-auth': $cookies.get('auth')}
                        }).success(function (bgvScores) {
                            tempStudent.bgvScores = [];
                            bgvScores.forEach(function (bgvScore) {
                                tempStudent.scores.push(bgvScore)
                            })
                        });
                    } else {
                        $http({
                            method: 'GET', url: 'http://localhost:8080/score/id/' + student.id + '/pav',
                            headers: {'X-auth': $cookies.get('auth')}
                        }).success(function (pavScores) {
                            tempStudent.pavScores = [];
                            pavScores.forEach(function (pavScore) {
                                tempStudent.scores.push(pavScore)
                            })
                        });
                    }
                    clazz.students.push(tempStudent);
                })
            });
            console.log(clazz.type);
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
});

/*app.controller('DatepickerPopupDemoCtrl', function ($scope) {
    $scope.today = function() {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function() {
        $scope.dt = null;
    };

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: true
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

    $scope.toggleMin = function() {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function() {
        $scope.popup1.opened = true;
    };

    $scope.setDate = function(year, month, day) {
        $scope.dt = new Date(year, month, day);
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup1 = {
        opened: false
    };

    function getDayClass(data) {
        var date = data.date,
            mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0,0,0,0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }
});*/