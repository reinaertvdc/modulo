app.controller('ManageClassDetailsController', function ($scope, $http, $cookies) {
    // TODO implement controller
    $scope.className = document.getElementById('className');
    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);


    $scope.saveDetails = function(){
        $scope.class.name =  $scope.className.value;
        var model = JSON.stringify($scope.class);

        $http({
            method: 'PUT', url: 'http://localhost:8080/class/', data: model,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.createAlertCookie('Klas bewerkt.');
        });
    }

    $http.get('http://localhost:8080/class/id/' + $scope.classId, {headers: {'X-Auth': $cookies.get("auth")}}).success(function (response) {
        $scope.class = response;
        $scope.className.value = $scope.class.name;
    });
});