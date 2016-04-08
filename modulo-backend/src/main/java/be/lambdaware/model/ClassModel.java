package be.lambdaware.model;

import be.lambdaware.entities.ClassEntity;

import java.util.List;

/**
 * Created by Vincent on 08/04/16.
 */
public abstract class ClassModel {
    // models
    private TeacherModel teacherModel;
    private List<StudentModel> studentModels;

    // entities
    private ClassEntity classEntity;
}
