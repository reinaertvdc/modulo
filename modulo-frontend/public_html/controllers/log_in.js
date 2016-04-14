app.controller('LogInController', function ($scope) {
    // TODO finish controller
    $scope.formData = {};

    $scope.submitForm = function () {
        if (!$scope.account.attemptLogin($scope.formData.email, $scope.formData.password)) {
            showAlert(AlertType.DANGER, 'Ongeldig e-mailadres/wachtwoord.', 'Het ingegeven e-mailadres en wachtwoord komen niet overeen.', true);
        }
    };
});
