app.controller('LogInController', function ($scope) {
    // TODO finish controller
    $scope.formData = {};

    $scope.submitForm = function () {
        if (!$scope.account.attemptLogin($scope.formData.email, $scope.formData.password)) {
            alert('Ongeldig e-mailadres/wachtwoord');
        }
    };
});
