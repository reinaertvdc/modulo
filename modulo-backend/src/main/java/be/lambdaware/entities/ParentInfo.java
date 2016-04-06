package be.lambdaware.entities;

import be.lambdaware.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jensv on 06-Apr-16.
 */
public class ParentInfo {

    private List<Student> children;

    public ParentInfo(){
        children = new ArrayList<Student>();
    }

    public List<Student> getChildren() {
        return children;
    }

    public void setChildren(List<Student> _children) {
        this.children = _children;
    }

    @Override
    public String toString() {
        return "ParentInfo{" +
                "children=" + children +
                '}';
    }

    public void addChild(Student student) {
        children.add(student);
    }

    public void removeChild(Student student) {
        children.remove(student);
    }
}
