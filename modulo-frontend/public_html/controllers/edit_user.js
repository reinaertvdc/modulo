app.controller('EditUserController', function ($scope, $http) {
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
        nationalId: '01234567890'
    }

    $scope.basicInfo = {
        type: 'STUDENT',
        email: 'michiel@test.com',
        password: 'pass',
        firstName: 'Michiel',
        lastName: 'Vanmunster'
    };

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
    };


    $scope.submitForm = function () {
        var model;
        if ($scope.basicInfo.type == 'STUDENT')
            model = JSON.stringify({"userEntity": $scope.basicInfo, "studentInfoEntity": $scope.studentInfo});
        else
            model = JSON.stringify({"userEntity": $scope.basicInfo});

        $http.post('http://localhost:8080/account/' + $scope.basicInfo.type.toLocaleLowerCase(), model).success(function () {
            alert('Nieuwe gebruiker aangemaakt.');
            $scope.resetForm();
        });
    };


    if ($scope.location.getParameter($scope.location.PARAM_EDIT_USER_ID))
        $scope.panelCaption = 'Gebruiker bewerken';
    else if ($scope.location.getParameter($scope.location.PARAM_CREATE_NEW_USER))
        $scope.panelCaption = 'Nieuwe gebruiker aanmaken';
})
;
