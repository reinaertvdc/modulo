/*
 * Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.doc;

/**
 * Part of: modulobackend
 * User: Hendrik
 * Date: 2/05/2016
 * Time: 21:24
 */
public class MethodObject {
    private String method;
    private String value;

    public MethodObject(String method, String value) {
        this.method = method;
        this.value = value;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
