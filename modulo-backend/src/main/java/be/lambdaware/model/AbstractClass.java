package be.lambdaware.model;

import java.util.ArrayList;

/**
 * Created by Vincent on 06/04/16.
 */
public abstract class AbstractClass {
    private ArrayList<Student> students;
    private String className;
    private Teacher teacher;

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
