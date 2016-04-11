app.controller('MainController', function ($scope, $location) {
    // TODO finish controller
    $scope.user = {
        Type: Object.freeze({
            ADMIN: 'Administrator',
            TEACHER: 'Leerkracht',
            STUDENT: 'Student',
            PARENT: 'Ouder'
        }),

        type: null,

        isLoggedIn: function () {
            return this.type !== null;
        },

        logOut: function () {
            this.type = null;
        }
    };

    // TODO remove when finished developing
    $scope.user.type = $scope.user.Type.ADMIN;

    $scope.location = {
        HOME: 'startpagina',
        NOT_FOUND: 'pagina_niet_gevonden',
        ACCESS_DENIED: 'toegang_geweigerd',
        USER_MANAGEMENT: 'gebruikersbeheer',
        COURSES: 'opleidingen',
        MY_CLASSES: 'mijn_klassen',
        SCORES_MANAGEMENT: 'puntenbeheer',
        STUDENT_PROGRESS: 'voortgang_studenten',

        PARAM_EDIT_USER_ID: 'id',
        PARAM_CREATE_NEW_USER: 'nieuw',

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
            // TODO remove when finished developing
            return true;

            if (page === '') {
                return true;
            } else if (page === this.USER_MANAGEMENT) {
                return $scope.user.type === $scope.user.Type.ADMIN;
            } else if (page === this.COURSES) {
                return $scope.user.type === $scope.user.Type.ADMIN;
            } else if (page === this.MY_CLASSES) {
                return $scope.user.type === $scope.user.Type.TEACHER;
            } else if (page === this.SCORES_MANAGEMENT) {
                return $scope.user.type === $scope.user.Type.TEACHER;
            } else if (page === this.STUDENT_PROGRESS) {
                return $scope.user.type === $scope.user.Type.TEACHER
                    || $scope.user.type === $scope.user.Type.STUDENT
                    || $scope.user.type === $scope.user.Type.PARENT;
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
