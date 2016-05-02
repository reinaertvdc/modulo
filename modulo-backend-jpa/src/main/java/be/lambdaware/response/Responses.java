package be.lambdaware.response;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {

    private static String unauthorized = "Not authorized.";
    private static String authHeaderEmpty = "No credentials provided.";
    private static String loginInvalid = "The provided credentials are incorrect.";

    private static String userNotFound = "The requested user could not be found.";
    private static String usersNotFound = "The requested users could not be found.";
    private static String userNotParent = "The requested user is not a parent.";
    private static String userNotStudent = "The requested user is not a student.";
    private static String userNotTeacher = "The requested user is not a teacher.";
    private static String userNotAdmin = "The requested user is not an admin.";
    private static String userNoInfo = "The requested user has no student info.";
    private static String userNoClasses = "The requested user is not enrolled in a class.";
    private static String userNoTeaching = "The requested user is not teaching any classes.";
    private static String userEmailExists = "The supplied email is already assigned to a user.";
    private static String userEnabled = "User successfully enabled.";
    private static String userDisabled = "User successfully disabled.";
    private static String userDeleted = "User successfully deleted.";

    private static String classesNotFound = "The requested classes could not be found.";
    private static String classNotFound = "The requested class could not be found.";
    private static String classAddedStudent = "Student successfully added to class.";
    private static String classAddedTeacher = "Teacher successfully assigned to class.";

    private static String certificateNotFound = "The requested certificate could not be found.";
    private static String certificatesNotFound = "The requested certificates could not be found.";
    private static String certificateEnabled = "Certificate successfully enabled.";
    private static String certificateDisabled = "Certificate successfully disabled.";
    private static String certificateDeleted = "Certificate successfully deleted.";

    private static String gradeNotFound = "The requested grade could not be found.";

    public static ResponseEntity<?> AUTH_HEADER_EMPTY = StringMessage.asEntity(authHeaderEmpty, HttpStatus.FORBIDDEN);
    public static ResponseEntity<?> LOGIN_INVALID = StringMessage.asEntity(loginInvalid, HttpStatus.FORBIDDEN);
    public static ResponseEntity<?> UNAUTHORIZED = StringMessage.asEntity(unauthorized, HttpStatus.UNAUTHORIZED);

    public static ResponseEntity<?> USER_NOT_FOUND = StringMessage.asEntity(userNotFound, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> USERS_NOT_FOUND = StringMessage.asEntity(usersNotFound, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> USER_NOT_PARENT = StringMessage.asEntity(userNotParent, HttpStatus.BAD_REQUEST);
    public static ResponseEntity<?> USER_NOT_STUDENT = StringMessage.asEntity(userNotStudent, HttpStatus.BAD_REQUEST);
    public static ResponseEntity<?> USER_NOT_TEACHER = StringMessage.asEntity(userNotTeacher, HttpStatus.BAD_REQUEST);
    public static ResponseEntity<?> USER_NOT_ADMIN = StringMessage.asEntity(userNotAdmin, HttpStatus.BAD_REQUEST);
    public static ResponseEntity<?> USER_NO_INFO= StringMessage.asEntity(userNoInfo, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> USER_NO_CLASSES = StringMessage.asEntity(userNoClasses, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> USER_NO_TEACHING = StringMessage.asEntity(userNoTeaching, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> USER_EMAIL_EXISTS = StringMessage.asEntity(userEmailExists, HttpStatus.BAD_REQUEST);
    public static ResponseEntity<?> USER_ENABLED = StringMessage.asEntity(userEnabled, HttpStatus.OK);
    public static ResponseEntity<?> USER_DISABLED = StringMessage.asEntity(userDisabled, HttpStatus.OK);
    public static ResponseEntity<?> USER_DELETED = StringMessage.asEntity(userDeleted, HttpStatus.OK);

    public static ResponseEntity<?> CLASSES_NOT_FOUND = StringMessage.asEntity(classesNotFound, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> CLASS_NOT_FOUND = StringMessage.asEntity(classNotFound, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> CLASS_ADDED_STUDENT = StringMessage.asEntity(classAddedStudent, HttpStatus.OK);
    public static ResponseEntity<?> CLASS_ADDED_TEACHER = StringMessage.asEntity(classAddedTeacher, HttpStatus.OK);

    public static ResponseEntity<?> CERTIFICATE_NOT_FOUND = StringMessage.asEntity(certificateNotFound, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> CERTIFICATES_NOT_FOUND = StringMessage.asEntity(certificatesNotFound, HttpStatus.NOT_FOUND);
    public static ResponseEntity<?> CERTIFICATE_ENABLED = StringMessage.asEntity(certificateEnabled, HttpStatus.OK);
    public static ResponseEntity<?> CERTIFICATE_DISABLED = StringMessage.asEntity(certificateDisabled, HttpStatus.OK);
    public static ResponseEntity<?> CERTIFICATE_DELETED = StringMessage.asEntity(certificateDeleted, HttpStatus.OK);

    public static ResponseEntity<?> GRADE_NOT_FOUND = StringMessage.asEntity(gradeNotFound, HttpStatus.NOT_FOUND);
}
