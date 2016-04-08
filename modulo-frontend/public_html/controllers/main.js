app.controller('MainController', function ($scope, $location) {
    $scope.user = {
        Type: Object.freeze({
            ADMIN: 0,
            TEACHER: 1,
            STUDENT: 2,
            PARENT: 3
        }),

        type: null,

        isLoggedIn: function () {
            return this.type !== null;
        },

        logOut: function () {
            this.type = null;
        }
    };

    $scope.user.type = $scope.user.Type.ADMIN;

    $scope.location = {
        HOME: 'startpagina',
        NOT_FOUND: 'pagina_niet_gevonden',
        ACCESS_DENIED: 'toegang_geweigerd',
        USER_MANAGEMENT: 'gebruikersbeheer',
        GRADES_CERTIFICATES: 'graden_opleidingen',
        MY_CLASSES: 'mijn_klassen',
        SCORES_MANAGEMENT: 'puntenbeheer',
        STUDENT_PROGRESS: 'voortgang_studenten',

        pathToPanel: function (path) {
            return path.replace(/\//g, '');
        },

        getRequestedPanel: function () {
            return this.pathToPanel($location.path());
        },

        panelExists: function (panel) {
            return panel === ''
                || panel === this.USER_MANAGEMENT
                || panel === this.GRADES_CERTIFICATES
                || panel === this.MY_CLASSES
                || panel === this.SCORES_MANAGEMENT
                || panel === this.STUDENT_PROGRESS
        },

        userCanAccessPanel: function (panel) {
            if (panel === '') {
                return true;
            } else if (panel === this.USER_MANAGEMENT) {
                return $scope.user.type === $scope.user.Type.ADMIN;
            } else if (panel === this.GRADES_CERTIFICATES) {
                return $scope.user.type === $scope.user.Type.ADMIN;
            } else if (panel === this.MY_CLASSES) {
                return $scope.user.type === $scope.user.Type.TEACHER;
            } else if (panel === this.SCORES_MANAGEMENT) {
                return $scope.user.type === $scope.user.Type.TEACHER;
            } else if (panel === this.STUDENT_PROGRESS) {
                return $scope.user.type === $scope.user.Type.TEACHER
                    || $scope.user.type === $scope.user.Type.STUDENT
                    || $scope.user.type === $scope.user.Type.PARENT;
            }

            return false;
        },

        getPanelToOpen: function () {
            if (!this.panelExists(this.getRequestedPanel())) {
                return this.NOT_FOUND;
            } else if (!this.userCanAccessPanel(this.getRequestedPanel())) {
                return this.ACCESS_DENIED;
            } else if (this.getRequestedPanel() === '') {
                return this.HOME;
            }

            return this.getRequestedPanel();
        },

        getUrlToPanelToOpen: function() {
            return 'views/panels/' + this.getPanelToOpen() + '.html';
        },

        openPanel: function (panel) {
            $location.path(panel);
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
