app.controller('ListUsersController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {
    const USER_LIST_ITEM_PREFIX = 'user-list-item-';
    const USER_LIST_ELEMENT = document.getElementById('table-user-list-body');

    $scope.users = new Map();
    $scope.originalUsers = new Map();
    $scope.removeId;
    $scope.swapId;

    $scope.searchUsers = function () {
        // make $scope.users the original users
        $scope.users = new Map($scope.originalUsers);
        $scope.originalUsers.forEach(function (item) {
            $scope.addUser(item);
        });

        if ($scope.searchKeyword) {
            var search = $scope.searchKeyword.toLowerCase();

            // remove all the users that don't match
            $scope.users.forEach(function (item) {
                if (item.firstName.toLowerCase().indexOf(search) < 0 && item.lastName.toLocaleLowerCase().indexOf(search) < 0)
                    $scope.removeUserFrontend(item.id);
            });
        }
        $scope.refresh();
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

            '<td>' + user.firstName + ' ' + user.lastName + '</td><td>' + user.email + '</td><td>' + $scope.userRoles[user.role] + '</td>';

        if ($cookies.getObject("user").id === user.id) {
            html += '<td><span ng-class="getClass(' + user.id + ')"></span></td>';
        }
        else {
            html += '<td ng-click="swapEnabled(' + user.id + ')"><span ng-class="getClass(' + user.id + ')"></span></td>';
        }

        html += '<td class="text-info" ng-click="location.setParameter(location.PARAM_EDIT_USER_ID,' + user.id + ')"><span role="button" class="glyphicon glyphicon-edit"></span></td>';
        if ($cookies.getObject("user").id === user.id) {
            html += '<td><span class="glyphicon glyphicon-remove"></span></td></tr>';
        } else {
            html += '<td class="text-danger" ng-click="openRemoveModal(' + user.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td></tr>';
        }


        var element = document.createElement('tr');
        USER_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
        // }
    };

    $scope.getClass = function (id) {
        var user = $scope.users.get(id);
        if (user != null) {
            if ($cookies.getObject("user").id === user.id) {
                if (!angular.isUndefined(user) && !user.enabled)
                    return "glyphicon glyphicon-remove-circle";
                else
                    return "glyphicon glyphicon-ok-circle";
            }
            else {
                if (!angular.isUndefined(user) && !user.enabled)
                    return "glyphicon glyphicon-remove-circle text-danger";
                else
                    return "glyphicon glyphicon-ok-circle text-success";
            }
        }
    };

    $scope.swapEnabled = function (id) {
        var user = $scope.users.get(id);

        var enabledOrDisabled = "";
        if (!user.enabled)
            enabledOrDisabled = "enable";
        else
            enabledOrDisabled = "disable";

        $http({
            method: 'PUT', url: $scope.SERVER_ADDRESS + 'user/id/' + user.id + '/' + enabledOrDisabled,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            user.enabled = !user.enabled;

            if (user.enabled)
                $scope.createAlertCookie('Gebruiker actief gezet.');
            else
                $scope.createAlertCookie('Gebruiker inactief gezet.');
        });
    };

    $scope.removeUserBackend = function (id) {
        $http.delete($scope.SERVER_ADDRESS + 'user/id/' + id, {headers: {'X-Auth': $cookies.get("auth")}}).success(function (response) {
            $scope.removeUserFrontend(id);
        });
    };

    $scope.removeUserFrontend = function (id) {
        $scope.users.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    };

    $scope.openRemoveModal = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/remove_modal.html',
            controller: 'RemoveUserModalInstanceCtrl',
            resolve: {}
        });
        $scope.removeId = id;
        modalInstance.result.then(function () {
            $scope.removeUserBackend($scope.removeId)
        }, function () {
        });
    };


    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(USER_LIST_ELEMENT)($scope);
    };

    $http.get($scope.SERVER_ADDRESS + 'user/all', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
            $scope.addUser(item);
        });

        $scope.originalUsers = new Map($scope.users);
        $scope.refresh();
    });
});


app.controller('RemoveUserModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalTitle = "gebruiker";
    $scope.modalObject = "deze gebruiker";

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

app.controller('StatusModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalTitle = "Zet gebruiker (in)actief";

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
