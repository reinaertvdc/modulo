app.controller('LogInController', function ($scope, $http, $base64, $cookies) {
    // TODO finish controller
    $scope.formData = {};
    $scope.submitForm = function () {
        var auth = $scope.formData.email + ":" + $scope.formData.password;
        auth = $base64.encode(auth);

        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'auth',
            headers: {'X-auth': auth }
        }).success(function (response) {
            $cookies.put('auth', auth);
            $cookies.putObject('user', response);

            $scope.account.logIn();
        }).error(function (response, code){
            $scope.createAlertCookie('Ongeldig e-mailadres en/of wachtwoord. De ingegeven combinatie bestaat niet.');
        });
    };

});
