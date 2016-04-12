app.controller('ListUsersController', function ($scope, $compile) {
    // TODO finish controller
    const USER_LIST_ITEM_PREFIX = 'user-list-item-';
    const USER_LIST_ELEMENT = document.getElementById('table-user-list-body');

    $scope.users = new Map();

    $scope.toElementId = function (id) {
        return USER_LIST_ITEM_PREFIX + id;
    };

    $scope.addUser = function (user) {
        $scope.removeUser(user.id);

        $scope.users.set(user.id, user);

        var html = '<tr id="' + $scope.toElementId(user.id) + '">' +
            '<td>' + user.name.first + ' ' + user.name.last + '</td><td>' + user.email + '</td><td>' + user.details.type + '</td>' +
            '<td class="text-info"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" data-ng-click="removeUser(' + user.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        var element = document.createElement('tr');
        USER_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.removeUser = function (id) {
        $scope.users.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null) {
            element.parentElement.removeChild(element);
        }
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(USER_LIST_ELEMENT)($scope);
    };

    backend.getUsers().forEach(function (item) {
        $scope.addUser(item);
    });
    $scope.refresh();
});
