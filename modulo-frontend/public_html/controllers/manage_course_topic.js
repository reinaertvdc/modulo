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
        resit: false,
        description: '',
        students: [],
        objectives: [],
        clazz: {id: parseInt(paramClass)},
        grade: {id: null},
        pavScores: []
    };


    $http({
        method: 'GET', url: $scope.SERVER_ADDRESS + 'class/id/' + paramClass + '/students',
        headers: {'X-auth': $cookies.get("auth")}
    }).success(function (response) {
        response.forEach(function (item) {
            $scope.addStudent(item);
        });
        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'class/id/' + paramClass + '/grade',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            $scope.info.grade.id = response.id;
            $http({
                method: 'GET', url: $scope.SERVER_ADDRESS + 'grade/id/' + response.id + '/objectives',
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                response.forEach(function (item) {
                    $scope.addObjectives(item);

                });
                $scope.loadPage();
                $scope.refresh();
            })
        })
    });


    $scope.loadPage = function () {

        if(paramCourse == $scope.location.PARAM_CREATE_NEW_COURSE_TOPIC_ID)
        {
            $scope.panelCaption = 'Nieuw vakthema aanmaken';
            $scope.btnText = 'Aanmaken';

        }
        else
        if (paramCourse) {
            $scope.panelCaption = 'Vakthema bewerken';
            $scope.btnText = 'Opslaan';

            $http({
                method: 'GET', url: $scope.SERVER_ADDRESS + 'coursetopic/id/' + paramCourse,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.info.description = response.description;
                $scope.info.name = response.name;
                $scope.info.id = response.id;
                $scope.info.resit = response.resit;
                if (response.resit === true) {
                    $scope.setStudents();
                    document.getElementById("resit").checked = true;
                    $scope.changeType();
                }
                $scope.setObjectives();
                $scope.setPAVScores();

                $scope.refresh();
            });
        }
    };

    $scope.setStudents = function () {

        $scope.students.forEach(function (student) {
            student.checked = false;
        });

        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'coursetopic/id/' + paramCourse + '/students',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            response.forEach(function (item) {
                $scope.students.get(item.id).checked = true;
            });
        })
    };


    $scope.setObjectives = function () {

        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'coursetopic/id/' + paramCourse + '/objectives',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            response.forEach(function (item) {
                $scope.objectives.get(item.id).checked = true;
            });
        })
    };

    $scope.setPAVScores = function () {
        $http({
            method: 'GET', url: $scope.SERVER_ADDRESS + 'coursetopic/id/' + paramCourse + '/pavscores',
            headers: {'X-auth': $cookies.get("auth")}
        }).success(function (response) {
            if(response != undefined) {
                response.forEach(function (item) {
                    $scope.info.pavScores.push({id: item.id});
                });
            }
        })
    };



    $scope.submitForm = function () {

        if($scope.info.resit === false){
            $scope.students.forEach(function (student) {
                student.checked = true;
            });
        }

        $scope.students.forEach(function (student){
            if(student.checked === true)
                $scope.info.students.push({id: student.studentObj.id});
        });

        $scope.objectives.forEach(function (objective){

            if(objective.checked === true) {
                console.log(objective.objectiveObj.id);
                $scope.info.objectives.push({id: objective.objectiveObj.id})
            }
        });

        if(paramCourse == $scope.location.PARAM_CREATE_NEW_COURSE_TOPIC_ID) {
            $http({
                method: 'POST', url: $scope.SERVER_ADDRESS + 'coursetopic/', data: $scope.info,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.location.openPage($scope.location.MY_CLASSES);
                $scope.location.setParameter($scope.location.PARAM_MANAGE_CLASS_ID, paramClass);
                $scope.createAlertCookie('Vakthema toegevoegd.');
            });
        }else if(paramCourse) {

            $http({
                method: 'PUT', url: $scope.SERVER_ADDRESS + 'coursetopic/', data: $scope.info,
                headers: {'X-auth': $cookies.get("auth")}
            }).success(function (response) {
                $scope.location.openPage($scope.location.MY_CLASSES);
                $scope.location.setParameter($scope.location.PARAM_MANAGE_CLASS_ID, paramClass);
                $scope.createAlertCookie('Vakthema bewerkt.');
            });
        }
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

    $scope.changeType = function () {
        var checkbox = document.getElementById("resit");
        var list = document.getElementById("studentsList");
        if(checkbox.checked == true) {
            list.style.display = "block";
            $scope.info.resit = true;
        }else{
            list.style.display = "none";
            $scope.info.resit = false;
        }
        $scope.refresh();
    };


    $scope.addStudent = function (student) {
        var studentObj = {'studentObj': student, 'checked': true};
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
