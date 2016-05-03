app.controller('LogInController', function ($scope, $http, $base64, $cookies) {
    // TODO finish controller
    $scope.formData = {};
    $scope.submitForm = function () {
        var auth = $scope.formData.email + ":" + $scope.formData.password;
        auth = $base64.encode(auth);

        $http({
            method: 'GET', url: 'http://localhost:8080/auth',
            headers: {'X-auth': auth }
        }).success(function (response) {
            /*var expireTime = new Date();
            var time = expireTime.getTime();
            time += 60*1000*20; // 20min expire tijd
            expireTime.setTime(time);
            $cookies.put('auth', auth, {'expires': expireTime});
            $cookies.putObject('user', response, {'expires': expireTime});*/
            $cookies.put('auth', auth);
            $cookies.putObject('user', response);
        }).error(function (response, code){
            showAlert(AlertType.DANGER, 'Ongeldig e-mailadres/wachtwoord.', 'Het ingegeven e-mailadres en wachtwoord komen niet overeen.', true);
        });
    };

});
