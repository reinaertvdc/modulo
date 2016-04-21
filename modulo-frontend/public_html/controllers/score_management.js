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

    $scope.getScoreHierarchy = function() {
        var subjects = [
            {
                name: 'BGV',
                coursePlaceholder: 'Opleiding',
                schoolClassPlaceholder: 'Klas',
                modulePlaceholder: 'Deelcertificaat',
                courses: ['Metselaar', 'BouwplaatsMachinist', 'Kok', 'Kassier']
            },
            {
                name: 'PAV',
                coursePlaceholder: 'Graad',
                schoolClassPlaceholder: 'Klas',
                modulePlaceholder: 'Module',
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

});

app.controller('ExampleController', ['$scope', function($scope) {
    $scope.data = {
        singleSelect: null,
        multipleSelect: [],
        option1: 'option-1'
    };
}]);

/*
app.controller('MyCtrl', function($scope) {
    $scope.mySelections = [];

    $scope.myData = [{name: "Moroni", id: 1},
        {name: "Tiancum", id: 2},
        {name: "Jacob", id: 3},
        {name: "Nephi", id: 4},
        {name: "Akon", id: 5},
        {name: "Enos", id: 6}];

    $scope.gridOptions = {
        data: 'myData',
        selectedItems: $scope.mySelections,
        multiSelect: true,
        enableCellSelection: true,
        enableRowSelection: false,
        enableCellEdit: false,
        afterSelectionChange: function () {
            $scope.selectedIDs = [];

        }
    };

});*/

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