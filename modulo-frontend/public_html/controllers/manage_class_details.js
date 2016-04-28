app.controller('ManageClassDetailsController', function ($scope, $http) {
    // TODO implement controller
    $scope.className = document.getElementById('className');
    $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);


    $scope.saveDetails = function(){
        $scope.class.name =  $scope.className.value;
        var model = JSON.stringify({"classEntity": $scope.class});

        $http.put('http://localhost:8080/class', model).success(function (response) {
            if(response){
                console.log("PUT succes");
            }
            else {
                console.log("PUT failed");
            }
        });
    }

    $http.get('http://localhost:8080/class/' + $scope.classId).success(function (response) {
        $scope.class = response.classEntity;
        $scope.className.value = $scope.class.name;
    });
});