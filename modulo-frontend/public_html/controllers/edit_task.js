app.controller('EditTaskController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    var paramVal = $scope.location.getParameter($scope.location.PARAM_EDIT_TASK_ID);


    $scope.task = {
        name: '',
        deadline: '',
        description: '',
        clazz: {id: null}
    };


    // PAV classes
    $scope.PAVClasses = {};  // gets filled later
    $scope.setSelectedPAVClass = function (PAVClass) {
        $scope.selectedPAVClass = PAVClass.name;
        $scope.task.clazz.id = PAVClass.id;
    };


    // ACTUAL ACTIONS ON LOADED PAGE
    // load all PAV classes for this teacher
    $http({
        method: 'GET', url: 'http://localhost:8080/user/id/' + $cookies.getObject("user").id + '/teaching/type/PAV',
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        response.forEach(function (item) {
            $scope.PAVClasses[item.id] = item;
        });

        // select first class if 'new'
        if (paramVal == 'nieuw') {
            var firstKey = (Object.keys($scope.PAVClasses)[0]);
            $scope.selectedPAVClass = $scope.PAVClasses[firstKey].name;  // get first element in map  (anywhere else the Map is needed; array not feasible)
            $scope.task.clazz.id = $scope.PAVClasses[firstKey].id;
        }
    });


    // new task
    if (paramVal == 'nieuw') {
        $scope.btnText = 'Aanmaken';
        $scope.panelCaption = 'Nieuwe taak aanmaken';
        $scope.deadlineDate = new Date();
    }
    // edit task
    else if (paramVal) {
        $scope.btnText = 'Opslaan';
        $http.get('http://localhost:8080/task/id/' + paramVal, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            $scope.task = response;
            $scope.deadlineDate = new Date($scope.task.deadline);
            $scope.panelCaption = 'Taak bewerken: ' + $scope.task.name;
            $scope.selectedPAVClass = $scope.task.clazz.name;
        });
    };


    // ON SUBMIT
    $scope.submitForm = function () {
        $scope.task.deadline = $scope.deadlineDate.toISOString().substring(0, 10);  // extract date in format yyyy-MM-dd
        
        if (paramVal == 'nieuw') {
            $http({
                method: 'POST', url: 'http://localhost:8080/task/', data: $scope.task,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.location.openPage($scope.location.TASKS);
                $scope.createAlertCookie('Taak toegevoegd.');
            });

        } else if (paramVal) {
            $http({
                method: 'PUT', url: 'http://localhost:8080/task/', data: $scope.task,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.location.openPage($scope.location.TASKS);
                $scope.createAlertCookie('Taak bewerkt.');
            });
        }
    };


    // DATE CONTROLLER
    //  "$scope.deadlineDate" is defined on load (its value depends on param 'new' or 'edit')

    $scope.clear = function () {
        $scope.deadlineDate = null;
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

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function () {
        $scope.popup1.opened = true;
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
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }
});