app.controller('ListUsersController', function ($scope, $http, $window, $compile, $uibModal) {
    // TODO finish controller
    const USER_LIST_ITEM_PREFIX = 'user-list-item-';
    const USER_LIST_ELEMENT = document.getElementById('table-user-list-body');

    $scope.users = new Map();
    $scope.originalUsers = new Map();
    $scope.removeId;
    $scope.swapId;

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
            $scope.refresh();
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
            '<td ng-click="openStatusModal('+user.id+')"><span ng-class="getClass('+user.id+')"></span></td>'+
            '<td class="text-info" ng-click="location.setParameter(location.PARAM_EDIT_USER_ID,' + user.id + ')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" ng-click="openRemoveModal(' + user.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        var element = document.createElement('tr');
        USER_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
        // }
    };

    $scope.getClass = function(id){
        var user = $scope.users.get(id);
        if(!angular.isUndefined(user) && !user.enabled)
            return "glyphicon glyphicon-remove-circle text-danger";
        else
            return "glyphicon glyphicon-ok-circle text-success";
    }

    $scope.swapEnabled = function(id){
        var user = $scope.users.get(id);
        user.enabled = !user.enabled;
        var userModel = JSON.stringify({"userEntity":user});
        $http.put('http://localhost:8080/account', userModel).success(function (response) {
            $scope.users.set(id, response.userEntity);
        });
    }

    $scope.removeUserBackend = function (id) {
        $scope.removeUserFrontend(id);
        $http.delete('http://localhost:8080/account/' + id);
    };

    $scope.removeUserFrontend = function (id) {
        $scope.users.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    }

    $scope.openRemoveModal = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/remove_modal.html',
            controller: 'RemoveModalInstanceCtrl',
            resolve: {
            }
        });
        $scope.removeId = id
        modalInstance.result.then(function () {
            $scope.removeUserBackend($scope.removeId)
        }, function () {
        });
    };

    $scope.openStatusModal = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/user_status_modal.html',
            controller: 'StatusModalInstanceCtrl',
            resolve: {
            }
        });
        $scope.swapId = id
        modalInstance.result.then(function () {
            $scope.swapEnabled($scope.swapId)
        }, function () {
        });
    };

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


app.controller('RemoveModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalTitle = "Verwijder gebruiker";

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
