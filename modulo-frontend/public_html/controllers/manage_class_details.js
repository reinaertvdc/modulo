app.controller('ManageClassDetailsController', function ($scope, $http, $cookies) {
    // TODO implement controller
    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);

    $scope.class = {
        id: null,
        name: null
    };

    $scope.submitForm = function () {
        var model = JSON.stringify($scope.class);

        $http({
            method: 'PUT', url: $scope.SERVER_ADDRESS + 'class/', data: model,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.createAlertCookie('Klas bewerkt.');
        });
    }

    $http.get($scope.SERVER_ADDRESS + 'class/id/' + $scope.classId, {headers: {'X-Auth': $cookies.get("auth")}}).success(function (response) {
        var clazz = response;
        $scope.class.id = clazz.id;
        $scope.class.name = clazz.name;
    });
});