package be.lambdaware.model;

import be.lambdaware.entities.StudentInfo;

/**
 * Created by Vincent on 06/04/16.
 */
public class Student extends Account{
    private StudentInfo _studentInfo;

    public StudentInfo getStudentInfo() {
        return _studentInfo;
    }

    public void setStudentInfo(StudentInfo _studentInfo) {
        this._studentInfo = _studentInfo;
    }
}
