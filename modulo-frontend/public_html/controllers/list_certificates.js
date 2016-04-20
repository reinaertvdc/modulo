/**
 * Created by martijn on 14/04/16.
 */
app.controller('ListCertificatesController', function ($scope, $http, $window, $compile) {
    // TODO implement controller
    //TODO test data vervangen door effectieve data
    const CERTIFICATES_LIST_ITEM_PREFIX = 'certificates-list-item-';
    const CERTIFICATES_LIST_ELEMENT = document.getElementById('table-certificates-list-body');

    $scope.certificates = new Map();

    $scope.toElementId = function (id) {
        return CERTIFICATES_LIST_ITEM_PREFIX + id;
    };

    $scope.addCertificate = function (certificate) {
        $scope.removeCertificate(certificate.id);
        $scope.certificates.set(certificate.id, certificate);

        var html = '<tr id="' + $scope.toElementId(certificate.id) + '">' +
            '<td>'+certificate.name+'</td>' ;
        html += '<td ng-click="swapEnabled('+certificate.id+')"><span ng-class="getClass('+certificate.id+')"></span></td>';
        html += '</tr>';

        var element = document.createElement('tr');
        CERTIFICATES_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.getClass = function(id){
        var certificate = $scope.certificates.get(id);
        if(!certificate.enabled)
            return "glyphicon glyphicon-remove-circle text-danger";
        else
            return "glyphicon glyphicon-ok-circle text-success";
    }

    $scope.swapEnabled = function(id){
        var certificate = $scope.certificates.get(id);
        certificate.enabled = !certificate.enabled;
        var certificateModel = JSON.stringify({"certificateEntity":certificate});
        $http.put('http://localhost:8080/certificate', certificateModel).success(function (response) {
            $scope.certificates.set(id, response.certificateEntity);
        });
    }

    $scope.removeCertificate = function (id) {
        $scope.certificates.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null)
            element.parentElement.removeChild(element);
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(CERTIFICATES_LIST_ELEMENT)($scope);
    };



    $http.get('http://localhost:8080/certificate/all').success(function (response) {
        response.forEach(function (item) {
            $scope.addCertificate(item.certificateEntity);
        });
        $scope.refresh();
    });

    // backend.getCertificates().forEach(function (item) {
    //     $scope.addCertificate(item);
    // });
});
