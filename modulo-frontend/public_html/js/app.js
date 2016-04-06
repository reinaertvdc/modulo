angular.module('MyApp', ['ui.bootstrap'])
    .controller('CreateUserController', function ($scope, $http , $window) {

        $scope.formData = {};
        $scope.formData.type = 'STUDENT';


        $scope.submitForm = function () {
            console.log($scope.formData);
            $http.post('http://localhost:8080/user', JSON.stringify($scope.formData)).success(function () {
                $window.alert('It worked, check the database!');
            });
        };
    });