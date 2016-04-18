package be.lambdaware.model;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.ClassCertificateDAO;
import be.lambdaware.dao.ClassDAO;
import be.lambdaware.dao.StudentClassDAO;
import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.entities.ClassCertificateEntity;
import org.springframework.dao.DataAccessException;

/**
 * Created by Vincent on 15/04/16.
 */
public class BGVClassModel extends ClassModel {

    private CertificateModel certificateModel;
    private CertificateDAO certificateDAO;

    public BGVClassModel() {}

    public BGVClassModel(ClassDAO classDAO, CertificateDAO certificateDAO) {
        super(classDAO);
        this.certificateDAO = certificateDAO;
    }

    public CertificateModel getCertificateModel() {
        return certificateModel;
    }

    public void setCertificateModel(CertificateModel certificateModel) {
        this.certificateModel = certificateModel;
    }

    public void getFromDB(Integer id, ClassCertificateDAO classCertificateDAO) throws DataAccessException {
        super.getFromDB(id);

        ClassCertificateEntity classCertificateEntity = classCertificateDAO.getByClass(id);
        certificateModel = new CertificateModel(certificateDAO);
        certificateModel.getFromDB(classCertificateEntity.getCertificateId());
    }

    @Override
    public String toString() {
        return "BGVClassModel{" +
                "certificateModel=" + certificateModel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BGVClassModel that = (BGVClassModel) o;

        return certificateModel.equals(that.certificateModel);
    }
}
