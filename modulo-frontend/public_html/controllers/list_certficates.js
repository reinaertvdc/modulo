/**
 * Created by martijn on 14/04/16.
 */
app.controller('ListCertificatesController', function ($scope, $compile) {
    // TODO implement controller

    const CERTIFICATES_LIST_ITEM_PREFIX = 'certificates-list-item-';
    const CERTIFICATES_LIST_ELEMENT = document.getElementById('table-certificates-list-body');

    var enabled = false;
    $scope.certificates = new Map();

    $scope.toElementId = function (id) {
        return CERTIFICATES_LIST_ITEM_PREFIX + id;
    };

    $scope.addCertificate = function (i, enabled) {
        $scope.removeCertificate(i);

        $scope.certificates.set(i,enabled);

        var html = '<tr id="' + $scope.toElementId(i) + '">' +
            '<td>Naam '+i+'</td>' ;
        html += '<td class="{{color'+i+'}}" ng-click="swapEnabled(' + i + ')"><span class="glyphicon {{iconClass'+i+'}}"></span></td>';
        html += '</tr>';

        var element = document.createElement('tr');
        CERTIFICATES_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.swapEnabled = function(id){
        var enable = !$scope.certificates.get(id);
        console.log("Swap Enabled CLICK: " + enable);
        //TODO Array achtig iets maken zodat iedere rij zijn eige kleur/icon vars heeft
        if(!enable) {
            $scope.iconClass1 = "glyphicon-remove-circle";
            $scope.color1 = "text-danger";
        }
        else {
            $scope.iconClass1 = "glyphicon-ok-circle";
            $scope.color1 = "text-success";
        }

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
        $scope.addCertificate(i, enabled);
        i = i +1;
        enabled = !enabled;
    });
    $scope.refresh();
});
