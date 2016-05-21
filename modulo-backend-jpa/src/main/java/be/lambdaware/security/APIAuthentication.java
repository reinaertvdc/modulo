package be.lambdaware.security;

import be.lambdaware.enums.UserRole;
import be.lambdaware.models.User;
import be.lambdaware.repos.UserRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.security.MessageDigest;


@Component
public class APIAuthentication {

    @Autowired
    UserRepo userRepo;
    Logger log = Logger.getLogger(getClass());
    private User authenticatedUser;

    public boolean checkLogin(String base64Login) {
        // Decode credentials to form email:password
        String decodedLogin = new String(Base64Utils.decodeFromString(base64Login));

        String email = decodedLogin.split(":")[0];
        String password = decodedLogin.split(":")[1];
        String hashedPassword = SHA512(password);

        User user = userRepo.findByEmail(email);
        if ((user != null) && (user.getPassword().equals(hashedPassword))) {
            authenticatedUser = user;
            return true;
        } else
            return false;
    }

    public String SHA512(String toHash) {
        MessageDigest md;
        byte[] hash;
        try {
            md = MessageDigest.getInstance("SHA-512");
            hash = md.digest(toHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte currentByte : hash) {
                sb.append(Integer.toString((currentByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "BAD HASH";
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public boolean isAdmin() {
        return authenticatedUser.getRole() == UserRole.ADMIN;
    }

    public boolean isTeacher() {
        return authenticatedUser.getRole() == UserRole.TEACHER;
    }

    public boolean isParent() {
        return authenticatedUser.getRole() == UserRole.PARENT;
    }

    public boolean isStudent() {
        return authenticatedUser.getRole() == UserRole.STUDENT;
    }
}
