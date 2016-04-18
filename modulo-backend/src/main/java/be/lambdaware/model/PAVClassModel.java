package be.lambdaware.model;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.CourseTopicDAO;
import be.lambdaware.dao.GradeDAO;
import be.lambdaware.dao.StudentClassDAO;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

/**
 * Created by Vincent on 16/04/16.
 */
public class PAVClassModel extends ClassModel {

    private CourseTopicDAO courseTopicDAO;
    private ArrayList<CourseTopicModel> courseTopicModels; // TODO get-function

    private GradeDAO gradeDAO;
    private GradeModel gradeModel;


    public PAVClassModel() {}

    public PAVClassModel(ClassDAO classDAO, CourseTopicDAO courseTopicDAO) {
        super(classDAO);
        this.courseTopicDAO = courseTopicDAO;
    }
}
