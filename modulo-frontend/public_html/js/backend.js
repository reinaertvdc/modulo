var backend = {
    users: [
        new User(0, new Name('Hilde', 'Beerten'), 'hilde.beerten@tihh.be', '1234', new AdminDetails()),
        new User(1, new Name('Katrien', 'Formesyn'), 'katrien.formesyn@outlook.com', '1234', new AdminDetails()),

        new User(2, new Name('Martine', 'Bonné'), 'martine.bonne@tihh.be', '1234', new TeacherDetails()),
        new User(3, new Name('André', 'Coenen'), 'andre.coenen@gmail.com', '1234', new TeacherDetails()),
        new User(4, new Name('Rembert', 'Henderix'), 'rembert.henderix@tihh.be', '1234', new TeacherDetails()),
        new User(5, new Name('Chana', 'Lauwers'), 'chana.lauwers@tihh.be', '1234', new TeacherDetails()),

        new User(6, new Name('Aaron', 'Charlier'), 'aaroncharlier@hotmail.com', '1234', new StudentDetails()),
        new User(7, new Name('Ellen', 'Copermans'), 'ellencoper@hotmail.com', '1234', new StudentDetails()),
        new User(8, new Name('Frederik', 'De Ridder'), 'frederikderidder@hotmail.com', '1234', new StudentDetails()),
        new User(9, new Name('Evelien', 'De Swert'), 'eveliendeswert@gmail.com', '1234', new StudentDetails()),
        new User(10, new Name('Chris', 'Lipkens'), 'chris.lipkens@telenet.be', '1234', new StudentDetails()),
        new User(11, new Name('Elke', 'Olaerts'), 'elkeolaerts@hotmail.com', '1234', new StudentDetails()),
        new User(12, new Name('Tolgahan', 'Ozcan'), 'tolgahan.ozcan@gmail.be', '1234', new StudentDetails()),
        new User(13, new Name('Koen', 'Penne'), 'koen_penne@hotmail.com', '1234', new StudentDetails()),
        new User(14, new Name('Annemie', 'Pinna'), 'annemiepinna@hotmail.com', '1234', new StudentDetails()),
        new User(15, new Name('Marc', 'Poelmans'), 'marcpoelmans001@skynet.be', '1234', new StudentDetails()),
        new User(16, new Name('Jolien', 'Put'), 'jolien_put@hotmail.be', '1234', new StudentDetails()),
        new User(17, new Name('Karen', 'Silvi'), 'silvikaren@hotmail.com', '1234', new StudentDetails()),
        new User(18, new Name('Véronique', 'Snellinx'), 'veronique.snellinx@gmail.be', '1234', new StudentDetails()),
        new User(19, new Name('Heidi', 'Van Thielen'), 'heidi.vanthielen@hotmail.be', '1234', new StudentDetails()),
        new User(20, new Name('Ruben', 'Vandevorst'), 'ruben.vandevorst@gmail.be', '1234', new StudentDetails()),
        new User(21, new Name('Steven', 'Vandormael'), 'steven.vandormael@outlook.be', '1234', new StudentDetails()),
        new User(22, new Name('Marleen', 'Verjans'), 'marleen.verjans@gmail.be', '1234', new StudentDetails()),
        new User(23, new Name('Anja', 'Weutens'), 'anja.weutens@tihh.be', '1234', new StudentDetails()),

        new User(24, new Name('Debra-Lynn', 'Cleeren'), 'debralynn.cleeren@gmail.com', '1234', new ParentDetails()),
        new User(25, new Name('Dominique', 'Coenen'), 'coves@telenet.be', '1234', new ParentDetails()),
        new User(26, new Name('Linda', 'Mentens'), 'schome@skynet.be', '1234', new ParentDetails()),
        new User(27, new Name('Tony', 'Pollaris'), 'tony.pollaris@gmail.com', '1234', new ParentDetails()),
        new User(28, new Name('Frankie', 'Simons'), 'frankie.simons@telenet.be', '1234', new ParentDetails()),
        new User(29, new Name('Ilse', 'Slechten'), 'ilse.slechten@hotmail.com', '1234', new ParentDetails()),
        new User(30, new Name('Tamara', 'Swinnen'), 'tamara_swinnen@hotmail.com', '1234', new ParentDetails()),
        new User(31, new Name('Stephanie', 'Verboven'), 'stephanie_verboven@msn.com', '1234', new ParentDetails())
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
        ])
    ],

    user: null,

    attemptLogin: function (email, password) {
        var matchFound = false;
        this.users.forEach(function (item) {
            if (item.email === email && item.password === password) {
                matchFound = true;
                backend.user = item;
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
        if (this.isLoggedIn() && this.user.details.type === UserType.ADMIN) {
            return this.users;
        } else {
            return [];
        }
    }
};
