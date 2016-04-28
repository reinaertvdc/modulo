app.controller('EditUserController', function ($scope, $http, $uibModal) {
    var paramVal = $scope.location.getParameter($scope.location.PARAM_EDIT_USER_ID);


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
        id: null,
        type: 'TEACHER',
        email: 'michiel@test.com',
        password: 'pass',
        firstName: 'Michiel',
        lastName: 'Vanmunster'
    };

    $scope.parentStr = '';




    //PARENT SELECTION MODAL

    $scope.parents = [];

    $http.get('http://localhost:8080/account/parents').success(function (response) {
        response.forEach(function (item) {
            if($scope.basicInfo.id != item.userEntity.id)
                $scope.parents.push(item.userEntity);
        });
    });

    $scope.open = function () {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/parent_modal.html',
            controller: 'ParentModalInstanceCtrl',
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


    // NEW USER
    if (paramVal == 'nieuw') {
        $scope.btnText = 'Aanmaken'
        $scope.panelCaption = 'Nieuwe gebruiker aanmaken';
    }


    // EDIT USER
    else if (paramVal) {
        $scope.btnText = 'Opslaan'

        $http.get('http://localhost:8080/account/' + paramVal).success(function (response) {
            $scope.basicInfo = response.userEntity;
            $scope.panelCaption = 'Gebruiker bewerken: ' + $scope.basicInfo.firstName + ' ' + $scope.basicInfo.lastName;

            if ($scope.basicInfo.type == "STUDENT") {
                $http.get('http://localhost:8080/account/student/' + paramVal).success(function (response) {
                    $scope.studentInfo = response.studentInfoEntity;

                    if ($scope.studentInfo.parent)
                        $http.get('http://localhost:8080/account/' + $scope.studentInfo.parent).success(function (response) {
                            $scope.parentStr = response.userEntity.firstName + ' ' + response.userEntity.lastName;
                        });
                });
            }
        });
    }


    $scope.resetForm = function () {
        $scope.basicInfo.id = null;
        $scope.basicInfo.type = 'TEACHER'
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
        if ($scope.basicInfo.type == 'STUDENT')
            model = JSON.stringify({"userEntity": $scope.basicInfo, "studentInfoEntity": $scope.studentInfo});
        else
            model = JSON.stringify({"userEntity": $scope.basicInfo});

        if (paramVal == 'nieuw') {
            $http.post('http://localhost:8080/account/' + $scope.basicInfo.type.toLocaleLowerCase(), model).success(function () {
                $scope.location.openPage($scope.location.USER_MANAGEMENT);
                $scope.location.setParameter($scope.location.USER_MANAGEMENT_EDIT, 'Gebruiker toegevoegd.');
                $scope.resetForm();
            });
        } else if (paramVal) {
            $http.put('http://localhost:8080/account/' + $scope.basicInfo.type.toLocaleLowerCase(), model).success(function () {
                $scope.location.openPage($scope.location.USER_MANAGEMENT);
                $scope.location.setParameter($scope.location.USER_MANAGEMENT_EDIT, 'Gebruiker bewerkt.');
                $scope.resetForm();
            });
        }
    };
});


app.controller('ParentModalInstanceCtrl', function ($scope, $uibModalInstance, parents) {
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
