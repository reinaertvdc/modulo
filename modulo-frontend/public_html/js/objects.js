var UserType = Object.freeze({
    ADMIN: 'ADMIN',
    TEACHER: 'TEACHER',
    STUDENT: 'STUDENT',
    PARENT: 'PARENT'
});

var ClassType = Object.freeze({
    BGV: 'BGV',
    PAV: 'PAV'
});

var Score = Object.freeze({
    OFFERED: 'A',
    PRACTICED: 'I',
    ACQUIRED: 'V'
});

function User(id, name, email, password, details) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.details = details;
}

function AdminDetails() {
    this.type = UserType.ADMIN;
}

function TeacherDetails() {
    this.type = UserType.TEACHER;
}

function StudentDetails() {
    this.type = UserType.STUDENT;
}

function ParentDetails() {
    this.type = UserType.PARENT;
}

function Name(first, last) {
    this.first = first;
    this.last = last;
}
