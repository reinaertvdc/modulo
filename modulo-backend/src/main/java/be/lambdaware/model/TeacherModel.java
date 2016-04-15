package be.lambdaware.model;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.ClassEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 08/04/16.
 */
public class TeacherModel extends AccountModel {

    public TeacherModel() {}

    public TeacherModel(UserDAO userDAO) {
        super(userDAO);
    }

    public ArrayList<ClassModel> getClasses(ClassDAO classDAO) {
        ArrayList<ClassModel> classModels = new ArrayList<>();

//        System.out.println(classDAO.getAllByTeacher(userEntity.getId()));
        // lus over alle teruggekregen entiteiten
        for (ClassEntity entity : classDAO.getAllByTeacher(userEntity.getId())) {

//            System.out.println("ClassName: " + entity.getName());

            // vraag type op

            // maak pav of bgv class aan

            // Class maken
            ClassModel classModel = new ClassModel(classDAO);
            // ClassEntity setten
            classModel.setClassEntity(entity);
            // Class toevoegen aan lijst
            classModels.add(classModel);
        }

        // lijst returnen
        return classModels;
    }
}
