package be.lambdaware.model;

import be.lambdaware.dao.*;
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

    public ArrayList<ClassModel> getClasses(ClassDAO classDAO, CertificateDAO certificateDAO, ClassCertificateDAO classCertificateDAO) {
        ArrayList<ClassModel> classModels = new ArrayList<>();

//        System.out.println(classDAO.getAllByTeacher(userEntity.getId()));
        // lus over alle teruggekregen entiteiten
        for (ClassEntity entity : classDAO.getAllByTeacher(userEntity.getId())) {
            if (entity.getType().toLowerCase().equals("bgv")) {
                BGVClassModel bgvClassModel = new BGVClassModel(classDAO, certificateDAO);
                bgvClassModel.getFromDB(entity.getId(), classCertificateDAO);
                classModels.add(bgvClassModel);
            } else { // if type is pav
                // TODO PAV
            }
        }

        return classModels;
    }
}
