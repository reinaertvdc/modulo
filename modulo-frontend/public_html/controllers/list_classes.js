app.controller('ListClassesController', function ($scope , $http, $window, $compile, $uibModal, $cookies) {
    const CLASS_LIST_ITEM_PREFIX = 'class-list-item-';
    const PAVCLASS_LIST_ELEMENT = document.getElementById('table-pav-class-list-body');
    const BGVCLASS_LIST_ELEMENT = document.getElementById('table-bgv-class-list-body');

    $scope.classes = new Map();

    $scope.toElementId = function (id) {
        return CLASS_LIST_ITEM_PREFIX + id;
    };

    $scope.addClass = function (addclass) {
        $scope.removeClassFrontend(addclass.id);
        $scope.classes.set(addclass.id, addclass);


        if(addclass.type == "BGV"){
            var html = '<tr id="' + $scope.toElementId(addclass.id) + '">' +
                '<td>' + addclass.name + '</td>' +
                '<td class="text-info"  ng-click="editBGVClass('+addclass.id+')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
                '<td class="text-danger" ng-click="openRemoveModal(' + addclass.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
                '</tr>';
            var element = document.createElement('tr');

            BGVCLASS_LIST_ELEMENT.appendChild(element);
        }
        else if(addclass.type == "PAV") {
            var html = '<tr id="' + $scope.toElementId(addclass.id) + '">' +
                '<td>' + addclass.name + '</td>' +
                '<td class="text-info"  ng-click="editPAVClass('+addclass.id+')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
                '<td class="text-danger" ng-click="openRemoveModal(' + addclass.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
                '</tr>';
            var element = document.createElement('tr');

            PAVCLASS_LIST_ELEMENT.appendChild(element);
        }

        element.outerHTML = html;

    };

    $scope.editBGVClass = function(classId){
        $scope.location.setParameter($scope.location.PARAM_MANAGE_CLASS_ID, classId);
        $scope.location.setParameter($scope.location.PARAM_CLASS_TYPE, 'BGV');
    }


    $scope.editPAVClass = function(classId){
        $scope.location.setParameter($scope.location.PARAM_MANAGE_CLASS_ID, classId);
        $scope.location.setParameter($scope.location.PARAM_CLASS_TYPE, 'PAV');
    }

    $scope.openRemoveModal = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/remove_modal.html',
            controller: 'RemoveClassModalInstanceCtrl',
            resolve: {}
        });
        $scope.removeId = id;
        modalInstance.result.then(function () {
            $scope.removeClassBackend($scope.removeId)
        }, function () {
        });
    };

    $scope.removeClassBackend = function (id) {
        $http({
            method: 'DELETE', url: $scope.SERVER_ADDRESS + 'class/id/'+id,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.removeClassFrontend(id);
            $scope.createAlertCookie('Klas verwijderd.');
        });
    };

    $scope.removeClassFrontend = function (id) {
        $scope.classes.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    }

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(PAVCLASS_LIST_ELEMENT)($scope);
        $compile(BGVCLASS_LIST_ELEMENT)($scope);
    };

    $http.get($scope.SERVER_ADDRESS + 'user/id/' + $cookies.getObject("user").id + '/teaching', {headers: {'X-Auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
            $scope.addClass(item)
        });
        $scope.refresh();
    });

});

app.controller('RemoveClassModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalTitle = "klas";
    $scope.modalObject = "deze klas"

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

