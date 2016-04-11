app.controller('EditUserController', function ($scope, $http, $window) {
    // TODO finish controller
    $scope.formData = {
        type: 'ADMIN'
    };

    $scope.resetForm = function () {
        $scope.formData.type = 'ADMIN';
    };

    $scope.submitForm = function () {
        $http.post('http://localhost:8080/user', JSON.stringify($scope.formData)).success(function () {
            $window.alert('Nieuwe gebruiker aangemaakt.');
            $scope.resetForm();
        });
    };

    if ($scope.location.getParameter($scope.location.PARAM_EDIT_USER_ID)) {
        $scope.panelCaption = 'Gebruiker bewerken';
    } else if ($scope.location.getParameter($scope.location.PARAM_CREATE_NEW_USER)) {
        $scope.panelCaption = 'Nieuwe gebruiker aanmaken';
    }
});
