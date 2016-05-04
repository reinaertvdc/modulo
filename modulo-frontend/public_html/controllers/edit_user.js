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
            templateUrl: 'views/panels/role_modal.html',
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

    $http.get('http://localhost:8080/grade/all', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
            $scope.grades[item.id] = item;
        });
        var firstKey = (Object.keys($scope.grades)[0]);
        $scope.selectedGrade = $scope.grades[firstKey].name;  // get first element in map  (anywhere else the Map is needed; array not feasible)
        $scope.studentInfo.gradeId = $scope.grades[firstKey].id;
    });


    // CERTIFICATES
    $scope.certificates = {};
    $scope.setSelectedCert = function (cert) {
        $scope.selectedCert = cert.name;
        $scope.studentInfo.certificateId = cert.id;
    };
    $http.get('http://localhost:8080/certificate/enabled/' + true, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
            $scope.certificates[item.id] = item;
        });
        var firstKey = (Object.keys($scope.certificates)[0]);
        $scope.selectedCert = $scope.certificates[firstKey].name;  // get first element in map  (anywhere else the Map is needed; array not feasible)
        $scope.studentInfo.certificateId = $scope.certificates[firstKey].id;
    });


    //PARENT SELECTION MODAL
    $scope.parents = [];
    $http.get('http://localhost:8080/user/role/PARENT', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
        response.forEach(function (item) {
                $scope.parents.push(item);
        });
    });

    $scope.removeParent = function(){
        $scope.studentInfo.parentId = null;
        $scope.parentStr = '';
    }

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
            $scope.studentInfo.parentId = selectedItem.id;
            $scope.parentStr = selectedItem.firstName + ' ' + selectedItem.lastName;
        });
    };


    // NEW USER
    if (paramVal == 'nieuw') {
        $scope.btnText = 'Aanmaken';
        $scope.panelCaption = 'Nieuwe gebruiker aanmaken';
    }


    // EDIT USER
    else if (paramVal) {
        $scope.btnText = 'Opslaan';

        $http.get('http://localhost:8080/user/id/' + paramVal, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
            $scope.basicInfo = response;
            $scope.panelCaption = 'Gebruiker bewerken: ' + $scope.basicInfo.firstName + ' ' + $scope.basicInfo.lastName;


            if ($scope.basicInfo.role == "STUDENT") {
                $scope.studentInfo = response.studentInfo;
                
                $http.get('http://localhost:8080/user/id/' + paramVal+'/parent', {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
                    $scope.parentStr = response.firstName + ' ' + response.lastName;
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
                $http.get('http://localhost:8080/user/id/' + $scope.studentInfo.parentId, {headers: {'X-auth': $cookies.get("auth")}}).success(function (response) {
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
            console.log(model);
            $http({
                method: 'POST', url: 'http://localhost:8080/user/', data: model,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.location.openPage($scope.location.USER_MANAGEMENT);
                $scope.createAlertCookie('Gebruiker toegevoegd.');
            });
        } else if (paramVal) {
            $http({
                method: 'PUT', url: 'http://localhost:8080/user/id/' + $scope.basicInfo.id, data: model,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.location.openPage($scope.location.USER_MANAGEMENT);
                $scope.createAlertCookie('Gebruiker bewerkt.');
            }).error(function(response, code){
                console.log(response.message);
            });
        }
    }

    $scope.createAlertCookie= function(msg){
        var alert = msg;
        var expireTime = new Date();
        var time = expireTime.getTime();
        time += 1000*3; // 20min expire tijd
        expireTime.setTime(time);
        $cookies.put("alert", alert, {'expires': expireTime});
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

    $scope.ok = function () {
        $uibModalInstance.close();
    };
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
