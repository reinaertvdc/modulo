// TODO probably should translate this to English, need to provide translation dictionary for displaying in that case
var UserType = Object.freeze({
    ADMIN: 'Administrator',
    TEACHER: 'Leerkracht',
    STUDENT: 'Student',
    PARENT: 'Ouder'
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

function User(id, email, password, type, details) {
    // TODO shouldn't User contain id of details instead of other way around?
    this.id = id;
    this.email = email;
    this.password = password;
    this.type = type;
    this.details = details;
}

function ParentDetails(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
}

function StudentDetails(firstName, lastName, parent, birthdate, birthPlace, nationality, nationalIdentificationNumber, street, houseNumber, postalCode, city, phoneParent, phoneCell, bankAccount) {
    // TODO only one parent?
    this.firstName = firstName;
    this.lastName = lastName;
    // TODO not also the other way around?
    this.parent = parent;
    this.birthdate = birthdate;
    this.birthPlace = birthPlace;
    this.nationality = nationality;
    this.nationalIdentificationNumber = nationalIdentificationNumber;
    this.street = street;
    this.houseNumber = houseNumber;
    this.postalCode = postalCode;
    this.city = city;
    // TODO shouldn't this be a property of the parent?
    this.phoneParent = phoneParent;
    this.phoneCell = phoneCell;
    this.bankAccount = bankAccount;
}

function TeacherDetails() {
}

function AdminDetails() {
}

function Class(id, teacher, name, type, students, topics) {
    this.id = id;
    // TODO not also the other way around?
    this.teacher = teacher;
    this.name = name;
    this.type = type;
    this.students = students;
    // TODO how does class_topics table work again?
    this.topics = topics;
    // TODO how does class_certificate table work?
}

function Certificate(id, name, enabled) {
    this.id = id;
    this.name = name;
    this.enabled = enabled;
}

function SubCertificate(id, certificate, name, description, customName, customDescription, enabled) {
    this.id = id;
    // TODO not also keep list the other way around?
    this.certificate = certificate;
    this.name = name;
    this.description = description;
    this.customName = customName;
    this.customDescription = customDescription;
    this.enabled = enabled;
}

function SubCertificateCategory(id, subCertificate, name, description, customName, customDescription, enabled) {
    this.id = id;
    this.subCertificate = subCertificate;
    this.name = name;
    this.description = description;
    this.customName = customName;
    this.customDescription = customDescription;
    this.enabled = enabled;
}

function Grade(id, name) {
    this.id = id;
    this.name = name;
}

function Competence(id, subCertificateCategory, name, description, customName, customDescription, enabled) {
    // TODO always the same, maybe create common table?
    this.id = id;
    this.subCertificateCategory = subCertificateCategory;
    this.name = name;
    this.description = description;
    this.customName = customName;
    this.customDescription = customDescription;
    this.enabled = enabled;
}

function Objective(id, grade, courseTopic, name, customName) {
    this.id = id;
    this.grade = grade;
    this.courseTopic = courseTopic;
    this.name = name;
    this.customName = customName;
}

function BgvScore(id, student, competence, value, dateGraded, comments) {
    this.id = id;
    this.student = student;
    this.competence = competence;
    // TODO 'value' instead of 'score'?
    this.value = value;
    // TODO 'dateGraded' instead of 'gradedDate'?
    this.dateGraded = dateGraded;
    // TODO 'comments' instead of 'remarks'?
    this.comments = comments;
}

function PavScore(id, student, objective, value, dateGraded, comments) {
    // TODO just one minor difference with BgvScore, maybe merge?
    this.id = id;
    this.student = student;
    this.objective = objective;
    this.value = value;
    this.dateGraded = dateGraded;
    this.comments = comments;
}
