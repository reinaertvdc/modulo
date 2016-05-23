app.controller('NavbarController', function ($scope) {
    $scope.isCollapsed = true;

    $scope.navigate = function(location) {
        $scope.location.panel = location;
    }
});
