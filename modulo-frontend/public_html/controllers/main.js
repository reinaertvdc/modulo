app.controller('MainController', function ($scope) {
    $scope.user = {
        Type: Object.freeze({
            ADMIN: 0,
            TEACHER: 1,
            STUDENT: 2,
            PARENT: 3
        }),

        type: null,
        email: null,

        isLoggedIn: function () {
            return this.type !== null;
        },

        logOut: function () {
            this.type = null;
            this.email = null;
        }
    };

    $scope.location = {
        Panel: Object.freeze({
            USER_MANAGEMENT: 1,
            GRADES_CERTIFICATES: 2,
            MY_CLASSES: 3,
            SCORES_MANAGEMENT: 4,
            STUDENT_PROGRESS: 5
        }),

        getPanel: function () {
            var result = 'views/panels/';

            if ($scope.location.panel == $scope.location.Panel.USER_MANAGEMENT) {
                result += 'user_management';
            } else if ($scope.location.panel == $scope.location.Panel.GRADES_CERTIFICATES) {
                result += 'grades_certificates';
            } else if ($scope.location.panel == $scope.location.Panel.MY_CLASSES) {
                result += 'my_classes';
            } else if ($scope.location.panel == $scope.location.Panel.SCORES_MANAGEMENT) {
                result += 'scores_management';
            } else if ($scope.location.panel == $scope.location.Panel.STUDENT_PROGRESS) {
                result += 'student_progress';
            } else {
                result += 'home';
            }
            
            result += '.html';
            return result;
        },

        userCanOpen: function (panel) {
            if ($scope.user.type === $scope.user.Type.ADMIN) {
                return panel === $scope.location.Panel.USER_MANAGEMENT || panel === $scope.location.Panel.GRADES_CERTIFICATES;
            } else if ($scope.user.type === $scope.user.Type.TEACHER) {
                return panel === $scope.location.Panel.MY_CLASSES || panel === $scope.location.Panel.SCORES_MANAGEMENT || panel === $scope.location.Panel.STUDENT_PROGRESS;
            } else if ($scope.user.type === $scope.user.Type.STUDENT) {
                return panel === $scope.location.Panel.STUDENT_PROGRESS;
            } else if ($scope.user.type === $scope.user.Type.PARENT) {
                return panel === $scope.location.Panel.STUDENT_PROGRESS;
            }
            return false;
        },

        panel: null
    }
});