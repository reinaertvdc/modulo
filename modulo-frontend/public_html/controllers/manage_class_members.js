app.controller('ManageClassMembersController', function ($scope) {
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
            href: '#Winkelbediende'  ,
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

    var json = '[' +
        '{' +
        '"text": "Parent 1",' +
        '"nodes": [' +
        '{' +
        '"text": "Child 1",' +
        '"nodes": [' +
        '{' +
        '"text": "Grandchild 1"' +
        '},' +
        '{' +
        '"text": "Grandchild 2"' +
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "Child 2"' +
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "Parent 2"' +
        '},' +
        '{' +
        '"text": "Parent 3"' +
        '},' +
        '{' +
        '"text": "Parent 4"' +
        '},' +
        '{' +
        '"text": "Parent 5"' +
        '}' +
        ']';


    var $checkableTree = $('#treeview-checkable').treeview({
        data: defaultData,
        levels: 1,
        showIcon: false,
        showCheckbox: true,
        onNodeChecked: function(event, node) {
            $('#checkable-output').prepend('<p>' + node.text + ' was checked</p>');
        },
        onNodeUnchecked: function (event, node) {
            $('#checkable-output').prepend('<p>' + node.text + ' was unchecked</p>');
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

    /*
    var $tree = $('#treeview12').treeview({
        data: json
    });*/

});


