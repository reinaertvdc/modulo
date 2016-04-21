app.controller('EditUserController', function ($scope, $http, $uibModal) {
    var paramVal = $scope.location.getParameter($scope.location.PARAM_EDIT_USER_ID);


    // NEW USER
    if (paramVal == 'nieuw') {
        $scope.btnText = 'Aanmaken'
        $scope.panelCaption = 'Nieuwe gebruiker aanmaken';

        // DEBUG:  fill with dummy data
        $scope.studentInfo = {
            birthDate: '1995-07-25',
            birthPlace: 'leuven',
            nationality: 'belg',
            street: 'WAALhostraat',
            houseNumber: '1',
            city: 'walshoutem',
            postalCode: '3401',
            phoneCell: '123456',
            phoneParent: '123456',
            bankAccount: '999999999',
            nationalId: '01234567890',
            parent: null
        }

        $scope.basicInfo = {
            type: 'STUDENT',
            email: 'michiel@test.com',
            password: 'pass',
            firstName: 'Michiel',
            lastName: 'Vanmunster'
        };

        $scope.parentStr = '';
    }


    // EDIT USER
    else if (paramVal) {
        $scope.btnText = 'Opslaan'

        $http.get('http://localhost:8080/account/' + paramVal).success(function (response) {
            $scope.basicInfo = response.userEntity;

            console.log($scope.basicInfo.type);

            if ($scope.basicInfo.type == "STUDENT") {
                $scope.studentInfo = response.studentInfoEntity;
                console.log("info " + $scope.studentInfo);
            }

            console.log($scope.studentInfo);

            $scope.panelCaption = 'Gebruiker bewerken: ' + $scope.basicInfo.firstName + ' ' + $scope.basicInfo.lastName;
        });
    }


    $scope.resetForm = function () {
        $scope.basicInfo.type = 'STUDENT'
        $scope.basicInfo.email = '';
        $scope.basicInfo.password = '';
        $scope.basicInfo.firstName = '';
        $scope.basicInfo.lastName = '';
        $scope.studentInfo.birthDate = '';
        $scope.studentInfo.birthPlace = '';
        $scope.studentInfo.nationality = '';
        $scope.studentInfo.street = '';
        $scope.studentInfo.houseNumber = '';
        $scope.studentInfo.city = '';
        $scope.studentInfo.postalCode = '';
        $scope.studentInfo.phoneCell = '';
        $scope.studentInfo.phoneParent = '';
        $scope.studentInfo.bankAccount = '';
        $scope.studentInfo.nationalId = '';
        $scope.studentInfo.parent = null;
        $scope.parentStr = '';
    };


    $scope.submitForm = function () {
        var model;

        console.log($scope.studentInfo);

        if ($scope.basicInfo.type == 'STUDENT')
            model = JSON.stringify({"userEntity": $scope.basicInfo, "studentInfoEntity": $scope.studentInfo});
        else
            model = JSON.stringify({"userEntity": $scope.basicInfo});

        $http.post('http://localhost:8080/account/' + $scope.basicInfo.type.toLocaleLowerCase(), model).success(function () {
            alert('Nieuwe gebruiker aangemaakt.');
            $scope.resetForm();
        });
    };


    //PARENT SELECTION MODAL

    $scope.parents = [];

    $http.get('http://localhost:8080/account/parents').success(function (response) {
        response.forEach(function (item) {
            $scope.parents.push(item.userEntity);
        });
    });

    $scope.open = function () {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/parentModal.html',
            controller: 'ModalInstanceCtrl',
            resolve: {
                parents: function () {
                    return $scope.parents;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            $scope.studentInfo.parent = selectedItem.id;
            $scope.parentStr = selectedItem.firstName + ' ' + selectedItem.lastName;
        }, function () {
            $scope.studentInfo.parent = null;
            $scope.parentStr = '';
        });
    };
});


app.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, parents) {
    $scope.modalTitle = "Kies een ouder";
    $scope.items = parents;
    $scope.selected = {
        item: $scope.items[0]
    };

    $scope.ok = function () {
        $uibModalInstance.close($scope.selected.item);
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
