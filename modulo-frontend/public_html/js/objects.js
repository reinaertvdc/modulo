// enums
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

// user classes
function User(id, name, email, password, details, enabled) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.details = details;
    this.enabled = enabled;
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

// certificate classes
function Certificate(id, name, enabled, subCertificates) {
    this.id = id;
    this.name = name;
    this.enabled = enabled;
    this.subCertificates = subCertificates;
}

function SubCertificate(id, name, customName, subCertificateCategories) {
    this.id = id;
    this.name = name;
    this.customName = customName;
    this.subCertificateCategories = subCertificateCategories;
}

function SubCertificateCategory(id, name, customName, competences) {
    this.id = id;
    this.name = name;
    this.customName = customName;
    this.competences = competences;
}

function Competence(id, name, customName) {
    this.id = id;
    this.name = name;
    this.customName = customName;
}
