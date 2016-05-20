app.controller('EditUserController', function ($scope, $http, $uibModal, $cookies) {
    var paramVal = $scope.location.getParameter($scope.location.PARAM_EDIT_USER_ID);


    // TODO: set to actual default values
    $scope.studentInfo = {
        birthDate: '1995-07-25',
        birthPlace: 'leuven',
        nationality: 'belg',
        street: 'WAALhostraat',
        houseNumber: '1',
        city: 'walshoutem',
        postalCode: '3401',
        phoneNumber: '123456',
        emergencyNumber: '123456',
        bankAccount: '999999999',
        nationalIdentificationNumber: '01234567890',
        parentId: null,
        gradeId: null,
        certificateId: null
    };

    $scope.basicInfo = {
        id: null,
        role: 'STUDENT',
        email: 'michiel@test.com',
        password: 'pass',
        firstName: 'Michiel',
        lastName: 'Vanmunster',
        sex: 'MALE'
    };

    $scope.parentStr = '';


    // USER TYPES
    $scope.openRoleModal = function (role) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/change_modal.html',
            controller: 'RoleModalInstanceCtrl',
            resolve: {}
        });
        //$scope.role = role;
        modalInstance.result.then(function () {
            $scope.basicInfo.role = role;
        });
    };

    //SEX
    $scope.setSex = function(sex){
        $scope.basicInfo.sex = sex;
    }

    // GRADES
    $scope.grades = {};
    $scope.setSelectedGrade = function (grade) {
        $scope.selectedGrade = grade.name;
        $scope.studentInfo.gradeId = grade.id;
    };

    $scope.openGradeModal = function (grade) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'views/panels/change_modal.html',
            controller: 'GradeModalInstanceCtrl',
            resolve: {}
        });

        modalInstance.result.then(function () {
            $scope.setSelectedGrade(grade);
        });
    };

    // CERTIFICATES
    $scope.certificates = {};
    $scope.setSelectedCert = function (cert) {
        $scope.selectedCert = cert.name;
        $scope.studentInfo.certificateId = cert.id;
    };

    //PARENT SELECTION MODAL
    $scope.parents = [];
    $http.get($scope.SERVER_ADDRESS + 'user/role/PARENT', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
                $scope.parents.push(item);
        });
    });

    $scope.removeParent = function(){
        $scope.studentInfo.parentId = null;
        $scope.parentStr = '';
    }

    $scope.openParentModal = function () {
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
            $scope.studentInfo.parentId = selectedItem.id;
            $scope.parentStr = selectedItem.firstName + ' ' + selectedItem.lastName;
        });
    };


    // NEW USER
    if (paramVal == 'nieuw') {
        $scope.btnText = 'Aanmaken';
        $scope.panelCaption = 'Nieuwe gebruiker aanmaken';

        $http.get($scope.SERVER_ADDRESS + 'grade/all', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            response.forEach(function (item) {
                $scope.grades[item.id] = item;
            });
            var firstKey = (Object.keys($scope.grades)[0]);
            $scope.selectedGrade = $scope.grades[firstKey].name;  // get first element in map  (anywhere else the Map is needed; array not feasible)
            $scope.studentInfo.gradeId = $scope.grades[firstKey].id;
        });

        $http.get($scope.SERVER_ADDRESS + 'certificate/enabled/' + true, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            response.forEach(function (item) {
                $scope.certificates[item.id] = item;
            });

            var firstKey = (Object.keys($scope.certificates)[0]);
            $scope.studentInfo.certificateId = $scope.certificates[firstKey].id;
            $scope.selectedCert = $scope.certificates[firstKey].name;  // get first element in map  (anywhere else the Map is needed; array not feasible)
        });
    }


    // EDIT USER
    else if (paramVal) {
        $scope.btnText = 'Opslaan';

        $http.get($scope.SERVER_ADDRESS + 'user/id/' + paramVal, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            $scope.basicInfo = response;
            $scope.panelCaption = 'Gebruiker bewerken: ' + $scope.basicInfo.firstName + ' ' + $scope.basicInfo.lastName;


            if ($scope.basicInfo.role == "STUDENT") {
                $scope.studentInfo = response.studentInfo;

                $http.get($scope.SERVER_ADDRESS + 'user/id/' + paramVal+'/gradeId', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                    $scope.studentInfo.gradeId = response;

                    $http.get($scope.SERVER_ADDRESS + 'grade/all', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                        response.forEach(function (item) {
                            $scope.grades[item.id] = item;
                        });
                        $scope.selectedGrade = $scope.grades[$scope.studentInfo.gradeId].name;
                    });

                });
                $http.get($scope.SERVER_ADDRESS + 'user/id/' + paramVal + '/certificateId', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                    $scope.studentInfo.certificateId = response;

                    $http.get($scope.SERVER_ADDRESS + 'certificate/enabled/' + true, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                        response.forEach(function (item) {
                            $scope.certificates[item.id] = item;
                        });
                        $scope.selectedCert = $scope.certificates[$scope.studentInfo.certificateId].name;
                    });
                });
                $http.get($scope.SERVER_ADDRESS + 'user/id/' + paramVal + '/parent', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                    $scope.parentStr = response.firstName + ' ' + response.lastName;
                    $scope.studentInfo.parentId = response.id;
                });
            }
        });
    }


    $scope.resetForm = function () {
        $scope.basicInfo.id = null;
        $scope.basicInfo.role = 'STUDENT'
        $scope.basicInfo.email = '';
        $scope.basicInfo.password = '';
        $scope.basicInfo.firstName = '';
        $scope.basicInfo.lastName = '';
        $scope.basicInfo.sex = '';
        $scope.studentInfo.birthDate = '';
        $scope.studentInfo.birthPlace = '';
        $scope.studentInfo.nationality = '';
        $scope.studentInfo.street = '';
        $scope.studentInfo.houseNumber = '';
        $scope.studentInfo.city = '';
        $scope.studentInfo.postalCode = '';
        $scope.studentInfo.phoneNumber = '';
        $scope.studentInfo.emergencyNumber = '';
        $scope.studentInfo.bankAccount = '';
        $scope.studentInfo.nationalIdentificationNumber = '';
        $scope.studentInfo.parentId = null;
        $scope.studentInfo.certificateId = null;
        $scope.studentInfo.gradeId = null;
        $scope.parentStr = '';
    };

    $scope.submitForm = function () {
        var model = $scope.basicInfo;
        if ($scope.basicInfo.role == 'STUDENT') {
            model.studentInfo = $scope.studentInfo;
            if($scope.studentInfo.parentId != null) {
                $http.get($scope.SERVER_ADDRESS + 'user/id/' + $scope.studentInfo.parentId, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                    model.parent = response;
                    $scope.createOrUpdateUser(model);
                });
            }
            else{
                $scope.createOrUpdateUser(model);
            }
        }else{
            $scope.createOrUpdateUser(model);
        }
    };

    $scope.createOrUpdateUser = function(model){
        model = JSON.stringify(model);

        if (paramVal == 'nieuw') {
            $http({
                method: 'POST', url: $scope.SERVER_ADDRESS + 'user/', data: model,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                if($scope.basicInfo.role == 'STUDENT') {
                    var user = response;
                    $http({
                        method: 'PUT',
                        url: $scope.SERVER_ADDRESS + 'certificate/id/' + $scope.studentInfo.certificateId + '/student/id/' + user.studentInfo.id,
                        headers: {'X-auth': $cookies.get("auth")}
                    }).success(function (response) {
                        $http({
                            method: 'PUT',
                            url: $scope.SERVER_ADDRESS + 'grade/id/' + $scope.studentInfo.gradeId + '/student/id/' + user.studentInfo.id,
                            headers: {'X-auth': $cookies.get("auth")}
                        }).success(function (response) {
                            $scope.createAlertCookie('Gebruiker toegevoegd.');
                            $scope.location.openPage($scope.location.USER_MANAGEMENT);
                        });
                    });
                }
                else{
                    $scope.createAlertCookie('Gebruiker toegevoegd.');
                    $scope.location.openPage($scope.location.USER_MANAGEMENT);
                }
            });
        } else if (paramVal) {
            $http({
                method: 'PUT', url: $scope.SERVER_ADDRESS + 'user/', data: model,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                if($scope.basicInfo.role == 'STUDENT') {
                    var user = response;
                    $http({
                        method: 'PUT',
                        url: $scope.SERVER_ADDRESS + 'certificate/id/' + $scope.studentInfo.certificateId + '/student/id/' + user.studentInfo.id,
                        headers: {'X-auth': $cookies.get("auth")}
                    }).success(function (response) {
                        $http({
                            method: 'PUT',
                            url: $scope.SERVER_ADDRESS + 'grade/id/' + $scope.studentInfo.gradeId + '/student/id/' + user.studentInfo.id,
                            headers: {'X-auth': $cookies.get("auth")}
                        }).success(function (response) {
                            $scope.createAlertCookie('Gebruiker bewerkt.');
                            $scope.location.openPage($scope.location.USER_MANAGEMENT);
                        });
                    });
                }
                else{
                    $scope.createAlertCookie('Gebruiker bewerkt.');
                    $scope.location.openPage($scope.location.USER_MANAGEMENT);
                }
            });
        }
    }
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


app.controller('RoleModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalTitle = "Rol aanpassen";
    $scope.modalBody = "Bent u zeker dat u de rol van deze gebruiker wilt veranderen?";

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});


app.controller('GradeModalInstanceCtrl', function ($scope, $uibModalInstance) {
    $scope.modalTitle = "Graad aanpassen";
    $scope.modalBody = "Bent u zeker dat u de graad van deze gebruiker wilt veranderen?\n" +
    "De gebruiker zal uit zijn PAV-klassen worden verwijderd.";

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
