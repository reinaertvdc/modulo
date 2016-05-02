app.controller('LogInController', function ($scope, $http, $base64) {
    // TODO finish controller
    $scope.formData = {};

    $scope.submitForm = function () {
        var auth = $scope.formData.email + ":" + $scope.formData.password;
        auth = $base64.encode(auth);

        $http.get('http://localhost:8080/auth', {headers: {'X-Auth': auth}}).success(function (response){
            console.log("Response " + response.email);
/*
            // Setting a cookie
            $cookies.put('auth', auth);
            // Retrieving a cookie
            var testCookie = $cookies.get('auth');
            console.log("Cookie: " + testCookie);
*/
        }).error(function (response, code){
            console.log("Error " + response);
            console.log("Code " + code);
        });
        /*
        $http.get('http://localhost:8080/auth' + $scope.formData.email + '/').success(function (response) {
            console.log("TEST" + $http.succes);
            console.log("TEST1: " + $http.error);

            if (response.userEntity.password !== $scope.formData.password)
                showAlert(AlertType.DANGER, 'Ongeldig e-mailadres/wachtwoord.', 'Het ingegeven e-mailadres en wachtwoord komen niet overeen.', true);
            else
                $scope.account.user = response.userEntity;

        });
*/
    };

});
