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
    }
};
