app.controller('MainController', function ($scope, $location, $base64, $cookies, $http) {
    // TODO finish controller
    $scope.account = {
        isLoggedIn: function () {
            return  $cookies.get("auth") != null;
        },

        logOut: function () {
            $cookies.putObject('user', null, {'expires': new Date()});
            $cookies.put("auth", null, {'expires': new Date()});
        }
    };

    $scope.getActiveMyClassesPanel = function () {
        if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID)) {
            if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID) == $scope.location.PARAM_CREATE_NEW_CLASS_ID && $scope.location.getParameter($scope.location.PARAM_CLASS_TYPE)) {
                return 'views/panels/new_class.html';
            }
            if ($scope.location.getParameter($scope.location.PARAM_MANAGE_COURSE_TOPIC_ID)) {
                return 'views/panels/manage_course_topic.html';
            } else {
                if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_MEMBERS)) {
                    return 'views/panels/manage_class_members.html';
                } else if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID) != $scope.location.PARAM_CREATE_NEW_CLASS_ID) {
                    return 'views/panels/manage_class.html';
                } else {
                    return 'views/panels/list_classes.html';
                }
            }
        } else {
            return 'views/panels/list_classes.html';
        }
    };

    $scope.location = {
        HOME: 'startpagina',
        NOT_FOUND: 'pagina_niet_gevonden',
        ACCESS_DENIED: 'toegang_geweigerd',
        USER_MANAGEMENT: 'gebruikersbeheer',
        USER_MANAGEMENT_EDIT: 'bewerkt',
        COURSES: 'certificaten',
        MY_CLASSES: 'mijn_klassen',
        SCORES_MANAGEMENT: 'puntenbeheer',
        STUDENT_PROGRESS: 'voortgang',
        TASK_MANAGEMENT: 'taken',

        PARAM_EDIT_USER_ID: 'gebruiker',
        PARAM_MANAGE_CLASS_ID: 'klas',
        PARAM_CREATE_NEW_CLASS_ID: 'nieuw',
        PARAM_CLASS_TYPE: 'type',
        PARAM_MANAGE_COURSE_TOPIC_ID: 'vakthema',
        PARAM_CREATE_NEW_COURSE_TOPIC_ID: 'nieuw',
        PARAM_MANAGE_CLASS_MEMBERS: 'leerlingen',
        PARAM_EDIT_TASK_ID: 'taak',
        PARAM_TASK_SCORES: 'taakscores',

        pathToPage: function (path) {
            return path.replace(/\//g, '');
        },

        getRequestedPage: function () {
            return this.pathToPage($location.path());
        },

        pageExists: function (page) {
            return page === ''
                || page === this.USER_MANAGEMENT
                || page === this.COURSES
                || page === this.MY_CLASSES
                || page === this.SCORES_MANAGEMENT
                || page === this.STUDENT_PROGRESS
                || page === this.TASK_MANAGEMENT
                || page === this.TASK_SCORES
        },

        userCanAccessPage: function (page) {

            // TODO uncomment when debugging
            //return true;

            if (page === '') {
                return true;
            } else if (!$scope.account.isLoggedIn()) {
                return false;
            } else if (page === this.USER_MANAGEMENT) {
                return $cookies.getObject("user").role === UserType.ADMIN;
            } else if (page === this.COURSES) {
                return $cookies.getObject("user").role === UserType.ADMIN;
            } else if (page === this.MY_CLASSES) {
                return $cookies.getObject("user").role === UserType.TEACHER;
            } else if (page === this.SCORES_MANAGEMENT) {
                return $cookies.getObject("user").role === UserType.TEACHER;
            } else if (page === this.STUDENT_PROGRESS) {
                return $cookies.getObject("user").role === UserType.TEACHER
                    || $cookies.getObject("user").role === UserType.STUDENT
                    || $cookies.getObject("user").role === UserType.PARENT;
            } else if (page === this.TASK_MANAGEMENT) {
                return $cookies.getObject("user").role === UserType.TEACHER
                    || $cookies.getObject("user").role === UserType.STUDENT
                    || $cookies.getObject("user").role === UserType.PARENT;
            }

            return false;
        },

        getPageToOpen: function () {
            if (!this.pageExists(this.getRequestedPage())) {
                return this.NOT_FOUND;
            } else if (!this.userCanAccessPage(this.getRequestedPage())) {
                return this.ACCESS_DENIED;
            } else if (this.getRequestedPage() === '') {
                return this.HOME;
            }

            return this.getRequestedPage();
        },

        getUrlToPageToOpen: function () {
            return 'views/pages/' + this.getPageToOpen() + '.html';
        },

        openPage: function (page) {
            this.removeAllParameters();
            $location.path(page);
        },

        getParameter: function (name) {
            return $location.search()[name];
        },

        setParameter: function (name, value) {
            return $location.search(name, value);
        },

        removeAllParameters: function (value) {
            $location.url($location.path());
        }

    };

    $scope.form = {
        isValidEmail: function (value) {
            return value.length > 0;
        },

        isValidPassword: function (value) {
            return value.length > 0;
        }
    };

    $scope.isConnectedToBackend = true;

    $scope.testedBackendConnectionSuccessfully = false;

    $scope.checkBackendConnection = function () {
        var auth = 'ping:ping';
        auth = $base64.encode(auth);
        $http({
            method: 'GET', url: 'http://localhost:8080/auth',
            headers: {'X-auth': auth }
        }).success(function (response) {
            $scope.isConnectedToBackend = true;
            $scope.testedBackendConnectionSuccessfully = true;
        }).error(function (response, code) {
            if (code > 0) {
                $scope.isConnectedToBackend = true;
                $scope.testedBackendConnectionSuccessfully = true;
            } else {
                $scope.isConnectedToBackend = false;
            }
        });
    };

    $scope.checkBackendConnectionLoop = function () {
        $scope.checkBackendConnection();
        setTimeout(function() {
            if (!$scope.testedBackendConnectionSuccessfully) {
                $scope.isConnectedToBackend = false;
                $scope.checkBackendConnectionLoop();
            }
        }, (3 * 1000));
    };

    $scope.checkBackendConnectionLoop();

    $scope.userRoles = {"STUDENT": "Student", "TEACHER": "Leerkracht", "ADMIN": "Beheerder", "PARENT": "Ouder"};
    $scope.userRolesKeys = Object.keys($scope.userRoles);

    $scope.userSexes = {"MALE": "Man", "FEMALE": "Vrouw"};
    $scope.userSexesKeys = Object.keys($scope.userSexes);


    $scope.createAlertCookie= function(msg){
        var alert = msg;
        var expireTime = new Date();
        var time = expireTime.getTime();
        time += 1000*3; // 3sec expire tijd
        expireTime.setTime(time);
        $cookies.put("alert", alert, {'expires': expireTime});
    }

    $scope.isAlertEmpty = function(){
        return $cookies.get("alert") != null;
    }

    $scope.getAlert = function(){
        return $cookies.get("alert");
    }

    $scope.removeAlert = function () {
        $cookies.put("alert", null, {'expires': new Date()});
    }

});
