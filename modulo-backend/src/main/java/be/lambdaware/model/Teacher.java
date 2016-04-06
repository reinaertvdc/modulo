package be.lambdaware.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 06/04/16.
 */
public class Teacher extends Account {
    ArrayList<AbstractClass> classes;

    public ArrayList<AbstractClass> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<AbstractClass> classes) {
        this.classes = classes;
    }
}
