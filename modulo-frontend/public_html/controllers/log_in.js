app.controller('LogInController', function ($scope, $http) {
    // TODO finish controller
    $scope.formData = {};

    $scope.submitForm = function () {
        $http.get('http://localhost:8080/account/' + $scope.formData.email + '/').success(function (response) {
            console.log(response.userEntity);

            if (response.userEntity.password !== $scope.formData.password)
                showAlert(AlertType.DANGER, 'Ongeldig e-mailadres/wachtwoord.', 'Het ingegeven e-mailadres en wachtwoord komen niet overeen.', true);
            else
                $scope.account.user = response.userEntity;
        });

    };
});
