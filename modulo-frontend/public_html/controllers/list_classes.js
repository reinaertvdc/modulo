app.controller('ListClassesController', function ($scope , $http, $window, $compile) {
    // TODO implement controller

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

        var html = '<tr id="' + $scope.toElementId(addclass.id) + '">' +
            '<td>' + addclass.name + '</td>' +
            '<td class="text-info"  ng-click="location.setParameter(location.PARAM_MANAGE_CLASS_ID,'+addclass.id+')"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" ng-click="removeClassBackend(' + addclass.id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        var element = document.createElement('tr');

        if(addclass.type == "BGV")
            BGVCLASS_LIST_ELEMENT.appendChild(element);
        if(addclass.type == "PAV")
            PAVCLASS_LIST_ELEMENT.appendChild(element);

        element.outerHTML = html;

    };

    $scope.removeClassBackend = function (id) {
        $scope.removeClassFrontend(id);
        $http.delete('http://localhost:8080/class/'+id);
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

    $http.get('http://localhost:8080/class/teacher/' + $scope.backend.getUser().id).success(function (response) {
        response.forEach(function (item) {
            $scope.addClass(item.classEntity)
        });
        $scope.refresh();
    });

});

