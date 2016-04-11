app.controller('NavbarController', function ($scope) {
    // TODO implement closing navbar when user clicks outside of navbar
    $scope.isCollapsed = true;

    $scope.navigate = function(location) {
        $scope.location.panel = location;
    }
});
