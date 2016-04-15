/**
 * Created by martijn on 14/04/16.
 */
app.controller('ListCertificatesController', function ($scope, $compile) {
    // TODO implement controller
    //TODO test data vervangen door effectieve data
    const CERTIFICATES_LIST_ITEM_PREFIX = 'certificates-list-item-';
    const CERTIFICATES_LIST_ELEMENT = document.getElementById('table-certificates-list-body');

    var enabled = false;
    $scope.certificates = new Map();

    $scope.toElementId = function (id) {
        return CERTIFICATES_LIST_ITEM_PREFIX + id;
    };

    $scope.addCertificate = function (i, enabled, name) {
        $scope.removeCertificate(i);

        $scope.certificates.set(i,enabled);


        var html = '<tr id="' + $scope.toElementId(i) + '">' +
            '<td>'+name+'</td>' ;
        html += '<td ng-click="swapEnabled(' + i + ')"><span ng-class="getClass('+i+')"></span></td>';
        html += '</tr>';

        var element = document.createElement('tr');
        CERTIFICATES_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.getClass = function(id){
        console.log("Get Class");
        var enable = $scope.certificates.get(id);
        if(!enable) {
            return "glyphicon glyphicon-remove-circle text-danger";
        }
        else {
            return "glyphicon glyphicon-ok-circle text-success";
        }
    }

    $scope.swapEnabled = function(id){
        var enable = !$scope.certificates.get(id);
        $scope.certificates.set(id, enable);
    }

    $scope.removeCertificate = function (id) {
        $scope.certificates.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null) {
            element.parentElement.removeChild(element);
        }
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(CERTIFICATES_LIST_ELEMENT)($scope);
    };

    var i = 1;
    backend.getUsers().forEach(function (item) {
        $scope.addCertificate(i, enabled, "Naam " +i);
        i = i +1;
        enabled = !enabled;
    });
    $scope.refresh();
});
