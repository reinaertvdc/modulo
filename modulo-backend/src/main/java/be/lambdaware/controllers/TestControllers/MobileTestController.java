package be.lambdaware.controllers.TestControllers;

import be.lambdaware.dao.*;
import be.lambdaware.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hendrik
 */
@RestController
@RequestMapping("/mobile")
public class MobileTestController {

    @Autowired
    SubCertificateDAO subCertificateDAO;

    @Autowired
    SubCertificateCategoryDAO subCertificateCategoryDAO;

    @Autowired
    CompetenceDAO competenceDAO;

    @Autowired
    ClassDAO classDAO;

    @Autowired
    CertificateDAO certificateDAO;

    @Autowired
    StudentInfoDAO studentInfoDAO;

    @Autowired
    UserDAO userDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public List<CompetenceModel>  get() {
        SubCertificateModel bekisting = new SubCertificateModel(subCertificateDAO);
        bekisting.getFromDB(1);

        List<SubCertificateCategoryModel> subcertificates = bekisting.getSubCertificateCategories(subCertificateCategoryDAO);
        List<CompetenceModel> competences = new ArrayList<>();

        for(SubCertificateCategoryModel sccm : subcertificates) {
            competences.addAll(sccm.getCompetences(competenceDAO));
        }



        return competences;
    }
}
