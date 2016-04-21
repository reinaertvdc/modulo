app.controller('ManageClassMembersController', function ($scope, $http, $compile) {
    // TODO implement controller
    var defaultData = [
        {
            text: 'Metselaar',
            href: '#Metselaar',
            tags: ['2'],
            nodes: [
                {
                    text: 'Joni Gijsens',
                    href: '#Joni Gijsens',
                    tags: ['0']
                },
                {
                    text: 'Kjell Swinnen',
                    href: '#Kjell Swinnen',
                    tags: ['0']
                }
            ]
        },
        {
            text: 'Industrieel verpakker',
            href: '#Industrieel verpakker',
            tags: ['2'],
            nodes: [
                {
                    text: 'Joni Gijsens',
                    href: '#Joni Gijsens',
                    tags: ['0']
                },
                {
                    text: 'Kjell Swinnen',
                    href: '#Kjell Swinnen',
                    tags: ['0']
                }
            ]
        },
        {
            text: 'Plaatlasser',
            href: '#Plaatlasser',
            tags: ['2'],
            nodes: [
                {
                    text: 'Joni Gijsens',
                    href: '#Joni Gijsens',
                    tags: ['0']
                },
                {
                    text: 'Kjell Swinnen',
                    href: '#Kjell Swinnen',
                    tags: ['0']
                }
            ]
        },
        {
            text: 'Banketbakker',
            href: '#Banketbakker',
            tags: ['2'],
            nodes: [
                {
                    text: 'Joni Gijsens',
                    href: '#Joni Gijsens',
                    tags: ['0']
                },
                {
                    text: 'Kjell Swinnen',
                    href: '#Kjell Swinnen',
                    tags: ['0']
                }
            ]
        },
        {
            text: 'Winkelbediende',
            href: '#Winkelbediende',
            tags: ['2'],
            nodes: [
                {
                    text: 'Joni Gijsens',
                    href: '#Joni Gijsens',
                    tags: ['0']
                },
                {
                    text: 'Kjell Swinnen',
                    href: '#Kjell Swinnen',
                    tags: ['0']
                }
            ]
        }
    ];

    var json = "" +
        '[' +
            '{"text": "Parent 1",' +
                '"nodes": [' +
                    '{"text": "Child 1",' +
                        '"nodes": [' +
                            '{"text": "Grandchild 1"},' +
                            '{"text": "Grandchild 2"}' +
                        ']' +
                    '},' +
                    '{"text": "Child 2"}' +
                ']' +
            '},' +
            '{"text": "Parent 2"},' +
            '{"text": "Parent 3"},' +
            '{"text": "Parent 4"},' +
            '{"text": "Parent 5"}' +
        ']';

    var jsonSmall = '[' +
        '{"text": "Parent 1", "id": "1"},' +
        '{"text": "Parent 1", "id": "2"},' +
        '{"text": "Parent 1", "id": "3"},' +
        '{"text": "Parent 1", "id": "4"},' +
        '{"text": "Parent 1", "id": "5"}' +
        ']';

        $scope.students = new Map();

        $scope.jsonUsers = "";

        $scope.classId = $scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID);

        $http.get('http://localhost:8080/student/all').success(function (response) {
            response.forEach(function (item) {
                $scope.students.set(item.studentInfoEntity.id, item);
                //console.log("Certificate of student " + item.userEntity.firstName + " " + item.userEntity.lastName + ": " + item.certificateEntity.name);
            });
            $scope.fillJsonUsers();
            $scope.buildTree();

        });

        $scope.fillJsonUsers = function () {
            //todo Mapping van 'certificateName', 'studentArray'
            /*
            var certificateStudents = new Map();
            $scope.students.forEach(function(student, key){
                var certificate = student.certificateEntity;
                if(certificateStudents.has(certificate.name)){
                    certificateStudents.get(certificate.name).push(student);
                }
                else{
                    certificateStudents.set(certificate.name, []);
                    certificateStudents.get(certificate.name).push(student);
                }
            }, $scope.students);

            //loop over map, met per certificate loop over student array
            $scope.jsonUsers += '[';
            var i = 0;
            certificateStudents.forEach(function(students, key){

                $scope.jsonUsers += '{"text": "' + key + '", "nodes": [';
                $scope.addStudents(students);
                //add student

                if(i < certificateStudents.size - 1){
                    $scope.jsonUsers += ']},'
                }
                else {
                    $scope.jsonUsers += ']}'
                }
                i++;
            }, $scope.students);
            $scope.jsonUsers += ']';
*/
            var i = 0;
            $scope.jsonUsers += '[{"text": "Metselaar", "nodes": [';
            $scope.students.forEach(function(student, key){
                if(i < $scope.students.size - 1)
                    $scope.jsonUsers += '{"text": "' + student.userEntity.firstName +' '+ student.userEntity.lastName+'", "studentInfoId": "' + key +'"},';
                else
                    $scope.jsonUsers += '{"text": "' + student.userEntity.firstName +' '+ student.userEntity.lastName+'", "studentInfoId": "' + key +'"}]';
                i++;
            }, $scope.students);
            $scope.jsonUsers += '}]';
        }

        $scope.addStudents = function(students){
            var j = 0;
            //todo loop over array voeg toe
            students.length
        }

        $scope.buildTree = function(){
            //source: https://github.com/jonmiles/bootstrap-treeview/blob/master/public/index.html
            var $checkableTree = $('#treeview-checkable').treeview({
                data: $scope.jsonUsers,
                levels: 1,
                showIcon: false,
                showCheckbox: true,
                onNodeChecked: function (event, node) {
                    //Todo add user to class
                    $('#checkable-output').prepend('<p>' +  node.text + ' Checked with id: ' + node.studentInfoId + '</p>');

                    //console.log("Test1 " + node.text + " Checked with id: " + node.studentInfoId);
                },
                onNodeUnchecked: function (event, node) {
                    //Todo remove user from class
                    $('#checkable-output').prepend('<p>' +  node.text + ' Unchecked with id: ' + node.studentInfoId + '</p>');

                    //console.log("Test2 " + node.text + " Unchecked with id: " + node.studentInfoId);
                }
            });

            var findCheckableNodess = function() {
                return $checkableTree.treeview('search', [ $('#input-check-node').val(), { ignoreCase: false, exactMatch: false } ]);
            };
            var checkableNodes = findCheckableNodess();
            // Check/uncheck/toggle nodes
            $('#input-check-node').on('keyup', function (e) {
                checkableNodes = findCheckableNodess();
                $('.check-node').prop('disabled', !(checkableNodes.length >= 1));
            });
            $('#btn-check-node.check-node').on('click', function (e) {
                $checkableTree.treeview('checkNode', [ checkableNodes, { silent: $('#chk-check-silent').is(':checked') }]);
            });
            $('#btn-uncheck-node.check-node').on('click', function (e) {
                $checkableTree.treeview('uncheckNode', [ checkableNodes, { silent: $('#chk-check-silent').is(':checked') }]);
            });
            $('#btn-toggle-checked.check-node').on('click', function (e) {
                $checkableTree.treeview('toggleNodeChecked', [ checkableNodes, { silent: $('#chk-check-silent').is(':checked') }]);
            });
            // Check/uncheck all
            $('#btn-check-all').on('click', function (e) {
                $checkableTree.treeview('checkAll', { silent: $('#chk-check-silent').is(':checked') });
            });
            $('#btn-uncheck-all').on('click', function (e) {
                $checkableTree.treeview('uncheckAll', { silent: $('#chk-check-silent').is(':checked') });
            });

        }

    });


