app.controller('ScoreManagementController', function ($scope) {
    // TODO implement controller
    $scope.scoreHierarchy = [];

    $scope.visibleScores = {
        subject: null,
        course: null,
        schoolClass: null,
        module: null,

        setSubject: function (subject) {
            this.subject = subject;
            this.course = null;
            this.schoolClass = null;
            this.module = null;
        },

        setCourse: function (course) {
            this.course = course;
            this.schoolClass = null;
            this.module = null;
        },

        setSchoolClass: function (schoolClass) {
            this.schoolClass = schoolClass;
        },

        setModule: function (module) {
            this.module = module;
        }
    };

    $scope.selectedScore = null;

    $scope.scores = [];

    $scope.getScoreHierarchy = function() {
        var subjects = [
            {
                name: 'BGV',
                coursePlaceholder: 'Certificaat',
                schoolClassPlaceholder: 'Klas',
                modulePlaceholder: 'Deelcertificaat',
                courses: ['Metselaar', 'BouwplaatsMachinist', 'Kok', 'Kassier']
            },
            {
                name: 'PAV',
                coursePlaceholder: 'Graad',
                schoolClassPlaceholder: 'Klas',
                modulePlaceholder: 'Vakthema',
                courses: ['1ste graad', '2de graad']
            }
        ];

        for (var subjectIndex = 0; subjectIndex < subjects.length; subjectIndex++) {
            var subject = subjects[subjectIndex];
            var newSubject = {name: subject.name, coursePlaceholder: subject.coursePlaceholder, schoolClassPlaceholder: subject.schoolClassPlaceholder, modulePlaceholder: subject.modulePlaceholder, courses: []};
            for (var courseIndex = 0; courseIndex < subject.courses.length; courseIndex++) {
                var course = subject.courses[courseIndex];
                var newCourse = {name: course, classes: [], modules: []};
                for (var i = 1; i <= 4; i++) {
                    newCourse.classes.push('Klas ' + i);
                }
                for (var i = 1; i <= 6; i++) {
                    newCourse.modules.push(subject.modulePlaceholder + ' ' + i);
                }
                newSubject.courses.push(newCourse);
            }
            $scope.scoreHierarchy.push(newSubject);
        }

        for (var i = 0; i < $scope.scoreHierarchy.length; i++) {
            console.log($scope.scoreHierarchy[i].name);
            for (var j = 0; j < $scope.scoreHierarchy[i].courses.length; j++) {
                console.log('    ' + $scope.scoreHierarchy[i].courses[j].name);
                for (var k = 0; k < $scope.scoreHierarchy[i].courses[j].classes.length; k++) {
                    console.log('        ' + $scope.scoreHierarchy[i].courses[j].classes[k]);
                }
                for (var k = 0; k < $scope.scoreHierarchy[i].courses[j].modules.length; k++) {
                    console.log('        ' + $scope.scoreHierarchy[i].courses[j].modules[k]);
                }
            }
        }
    };

    $scope.getScoreHierarchy();

    $scope.competences = [
        new Competence(1, 'Doelstelling 1', null),
        new Competence(2, 'Doelstelling 2', null),
        new Competence(3, 'Doelstelling 3', null),
        new Competence(4, 'Doelstelling 4', null),
        new Competence(5, 'Doelstelling 5', null)
    ];

    $scope.studentScores = [
        new StudentScores('Jonas Verlinden', [Score.PRACTICED, Score.ACQUIRED, Score.PRACTICED, Score.PRACTICED, Score.ACQUIRED]),
        new StudentScores('Niels Vanmunster', [Score.PRACTICED, Score.ACQUIRED, Score.PRACTICED, Score.ACQUIRED, Score.OFFERED]),
        new StudentScores('Mohammed Vannitsen', [Score.OFFERED, Score.OFFERED, Score.OFFERED, Score.OFFERED, Score.OFFERED]),
        new StudentScores('Elise Vanderkruis', [Score.PRACTICED, Score.OFFERED, Score.PRACTICED, Score.ACQUIRED, Score.ACQUIRED]),
        new StudentScores('Christophe Drozdzyniak', [Score.PRACTICED, Score.ACQUIRED, Score.OFFERED, Score.PRACTICED, Score.ACQUIRED]),
        new StudentScores('Ivan De Vadder', [Score.ACQUIRED, Score.PRACTICED, Score.PRACTICED, Score.ACQUIRED, Score.PRACTICED]),
        new StudentScores('Ward De Bever', [Score.OFFERED, Score.OFFERED, Score.OFFERED, Score.OFFERED, Score.OFFERED]),
        new StudentScores('Frank Wilfrank', [Score.ACQUIRED, Score.PRACTICED, Score.OFFERED, Score.ACQUIRED, Score.PRACTICED])
    ];
    
    $scope.selectedStudentScores = [];
});

app.controller('DatepickerPopupDemoCtrl', function ($scope) {
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
        dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
    };

    // Disable weekend selection
    function disabled(data) {
        var date = data.date,
            mode = data.mode;
        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

    $scope.toggleMin = function() {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function() {
        $scope.popup1.opened = true;
    };

    $scope.open2 = function() {
        $scope.popup2.opened = true;
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

    $scope.popup2 = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
            date: tomorrow,
            status: 'full'
        },
        {
            date: afterTomorrow,
            status: 'partially'
        }
    ];

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
});