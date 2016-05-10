package be.lambdaware.response;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {

    private static final String unauthorized = "Not authorized.";
    private static final String authHeaderEmpty = "No credentials provided.";
    private static final String loginInvalid = "The provided credentials are incorrect.";

    private static final String userNotFound = "The requested user could not be found.";
    private static final String usersNotFound = "The requested users could not be found.";
    private static final String userNotParent = "The requested user is not a parent.";
    private static final String userNotStudent = "The requested user is not a student.";
    private static final String userNotTeacher = "The requested user is not a teacher.";
    private static final String userNotAdmin = "The requested user is not an admin.";
    private static final String userNoInfo = "The requested user has no student info.";
    private static final String userNoClasses = "The requested user is not enrolled in a class.";
    private static final String userNoTeaching = "The requested user is not teaching any classes.";
    private static final String userEmailExists = "The supplied email is already assigned to a user.";
    private static final String userEnabled = "User successfully enabled.";
    private static final String userDisabled = "User successfully disabled.";
    private static final String userDeleted = "User successfully deleted.";

    private static final String classesNotFound = "The requested classes could not be found.";
    private static final String classNotFound = "The requested class could not be found.";
    private static final String classAddedStudent = "Student successfully added to class.";
    private static final String classDeletedStudent = "Student successfully deleted from class.";
    private static final String classAddedTeacher = "Teacher successfully assigned to class.";

    private static final String certificateNotFound = "The requested certificate could not be found.";
    private static final String certificatesNotFound = "The requested certificates could not be found.";
    private static final String certificateEnabled = "Certificate successfully enabled.";
    private static final String certificateDisabled = "Certificate successfully disabled.";
    private static final String certificateDeleted = "Certificate successfully deleted.";
    private static final String certificateStudentAdded = "Certificate successfully added to student.";

    private static final String subCertificatesNotFound = "The requested subcertificates could not be found.";
    private static final String objectivesNotFound = "The requested objectives could not be found.";

    private static final String gradeNotFound = "The requested grade could not be found.";
    private static final String gradesNotFound = "The requested certificates could not be found.";
    private static final String gradeEnabled = "Grade successfully enabled.";
    private static final String gradeDisabled = "Grade successfully disabled.";
    private static final String gradeDeleted = "Grade successfully deleted.";
    private static final String gradeStudentAdded = "Grade successfully added to student.";

    private static final String studentInfoNotFound = "The requested student info could not be found.";

    private static final String bgvScoresNotFound = "The requested BGV scores could not be found";
    private static final String pavScoresNotFound = "The requested PAV scores could not be found";

    private static final String tasksNotFound = "The requested tasks could not be found.";
    private static final String taskNotFound = "The requested task could not be found.";
    private static final String taskScoreNotFound = "The requested taskscore could not be found.";
    private static final String taskDeleted = "Task successfully deleted.";

    private static final String taskScoresDeleted = "Task scores successfully deleted.";
    private static final String fileNotUploaded = "File could not be uploaded.";
    private static final String fileUploaded = "File successfully uploaded.";
    private static final String fileDownloaded = "File successfully downloaded.";
    private static final String fileNotDownloaded = "File could not be downloaded.";

    public static final ResponseEntity<?> AUTH_HEADER_EMPTY = StringMessage.asEntity(authHeaderEmpty, HttpStatus.FORBIDDEN);
    public static final ResponseEntity<?> LOGIN_INVALID = StringMessage.asEntity(loginInvalid, HttpStatus.FORBIDDEN);
    public static final ResponseEntity<?> UNAUTHORIZED = StringMessage.asEntity(unauthorized, HttpStatus.UNAUTHORIZED);

    public static final ResponseEntity<?> USER_NOT_FOUND = StringMessage.asEntity(userNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> USERS_NOT_FOUND = StringMessage.asEntity(usersNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> USER_NOT_PARENT = StringMessage.asEntity(userNotParent, HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> USER_NOT_STUDENT = StringMessage.asEntity(userNotStudent, HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> USER_NOT_TEACHER = StringMessage.asEntity(userNotTeacher, HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> USER_NOT_ADMIN = StringMessage.asEntity(userNotAdmin, HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> USER_NO_INFO= StringMessage.asEntity(userNoInfo, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> USER_NO_CLASSES = StringMessage.asEntity(userNoClasses, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> USER_NO_TEACHING = StringMessage.asEntity(userNoTeaching, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> USER_EMAIL_EXISTS = StringMessage.asEntity(userEmailExists, HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<?> USER_ENABLED = StringMessage.asEntity(userEnabled, HttpStatus.OK);
    public static final ResponseEntity<?> USER_DISABLED = StringMessage.asEntity(userDisabled, HttpStatus.OK);
    public static final ResponseEntity<?> USER_DELETED = StringMessage.asEntity(userDeleted, HttpStatus.OK);

    public static final ResponseEntity<?> CLASSES_NOT_FOUND = StringMessage.asEntity(classesNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> CLASS_NOT_FOUND = StringMessage.asEntity(classNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> CLASS_ADDED_STUDENT = StringMessage.asEntity(classAddedStudent, HttpStatus.OK);
    public static final ResponseEntity<?> CLASS_ADDED_TEACHER = StringMessage.asEntity(classAddedTeacher, HttpStatus.OK);
    public static final ResponseEntity<?> CLASS_DELETED_STUDENT = StringMessage.asEntity(classDeletedStudent, HttpStatus.OK);

    public static final ResponseEntity<?> CERTIFICATE_NOT_FOUND = StringMessage.asEntity(certificateNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> CERTIFICATES_NOT_FOUND = StringMessage.asEntity(certificatesNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> CERTIFICATE_ENABLED = StringMessage.asEntity(certificateEnabled, HttpStatus.OK);
    public static final ResponseEntity<?> CERTIFICATE_DISABLED = StringMessage.asEntity(certificateDisabled, HttpStatus.OK);
    public static final ResponseEntity<?> CERTIFICATE_DELETED = StringMessage.asEntity(certificateDeleted, HttpStatus.OK);
    public static final ResponseEntity<?> CERTIFICATE_STUDENT_ADD = StringMessage.asEntity(certificateStudentAdded, HttpStatus.OK);

    public static final ResponseEntity<?> SUBCERTIFICATES_NOT_FOUND = StringMessage.asEntity(subCertificatesNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> OBJECTIVES_NOT_FOUND = StringMessage.asEntity(objectivesNotFound, HttpStatus.NOT_FOUND);

    public static final ResponseEntity<?> GRADE_NOT_FOUND = StringMessage.asEntity(gradeNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> GRADES_NOT_FOUND = StringMessage.asEntity(gradeNotFound, HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> GRADE_ENABLED = StringMessage.asEntity(gradeEnabled, HttpStatus.OK);
    public static final ResponseEntity<?> GRADE_DISABLED = StringMessage.asEntity(gradeDisabled, HttpStatus.OK);
    public static final ResponseEntity<?> GRADE_DELETED = StringMessage.asEntity(gradeDeleted, HttpStatus.OK);
    public static final ResponseEntity<?> GRADE_STUDENT_ADD = StringMessage.asEntity(gradeStudentAdded, HttpStatus.OK);

    public static final ResponseEntity<?> STUDENT_INFO_NOT_FOUND = StringMessage.asEntity(studentInfoNotFound,HttpStatus.NOT_FOUND);

    public static final ResponseEntity<?> BGV_SCORES_NOT_FOUND = StringMessage.asEntity(bgvScoresNotFound,HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> PAV_SCORES_NOT_FOUND = StringMessage.asEntity(pavScoresNotFound,HttpStatus.NOT_FOUND);

    public static final ResponseEntity<?> TASKS_NOT_FOUND = StringMessage.asEntity(tasksNotFound,HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> TASK_NOT_FOUND = StringMessage.asEntity(taskNotFound,HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> TASK_DELETED = StringMessage.asEntity(taskDeleted, HttpStatus.OK);

    public static final ResponseEntity<?> TASK_SCORES_DELETED = StringMessage.asEntity(taskScoresDeleted, HttpStatus.OK);
    public static final ResponseEntity<?> TASKSCORE_NOT_FOUND = StringMessage.asEntity(taskScoreNotFound,HttpStatus.NOT_FOUND);

    public static final ResponseEntity<?> FILE_UPLOADED = StringMessage.asEntity(fileUploaded, HttpStatus.OK);
    public static final ResponseEntity<?> FILE_DOWNLOADED = StringMessage.asEntity(fileDownloaded, HttpStatus.OK);
    public static final ResponseEntity<?> FILE_NOT_UPLOADED = StringMessage.asEntity(fileNotUploaded,HttpStatus.NOT_FOUND);
    public static final ResponseEntity<?> FILE_NOT_DOWNLOADED = StringMessage.asEntity(fileNotDownloaded,HttpStatus.NOT_FOUND);

}
