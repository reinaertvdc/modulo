app.controller('MainController', function ($scope, $location) {
    // TODO finish controller
    $scope.backend = {
        users: [
            new User(1, new Name('Hilde', 'Beerten'), 'hilde.beerten@tihh.be', '1234', new AdminDetails()),
            new User(2, new Name('Katrien', 'Formesyn'), 'katrien.formesyn@outlook.com', '1234', new AdminDetails()),

            new User(21, new Name('Martine', 'Bonné'), 'martine.bonne@tihh.be', '1234', new TeacherDetails()),
            new User(4, new Name('André', 'Coenen'), 'andre.coenen@gmail.com', '1234', new TeacherDetails()),
            new User(5, new Name('Rembert', 'Henderix'), 'rembert.henderix@tihh.be', '1234', new TeacherDetails()),
            new User(6, new Name('Chana', 'Lauwers'), 'chana.lauwers@tihh.be', '1234', new TeacherDetails()),

            new User(7, new Name('Aaron', 'Charlier'), 'aaroncharlier@hotmail.com', '1234', new StudentDetails()),
            new User(8, new Name('Ellen', 'Copermans'), 'ellencoper@hotmail.com', '1234', new StudentDetails()),
            new User(9, new Name('Frederik', 'De Ridder'), 'frederikderidder@hotmail.com', '1234', new StudentDetails()),
            new User(10, new Name('Evelien', 'De Swert'), 'eveliendeswert@gmail.com', '1234', new StudentDetails()),
            new User(11, new Name('Chris', 'Lipkens'), 'chris.lipkens@telenet.be', '1234', new StudentDetails()),
            new User(12, new Name('Elke', 'Olaerts'), 'elkeolaerts@hotmail.com', '1234', new StudentDetails()),
            new User(13, new Name('Tolgahan', 'Ozcan'), 'tolgahan.ozcan@gmail.be', '1234', new StudentDetails()),
            new User(14, new Name('Koen', 'Penne'), 'koen_penne@hotmail.com', '1234', new StudentDetails()),
            new User(15, new Name('Annemie', 'Pinna'), 'annemiepinna@hotmail.com', '1234', new StudentDetails()),
            new User(16, new Name('Marc', 'Poelmans'), 'marcpoelmans001@skynet.be', '1234', new StudentDetails()),
            new User(17, new Name('Jolien', 'Put'), 'jolien_put@hotmail.be', '1234', new StudentDetails()),
            new User(18, new Name('Karen', 'Silvi'), 'silvikaren@hotmail.com', '1234', new StudentDetails()),
            new User(19, new Name('Véronique', 'Snellinx'), 'veronique.snellinx@gmail.be', '1234', new StudentDetails()),
            new User(20, new Name('Heidi', 'Van Thielen'), 'heidi.vanthielen@hotmail.be', '1234', new StudentDetails()),
            new User(55, new Name('Ruben', 'Vandevorst'), 'ruben.vandevorst@gmail.be', '1234', new StudentDetails()),
            new User(22, new Name('Steven', 'Vandormael'), 'steven.vandormael@outlook.be', '1234', new StudentDetails()),
            new User(23, new Name('Marleen', 'Verjans'), 'marleen.verjans@gmail.be', '1234', new StudentDetails()),
            new User(24, new Name('Anja', 'Weutens'), 'anja.weutens@tihh.be', '1234', new StudentDetails()),

            new User(25, new Name('Debra-Lynn', 'Cleeren'), 'debralynn.cleeren@gmail.com', '1234', new ParentDetails()),
            new User(26, new Name('Dominique', 'Coenen'), 'coves@telenet.be', '1234', new ParentDetails()),
            new User(27, new Name('Linda', 'Mentens'), 'schome@skynet.be', '1234', new ParentDetails()),
            new User(28, new Name('Tony', 'Pollaris'), 'tony.pollaris@gmail.com', '1234', new ParentDetails()),
            new User(29, new Name('Frankie', 'Simons'), 'frankie.simons@telenet.be', '1234', new ParentDetails()),
            new User(30, new Name('Ilse', 'Slechten'), 'ilse.slechten@hotmail.com', '1234', new ParentDetails()),
            new User(31, new Name('Tamara', 'Swinnen'), 'tamara_swinnen@hotmail.com', '1234', new ParentDetails()),
            new User(32, new Name('Stephanie', 'Verboven'), 'stephanie_verboven@msn.com', '1234', new ParentDetails())
        ],
        certificates: [
            new Certificate(0, 'Metselaar', true, [
                new SubCertificate(0, 'Basistechnieken metselwerk', null, [
                    new SubCertificateCategory(0, 'veilig, hygiënisch en milieubewust werken conform welzijn op het werk en de geldende regelgevingen', null, [
                        new Competence(0, 'ergonomisch werken', null),
                        new Competence(1, 'persoonlijke beschermingsmiddelen gebruiken', null),
                        new Competence(2, 'collectieve beschermingsmiddelen aanbrengen', null),
                        new Competence(3, 'producten met gevaarlijke eigenschappen correct gebruiken', null),
                        new Competence(4, 'veiligheidsvoorschriften en -instructies inzake arbeidsmiddelen toepassen', null),
                        new Competence(5, 'economisch werken', null),
                        new Competence(6, 'afval en restproducten sorteren', null),
                        new Competence(7, 'gereedschap en machines gebruiken', null),
                        new Competence(8, 'gereedschap en machines reinigen', null)
                    ]),
                    new SubCertificateCategory(1, 'noodzakelijke houdingen voor de uitoefening van het beroep aannemen', null, [
                        new Competence(9, 'met zin voor precisie werken', null),
                        new Competence(10, 'zin voor samenwerking tonen', null),
                        new Competence(11, ' doorzettingsvermogen tonen', null)
                    ]),
                    new SubCertificateCategory(2, 'functionele vaardigheden voor de uitoefening van het beroep toepassen', null, [
                        new Competence(12, 'dimensies (lengte, breedte, dikte, oppervlakte, inhoud, …) meten en berekenen', null),
                        new Competence(13, 'meetinstrumenten gebruiken', null),
                        new Competence(14, 'technische tekening gebruiken', null)
                    ]),
                    new SubCertificateCategory(3, 'eigen werkzaamheden organiseren', null, [
                        new Competence(15, 'eigen werkzaamheden voorbereiden', null),
                        new Competence(16, 'eigen werkzaamheden uitvoeren', null),
                        new Competence(17, 'eigen werkzaamheden evalueren', null),
                        new Competence(18, 'eigen werkzaamheden bijsturen', null),
                    ]),
                    new SubCertificateCategory(4, 'basistechnieken van eenvoudig metselwerk uitvoeren', null, [
                        new Competence(19, 'mortel aanmaken', null),
                        new Competence(20, 'halfsteense muren metselen', null),
                        new Competence(21, 'éénsteense muren metselen', null),
                        new Competence(22, 'kleine elementen in metselwerk plaatsen', null),
                        new Competence(23, 'voegen uitkrabben', null),
                        new Competence(24, 'meegaand voegen', null),
                        new Competence(25, '', null),
                        new Competence(26, '', null)
                    ])
                ])
            ]),
            new Certificate(1, 'Aanvuller', true, []),
            new Certificate(2, 'Bekister', false, []),
            new Certificate(3, 'Kassier', true, []),
            new Certificate(4, 'Pijpfitter', true, []),
            new Certificate(5, 'Kelner', false, []),
            new Certificate(6, 'Stukadoor', true, []),
            new Certificate(7, 'Slager', true, []),
            new Certificate(8, 'Keukenmedewerker', true, []),
            new Certificate(9, 'Podiumtechnicus', false, []),
            new Certificate(10, 'Plaatser binnenschrijnwerk', true, []),
            new Certificate(11, 'Industrieel schilder', true, []),
            new Certificate(12, 'Demonteur/monteur carrosserie', true, []),
            new Certificate(13, 'Plaatser boven- en ondergrondse kabels en leidingen', true, []),
            new Certificate(14, 'Motorfietsmecanicien', false, []),
            new Certificate(15, 'Verhuizer-inpakker', false, []),
            new Certificate(16, 'Lasser MIG/MAG', true, []),
            new Certificate(17, 'Kok', true, [])
        ],

        user: null,

        attemptLogin: function (email, password) {
            console.log("attemptlogin");
            var matchFound = false;
            this.users.forEach(function (item) {
                if (item.email === email && item.password === password) {
                    matchFound = true;
                    console.log(matchFound);
                    $scope.backend.user = item;
                    return true;
                }
            });
            return matchFound;
        },

        logOut: function () {
            this.user = null;
        },

        isLoggedIn: function () {
            return this.user !== null;
        },

        getUser: function () {
            return this.isLoggedIn() ? new User(this.user.id, this.user.name, this.user.email, null, this.user.details) : null;
        },

        getUsers: function () {

            console.log("logged in?" + $scope.backend.isLoggedIn());


            if (this.isLoggedIn() && this.user.details.type === UserType.ADMIN
            ) {
                return this.users;
            }
            else {
                return [];
            }
        },

        getCertificates: function () {
            return this.certificates;

            // TODO onderstaande check werkt niet voor een of andere reden
            if (this.isLoggedIn() && this.user.details.type === UserType.ADMIN) {
                return this.certificates;
            } else {
                return [];
            }
        }
        ,

        setCertificateEnabled: function (id, enabled) {
            if (this.isLoggedIn() && this.user.details.type === UserType.ADMIN) {
                for (var i = 0; i < this.certificates.length; i++) {
                    if (this.certificates[i].id == id) {
                        this.certificates[i].enabled = enabled;
                    }
                }
            }
        }
    };

    $scope.account = {
        user: null,
        
        attemptLogin: function(email, password) {
            if ($scope.backend.attemptLogin(email, password)) {
                this.user = $scope.backend.getUser();
                return true;
            }
            return false;
        },

        isLoggedIn: function () {
            return this.user !== null;
        },

        logOut: function () {
            $scope.backend.logOut();
            this.user = null;
        }
    };

    $scope.getActiveMyClassesPanel = function() {
        if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID)) {
            if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID) == $scope.location.PARAM_CREATE_NEW_CLASS_ID && $scope.location.getParameter($scope.location.PARAM_CLASS_TYPE)) {
                return 'views/panels/new_class.html';
            }
            if ($scope.location.getParameter($scope.location.PARAM_MANAGE_COURSE_TOPIC_ID)) {
                return 'views/panels/manage_course_topic.html';
            } else {
                if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_MEMBERS)) {
                    return 'views/panels/manage_class_members.html';
                } else if ($scope.location.getParameter($scope.location.PARAM_MANAGE_CLASS_ID) != $scope.location.PARAM_CREATE_NEW_CLASS_ID) {
                    return 'views/panels/manage_class.html';
                } else {
                    return 'views/panels/list_classes.html';
                }
            }
        } else {
            return 'views/panels/list_classes.html';
        }
    };

    // TODO remove when finished developing
    $scope.account.attemptLogin('martine.bonne@tihh.be', '1234');

    $scope.location = {
        HOME: 'startpagina',
        NOT_FOUND: 'pagina_niet_gevonden',
        ACCESS_DENIED: 'toegang_geweigerd',
        USER_MANAGEMENT: 'gebruikersbeheer',
        COURSES: 'opleidingen',
        MY_CLASSES: 'mijn_klassen',
        SCORES_MANAGEMENT: 'puntenbeheer',
        STUDENT_PROGRESS: 'voortgang_studenten',

        PARAM_EDIT_USER_ID: 'gebruiker',

        PARAM_MANAGE_CLASS_ID: 'klas',
        PARAM_CREATE_NEW_CLASS_ID: 'nieuw',
        PARAM_CLASS_TYPE: 'type',
        PARAM_MANAGE_COURSE_TOPIC_ID: 'vakthema',
        PARAM_CREATE_NEW_COURSE_TOPIC_ID: 'nieuw',
        PARAM_MANAGE_CLASS_MEMBERS: 'leerlingen', 

        pathToPage: function (path) {
            return path.replace(/\//g, '');
        },

        getRequestedPage: function () {
            return this.pathToPage($location.path());
        },

        pageExists: function (page) {
            return page === ''
                || page === this.USER_MANAGEMENT
                || page === this.COURSES
                || page === this.MY_CLASSES
                || page === this.SCORES_MANAGEMENT
                || page === this.STUDENT_PROGRESS
        },

        userCanAccessPage: function (page) {

            return true;

            if (page === '') {
                return true;
            } else if (!$scope.account.isLoggedIn()) {
                return false;
            } else if (page === this.USER_MANAGEMENT) {
                return $scope.account.user.details.type === UserType.ADMIN;
            } else if (page === this.COURSES) {
                return $scope.account.user.details.type === UserType.ADMIN;
            } else if (page === this.MY_CLASSES) {
                return $scope.account.user.details.type === UserType.TEACHER;
            } else if (page === this.SCORES_MANAGEMENT) {
                return $scope.account.user.details.type === UserType.TEACHER;
            } else if (page === this.STUDENT_PROGRESS) {
                return $scope.account.user.details.type === UserType.TEACHER
                    || $scope.account.user.details.type === UserType.STUDENT
                    || $scope.account.user.details.type === UserType.PARENT;
            }

            return false;
        },

        getPageToOpen: function () {
            if (!this.pageExists(this.getRequestedPage())) {
                return this.NOT_FOUND;
            } else if (!this.userCanAccessPage(this.getRequestedPage())) {
                return this.ACCESS_DENIED;
            } else if (this.getRequestedPage() === '') {
                return this.HOME;
            }

            return this.getRequestedPage();
        },

        getUrlToPageToOpen: function() {
            return 'views/pages/' + this.getPageToOpen() + '.html';
        },

        openPage: function (page) {
            this.removeAllParameters();
            $location.path(page);
        },

        getParameter: function (name) {
            return $location.search()[name];
        },

        setParameter: function (name, value) {
            console.log(name + " " + value);
            return $location.search(name, value);
        },

        removeAllParameters: function () {
            $location.url($location.path());
        }
    };

    $scope.form = {
        isValidEmail: function (value) {
            return value.length > 0;
        },

        isValidPassword: function (value) {
            return value.length > 0;
        }
    };
});
