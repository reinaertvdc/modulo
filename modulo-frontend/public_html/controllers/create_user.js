app.controller('CreateUserController', function ($scope, $http, $window) {
    $scope.formData = {};
    $scope.formData.type = 'ADMIN';

    $scope.resetForm = function () {
        $scope.formData = {};
        $scope.formData.type = 'ADMIN';
    };

    $scope.submitForm = function () {
        $http.post('http://localhost:8080/user', JSON.stringify($scope.formData)).success(function () {
            $window.alert('Nieuwe gebruiker aangemaakt.');
            $scope.resetForm();
        });
    };
});
