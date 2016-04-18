package be.lambdaware.model;

import be.lambdaware.dao.ClassDAO;
import be.lambdaware.entities.CertificateEntity;

/**
 * Created by Vincent on 15/04/16.
 */
public class BGVClassModel extends ClassModel {

    private CertificateModel certificateModel;

    public BGVClassModel() {}

    public BGVClassModel(ClassDAO classDAO) {
        super(classDAO);
    }

    public CertificateModel getCertificateModel() {
        return certificateModel;
    }

    public void setCertificateModel(CertificateModel certificateModel) {
        this.certificateModel = certificateModel;
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
