app.controller('ListUsersController', function ($scope, $compile) {
    // TODO finish controller
    const USER_LIST_ITEM_PREFIX = 'user-list-item-';
    const USER_LIST_ELEMENT = document.getElementById('table-user-list-body');

    $scope.userList = new Map();

    $scope.toElementId = function (id) {
        return USER_LIST_ITEM_PREFIX + id;
    };

    $scope.addUser = function (id, name, email, type) {
        $scope.removeUser(id);

        $scope.userList.set(id, {
            name: name,
            email: email,
            type: type
        });

        var html = '<tr id="' + $scope.toElementId(id) + '">' +
            '<td>' + name + '</td><td>' + email + '</td><td>' + type + '</td>' +
            '<td class="text-info"><span role="button" class="glyphicon glyphicon-edit"></span></td>' +
            '<td class="text-danger" data-ng-click="removeUser(' + id + ')"><span role="button" class="glyphicon glyphicon-remove"></span></td>' +
            '</tr>';

        var element = document.createElement('tr');
        USER_LIST_ELEMENT.appendChild(element);
        element.outerHTML = html;
    };

    $scope.removeUser = function (id) {
        $scope.userList.delete(id);
        var element = document.getElementById($scope.toElementId(id));
        if (element !== null) {
            element.parentElement.removeChild(element);
        }
    };

    $scope.refresh = function () {
        $compile(USER_LIST_ELEMENT)($scope);
    };

    $scope.addUser(0, 'Beerten Hilde', 'hilde.beerten@tihh.be', 'Administrator');
    $scope.addUser(1, 'Bonné Martine', 'martine.bonne@tihh.be', 'Leerkracht');
    $scope.addUser(2, 'Charlier Aaron', 'aaroncharlier@hotmail.com', 'Leerling');
    $scope.addUser(3, 'Cleeren Debra-Lynn', 'debralynn.cleeren@gmail.com', 'Ouder');
    $scope.addUser(4, 'Coenen André', 'andre.coenen@tihh.be', 'Leerkracht');
    $scope.addUser(5, 'Coenen Dominique', 'coves@telenet.be', 'Ouder');
    $scope.addUser(6, 'Copermans Ellen', 'ellencoper@hotmail.com', 'Leerling');
    $scope.addUser(7, 'De Ridder Frederik', 'frederikderidder@hotmail.com', 'Leerling');
    $scope.addUser(8, 'De Swert Evelien', 'eveliendeswert@gmail.com', 'Leerling');
    $scope.addUser(9, 'Formesyn Katrien', 'katrien.formesyn@tihh.be', 'Administrator');
    $scope.addUser(10, 'Henderix Rembert', 'rembert.henderix@tihh.be', 'Leerkracht');
    $scope.addUser(11, 'Lauwers Chana', 'chana.lauwers@tihh.be', 'Leerkracht');
    $scope.addUser(12, 'Lipkens Chris', 'chris.lipkens@telenet.be', 'Leerling');
    $scope.addUser(13, 'Mentens Linda', 'schome@skynet.be', 'Ouder');
    $scope.addUser(14, 'Olaerts Elke', 'elkeolaerts@hotmail.com', 'Leerling');
    $scope.addUser(15, 'Ozcan Tolgahan', 'tolgahan.ozcan@tihh.be', 'Leerkracht');
    $scope.addUser(16, 'Penne Koen', '	koen_penne@hotmail.com', 'Leerling');
    $scope.addUser(17, 'Pinna Annemie', 'annemiepinna@hotmail.com', 'Leerling');
    $scope.addUser(18, 'Poelmans Marc', 'marcpoelmans001@skynet.be', 'Leerling');
    $scope.addUser(19, 'Pollaris Tony', 'tony.pollaris@gmail.com', 'Ouder');
    $scope.addUser(20, 'Pollaris Tony', 'tony.pollaris@tihh.be', 'Leerkracht');
    $scope.addUser(21, 'Put Jolien', 'jolien_put@hotmail.be', 'Leerling');
    $scope.addUser(22, 'Saksen-coburg Boudenwijn', 'stevenvandormael@hotmail.com', 'Leerling');
    $scope.addUser(23, 'Seys Lieven', 'info@invokemediaworks.be', 'Administrator');
    $scope.addUser(24, 'Silvi Karen', 'silvikaren@hotmail.com', 'Leerling');
    $scope.addUser(25, 'Simons Frankie', 'frankie.simons@telenet.be', 'Ouder');
    $scope.addUser(26, 'Slechten Ilse', 'ilse.slechten@hotmail.com', 'Ouder');
    $scope.addUser(27, 'Snellinx Véronique', 'veronique.snellinx@tihh.be', 'Leerkracht');
    $scope.addUser(28, 'Swinnen Tamara', 'tamara_swinnen@hotmail.com', 'Ouder');
    $scope.addUser(29, 'Van Thielen Heidi', 'heidi.vanthielen@tihh.be', 'Leerkracht');
    $scope.addUser(30, 'Vandevorst Ruben', 'ruben.vandevorst@tihh.be', 'Leerkracht');
    $scope.addUser(31, 'Vandormael Steven', 'steven.vandormael@tihh.be', 'Leerkracht');
    $scope.addUser(32, 'Vannitsen Jens', 'jensvannitsen999@hotmail.com', 'Administrator');
    $scope.addUser(33, 'Verboven Stephanie', 'stephanie_verboven@msn.com', 'Ouder');
    $scope.addUser(34, 'Verjans Marleen', 'marleen.verjans@tihh.be', 'Leerkracht');
    $scope.addUser(35, 'Weutens Anja', 'anja.weutens@tihh.be', 'Leerling');
    $scope.refresh();
});
