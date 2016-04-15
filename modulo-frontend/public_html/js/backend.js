var backend = {
    users: [],
    certificates: [],

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
    },
    
    getCertificates: function() {
        if (this.isLoggedIn() && this.user.details.type === UserType.ADMIN) {
            return this.certificates;
        } else {
            return [];
        }
    },

    setCertificateEnabled: function(id, enabled) {
        if (this.isLoggedIn() && this.user.details.type === UserType.ADMIN) {
            for (var i = 0; i < this.certificates.length; i++) {
                if (this.certificates[i].id == id) {
                    this.certificates[i].enabled = enabled;
                }
            }
        }
    }
};
