app.controller('ListUsersController', function ($scope, $http, $window, $compile) {
    // TODO finish controller
    const USER_LIST_ITEM_PREFIX = 'user-list-item-';
    const USER_LIST_ELEMENT = document.getElementById('table-user-list-body');

    $scope.users = new Map();
    $scope.originalUsers = new Map();

    $scope.searchUsers = function () {
        if ($scope.searchKeyword) {
            $scope.users = new Map($scope.originalUsers);
            var search = $scope.searchKeyword.toLowerCase();

            $scope.users.forEach(function (item) {
                if (item.firstName.toLowerCase().indexOf(search) < 0 && item.lastName.toLocaleLowerCase().indexOf(search) < 0)
                    $scope.removeUserFrontend(item.id);
            });
        } else {
            $scope.users = new Map($scope.originalUsers);
            $scope.originalUsers.forEach(function (item) {
               $scope.addUser(item);
            });
        }
    };

    // //Pagination code
    // $scope.totalItems = $scope.backend.getUsers().length;
    // $scope.currentPage = 1;
    // $scope.maxSize = 5;
    // $scope.itemsPerPage = 5;
    // $scope.previousPage = $scope.currentPage;
    //
    // $scope.pageChanged = function() {
    //     //Remove previous elements
    //     var prevItems = $scope.itemsPerPage * $scope.previousPage;
    //     for(var i = prevItems - $scope.itemsPerPage + 1; i <= prevItems;i++)
    //     {
    //         var user = $scope.users.get(i);
    //         $scope.removeUser(user.id);
    //     }
    //
    //     //Add new elements
    //     for(var i = prevItems + 1; i <= $scope.currentPage * $scope.itemsPerPage;i++)
    //     {
    //         var user = $scope.users.get(i);
    //         $scope.addUser(user);
    //     }
    //     $scope.previousPage = $scope.currentPage;
    // };

    $scope.toElementId = function (id) {
        return USER_LIST_ITEM_PREFIX + id;
    };

    $scope.addUser = function (user) {
        $scope.removeUserFrontend(user.id);
        $scope.users.set(user.id, user);

        // if(user.id <= $scope.itemsPerPage * $scope.currentPage)
        // {

        var html = '<tr id="' + $scope.toElementId(user.id) + '">' +
            '<td>' + user.firstName + ' ' + user.lastName + '</td><td>' + user.email + '</td><td>' + user.type + '</td>' +
            '<td class="text-info" ng-click="location.setParameter(location.PARAM_EDIT_USER_ID,' + user.id + ')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" ng-click="removeUserBackend(' + user.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        var element = document.createElement('tr');
        USER_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
        // }
    };

    $scope.removeUserBackend = function (id) {
        $scope.removeUserFrontend(id);
        $http.delete('http://localhost:8080/account/'+id);
    };

    $scope.removeUserFrontend = function (id) {
        $scope.users.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    }

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(USER_LIST_ELEMENT)($scope);
    };



    $http.get('http://localhost:8080/account/all').success(function (response) {
        response.forEach(function (item) {
            $scope.addUser(item.userEntity);
        });

        $scope.originalUsers = new Map($scope.users);
        $scope.refresh();
    });
});
