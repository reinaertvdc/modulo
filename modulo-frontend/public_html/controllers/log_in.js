app.controller('LogInController', function ($scope, $http, $base64, $cookies) {
    // TODO finish controller
    $scope.formData = {};
    $cookies.put('auth', "");
    $cookies.putObject('user', null);
    $scope.submitForm = function () {
        var auth = $scope.formData.email + ":" + $scope.formData.password;
        auth = $base64.encode(auth);

        $http.get('http://localhost:8080/auth', {headers: {'X-auth': auth}}).success(function (response){
            $cookies.put('auth', auth);
            $cookies.putObject('user', response);

        }).error(function (response, code){
            showAlert(AlertType.DANGER, 'Ongeldig e-mailadres/wachtwoord.', 'Het ingegeven e-mailadres en wachtwoord komen niet overeen.', true);
        });
    };

});
