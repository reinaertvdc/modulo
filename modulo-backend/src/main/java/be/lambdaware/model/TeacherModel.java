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

            ClassModel classModel;
            // vraag type op
            if (entity.getType().toLowerCase().equals("bgv")) {
                // maak pav of bgv class aan
                classModel = new BGVClassModel(classDAO);
                System.out.println("BGV " + classModel);
            } else { // if (entity.getType().toLowerCase().equals("pav"))
                classModel = new PAVClassModel(classDAO);
                System.out.println("PAV" + classModel);
            }

            // ClassEntity setten
            classModel.setClassEntity(entity);
            // Class toevoegen aan lijst
            classModels.add(classModel);
        }

        // lijst returnen
        return classModels;
    }
}
