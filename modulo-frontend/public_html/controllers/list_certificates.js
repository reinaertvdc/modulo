app.controller('ListCertificatesController', function ($scope, $cookies, $http, $window, $compile) {
    const CERTIFICATES_LIST_ITEM_PREFIX = 'certificates-list-item-';
    const CERTIFICATES_LIST_ELEMENT = document.getElementById('table-certificates-list-body');

    $scope.certificates = new Map();
    $scope.originalCertificates = new Map();

    $scope.searchCertificates = function () {
        // make $scope.tasks the original certificates
        $scope.certificates = new Map($scope.originalCertificates);
        $scope.originalCertificates.forEach(function (item) {
            $scope.addCertificate(item);
        });

        if ($scope.certificateSearchKeyword) {
            var search = $scope.certificateSearchKeyword.toLowerCase();

            // remove all the certificates that don't match
            $scope.certificates.forEach(function (item) {
                if (item.name.toLowerCase().indexOf(search) < 0)
                    $scope.removeCertificate(item.id);
            });
        }
        $scope.refresh();
    };

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
        if(!angular.isUndefined(certificate) && !certificate.enabled)
            return "glyphicon glyphicon-remove-circle text-danger";
        else
            return "glyphicon glyphicon-ok-circle text-success";
    };

    $scope.swapEnabled = function(id){
        var certificate = $scope.certificates.get(id);
        $http.put($scope.SERVER_ADDRESS + 'certificate/id/' + id + '/' + (certificate.enabled ? 'disable' : 'enable'), null, {headers: {'X-auth': $cookies.get("auth")}})
            .success(function (response) {
                certificate.enabled = !certificate.enabled;

                if(certificate.enabled)
                    $scope.createAlertCookie('Certificaat actief gezet.');
                else
                    $scope.createAlertCookie('Certificaat inactief gezet.');

            });
    };

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

    $http.get($scope.SERVER_ADDRESS + 'certificate/all', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
            $scope.addCertificate(item);
        });

        $scope.originalCertificates = new Map($scope.certificates);
        $scope.refresh();
    });

    // backend.getCertificates().forEach(function (item) {
    //     $scope.addCertificate(item);
    // });
});
