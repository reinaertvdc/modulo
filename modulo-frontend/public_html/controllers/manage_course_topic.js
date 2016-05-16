app.controller('ManageCourseTopicController', function ($scope, $http, $window, $compile, $uibModal, $cookies) {

    const STUDENTS_LIST_ITEM_PREFIX = 'students-list-item-';
    const OBJECTIVES_LIST_ITEM_PREFIX = 'objectives-list-item-';
    const STUDENTS_LIST_ELEMENT = document.getElementById('table-students-list-body');
    const OBJECTIVES_LIST_ELEMENT = document.getElementById('table-objectives-list-body');
    const paramCourse = $scope.location.getParameter($scope.location.PARAM_MANAGE_COURSE_TOPIC_ID);
    const paramClass = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);
    $scope.students = new Map();
    $scope.objectives = new Map();

    $scope.info = {
        id: null,
        name: '',
        description: '',
        students: [],
        objectives: [],
        clazz: { id: parseInt(paramClass)},
        grade: { id: null}
    };


    if (paramCourse == $scope.location.PARAM_CREATE_NEW_COURSE_TOPIC_ID) {
        $scope.panelCaption = 'Nieuw vakthema aanmaken';
        $scope.btnText = 'Aanmaken';
        $http({
            method: 'GET', url: 'http://localhost:8080/class/id/' + paramClass + '/students',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            response.forEach(function (item) {
                $scope.addStudents(item);
            });
        });

        $http({
            method: 'GET', url: 'http://localhost:8080/class/id/' + paramClass + '/grade',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.info.grade.id = response.id;
            $http({
                method: 'GET', url: 'http://localhost:8080/grade/id/' + response.id+ '/objectives',
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                response.forEach(function (item) {
                    $scope.addObjectives(item);

                });
                $scope.refresh();
            })
        })

    } else if(paramCourse){
        $scope.panelCaption = 'Vakthema bewerken';
        $scope.btnText = 'Opslaan';
    }

    $scope.submitForm = function () {
        $scope.students.forEach(function (student){
            console.log(student);
            if(student.checked === true)
                $scope.info.students.push({id: student.studentObj.id});
        });

        $scope.objectives.forEach(function (objective){
            console.log(objective);
            if(objective.checked === true)
                $scope.info.objectives.push({id: objective.objectiveObj.id});
        });


        console.log($scope.info);
        $http({
            method: 'POST', url: 'http://localhost:8080/coursetopic/', data: $scope.info,
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $http({
                method: 'POST', url: 'http://localhost:8080/coursetopic/students/' + response.id, data: $scope.info.students,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {

            });

            $http({
                method: 'POST', url: 'http://localhost:8080/coursetopic/objectives/' + response.id, data: $scope.info.objectives,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.location.openPage($scope.location.MY_CLASSES);
                $scope.location.setParameter($scope.location.PARAM_MANAGE_CLASS_ID, paramClass);
                $scope.createAlertCookie('Vakthema toegevoegd.');
            });

        });

    };


    $scope.toElementIdStudents = function (id) {
        return STUDENTS_LIST_ITEM_PREFIX + id;
    };

    $scope.toElementIdObjectives = function (id) {
        return OBJECTIVES_LIST_ITEM_PREFIX + id;
    };

    // Update the Angular controls that have been added in the HTML
    $scope.refresh = function () {
        $compile(STUDENTS_LIST_ELEMENT)($scope);
        $compile(OBJECTIVES_LIST_ELEMENT)($scope);
    };


    $scope.addStudents = function (student) {
        var studentObj = {'studentObj': student, 'checked': false};
        $scope.students.set(student.id, studentObj);

        var html = '<tr id="' + $scope.toElementIdStudents(student.id) + '">' +
            '<td align="center"><input type="checkbox" ng-model="students.get(' + student.id + ').checked"></td>' +
            '<td>' + student.firstName + ' ' + student.lastName + '</td>';

        html += '</tr>';
        var element = document.createElement('tr');
        STUDENTS_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };


    $scope.addObjectives= function (objective) {
        var objectiveObj = {'objectiveObj': objective, 'checked': false};
        $scope.objectives.set(objective.id, objectiveObj);

        var html = '<tr id="' + $scope.toElementIdObjectives(objective.id) + '">' +
            '<td align="center"><input type="checkbox" ng-model="objectives.get(' + objective.id + ').checked"></td>' +
            '<td>' + objective.name +'</td>';

        html += '</tr>';
        var element = document.createElement('tr');
        OBJECTIVES_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };



    $scope.toggleAll = function (table) {
        if(table === "students") {
            var cntChecked = 0;
            $scope.students.forEach(function (student) {
                if (student.checked)
                    cntChecked++;
            });
            if (cntChecked == $scope.students.size)
                $scope.selectNone(table);
            else
                $scope.selectAll(table);
        }else if(table === "objectives"){
            var cntChecked = 0;
            $scope.objectives.forEach(function (objective) {
                if (objective.checked)
                    cntChecked++;
            });
            if (cntChecked == $scope.objectives.size)
                $scope.selectNone(table);
            else
                $scope.selectAll(table);
        }
        $scope.refresh();
    };

    $scope.selectNone = function (table) {
        if(table === "students") {
            $scope.students.forEach(function (student) {
                student.checked = false;
            });
        }else if(table === "objectives") {
            $scope.objectives.forEach(function (objective) {
                objective.checked = false;
            });
        }
    };


    $scope.selectAll = function (table) {
        if(table === "students") {
            $scope.students.forEach(function (student) {
                student.checked = true;
            });
        }else if(table === "objectives") {
            $scope.objectives.forEach(function (objective) {
                objective.checked = true;
            });
        }
    };
    
    
});
