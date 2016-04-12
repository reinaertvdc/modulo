app.controller('MainController', function ($scope, $location) {
    // TODO finish controller
    $scope.account = {
        user: null,
        
        attemptLogin: function(email, password) {
            if (backend.attemptLogin(email, password)) {
                this.user = backend.getUser();
                return true;
            }
            return false;
        },

        isLoggedIn: function () {
            return this.user !== null;
        },

        logOut: function () {
            this.user = null;
        }
    };

    // TODO remove when finished developing
    $scope.account.attemptLogin('hilde.beerten@tihh.be', '1234');

    $scope.location = {
        HOME: 'startpagina',
        NOT_FOUND: 'pagina_niet_gevonden',
        ACCESS_DENIED: 'toegang_geweigerd',
        USER_MANAGEMENT: 'gebruikersbeheer',
        COURSES: 'opleidingen',
        MY_CLASSES: 'mijn_klassen',
        SCORES_MANAGEMENT: 'puntenbeheer',
        STUDENT_PROGRESS: 'voortgang_studenten',

        PARAM_EDIT_USER_ID: 'gebruiker',
        PARAM_CREATE_NEW_USER: 'nieuw',

        PARAM_MANAGE_CLASS_ID: 'klas',
        PARAM_CREATE_NEW_CLASS: 'nieuw',
        PARAM_MANAGE_COURSE_TOPIC_ID: 'vakthema',
        PARAM_CREATE_NEW_COURSE_TOPIC: 'nieuw',

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
        },

        userCanAccessPage: function (page) {
            if (page === '') {
                return true;
            } else if (!$scope.account.isLoggedIn()) {
                return false;
            } else if (page === this.USER_MANAGEMENT) {
                return $scope.account.user.details.type === UserType.ADMIN;
            } else if (page === this.COURSES) {
                return $scope.account.user.details.type === UserType.ADMIN;
            } else if (page === this.MY_CLASSES) {
                return $scope.account.user.details.type === UserType.TEACHER;
            } else if (page === this.SCORES_MANAGEMENT) {
                return $scope.account.user.details.type === UserType.TEACHER;
            } else if (page === this.STUDENT_PROGRESS) {
                return $scope.account.user.details.type === UserType.TEACHER
                    || $scope.account.user.details.type === UserType.STUDENT
                    || $scope.account.user.details.type === UserType.PARENT;
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

        getUrlToPageToOpen: function() {
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

        removeAllParameters: function () {
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
});
