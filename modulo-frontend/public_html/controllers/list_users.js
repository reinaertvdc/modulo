app.controller('ListUsersController', function ($scope, $compile) {
    // TODO finish controller
    //TODO vervang alle dummy data met backend stuff
    //TODO user zorden nu handmatig via for lus bepaakd, dit zou een function call uit de database moeten worden
    const USER_LIST_ITEM_PREFIX = 'user-list-item-';
    const USER_LIST_ELEMENT = document.getElementById('table-user-list-body');

    $scope.users = new Map();

    //Pagination code
    $scope.totalItems = backend.getUsers().length;
    $scope.currentPage = 1;
    $scope.maxSize = 5;
    $scope.itemsPerPage = 5;
    $scope.previousPage = $scope.currentPage;

    $scope.pageChanged = function() {
        //Remove previous elements
        var prevItems = $scope.itemsPerPage * $scope.previousPage;
        for(var i = prevItems - $scope.itemsPerPage + 1; i <= prevItems;i++)
        {
            var user = $scope.users.get(i);
            $scope.removeUser(user.id);
        }

        //Add new elements
        for(var i = prevItems + 1; i <= $scope.currentPage * $scope.itemsPerPage;i++)
        {
            var user = $scope.users.get(i);
            $scope.addUser(user);
        }
        $scope.previousPage = $scope.currentPage;
    };

    $scope.toElementId = function (id) {
        return USER_LIST_ITEM_PREFIX + id;
    };

    $scope.addUser = function (user) {
        $scope.removeUser(user.id);

        if(user.id <= $scope.itemsPerPage * $scope.currentPage)
        {

            var html = '<tr id="' + $scope.toElementId(user.id) + '">' +
                '<td>' + user.name.first + ' ' + user.name.last + '</td><td>' + user.email + '</td><td>' + user.details.type + '</td>' +
                '<td class="text-info"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
                '<td class="text-danger" data-ng-click="removeUser(' + user.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
                '</tr>';

            var element = document.createElement('tr');
            USER_LIST_ELEMENT.appendChild(element);
            element.outerHTML = html;
        }
    };

    $scope.removeUser = function (id) {
        //$scope.users.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null) {
            element.parentElement.removeChild(element);
        }
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(USER_LIST_ELEMENT)($scope);
    };

    backend.getUsers().forEach(function (user) {
        $scope.users.set(user.id, user);
        $scope.addUser(user);
    });
    $scope.refresh();
});
