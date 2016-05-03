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
        phoneCell: '123456',
        phoneParent: '123456',
        bankAccount: '999999999',
        nationalId: '01234567890',
        parent: null,
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
    $scope.setUserRole = function (role) {
        $scope.basicInfo.role = role;
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

    $http.get('http://localhost:8080/grade/all').success(function (response) {
        response.forEach(function (item) {
            $scope.grades[item.gradeEntity.id] = item.gradeEntity;
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
            if($scope.basicInfo.id != item.id)
                $scope.parents.push(item);
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
        $scope.studentInfo.parent = null;
        $scope.parentStr = '';
    };

    $scope.submitForm = function () {
        var model;
        if ($scope.basicInfo.role == 'STUDENT')
            model = JSON.stringify({"userEntity": $scope.basicInfo, "studentInfoEntity": $scope.studentInfo});
        else
            model = JSON.stringify({"userEntity": $scope.basicInfo});

        if (paramVal == 'nieuw') {
            $http.post('http://localhost:8080/account/' + $scope.basicInfo.role.toLocaleLowerCase(), model).success(function () {
                alert('Nieuwe gebruiker aangemaakt.');
                $scope.resetForm();
            });
        } else if (paramVal) {
            console.log($scope.basicInfo.id);

            $http.put('http://localhost:8080/account/' + $scope.basicInfo.role.toLocaleLowerCase(), model).success(function () {
                alert('Gebruiker geüpdatet.');
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
