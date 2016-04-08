app.controller('LogInController', function ($scope) {
    $scope.formData = {};

    $scope.submitForm = function () {
        if ($scope.formData.email === 'admin@tihh.be' && $scope.formData.password === 'supersecret') {
            $scope.user.type = $scope.user.Type.ADMIN;
        } else if ($scope.formData.email === 'jan.spaepen@tihh.be' && $scope.formData.password === 'mypassword') {
            $scope.user.type = $scope.user.Type.TEACHER;
        } else if ($scope.formData.email === 'jonas223@gmail.com' && $scope.formData.password === '1234') {
            $scope.user.type = $scope.user.Type.STUDENT;
        } else if ($scope.formData.email === 'peter.opdebeeck@outlook.com' && $scope.formData.password === '4321') {
            $scope.user.type = $scope.user.Type.PARENT;
        } else {
            alert('Ongeldig e-mailadres/wachtwoord');
        }
    };
});
