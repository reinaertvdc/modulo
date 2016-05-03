package be.lambdaware.response;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StringMessage {

    private String message;

    private StringMessage(String message) {
        this.message = message;
    }

    public static ResponseEntity<?> asEntity(String message, HttpStatus status) {
        StringMessage stringMessage = new StringMessage(message);
        return new ResponseEntity<>(stringMessage, status);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringMessage that = (StringMessage) o;

        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public String toString() {
        String sb = "StringMessage{" + "message='" + message + '\'' +
                '}';
        return sb;
    }

}
