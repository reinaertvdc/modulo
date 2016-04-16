package be.lambdaware.model;

import be.lambdaware.dao.ClassDAO;

import java.util.ArrayList;

/**
 * Created by Vincent on 16/04/16.
 */
public class PAVClassModel extends ClassModel {

    private CertificateModel certificateModel;
    private ArrayList<CourseTopicModel> courseTopicModels; // TODO get-function

    public PAVClassModel() {}

    public PAVClassModel(ClassDAO classDAO) {
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
        return "PAVClassModel{" +
                "certificateModel=" + certificateModel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PAVClassModel that = (PAVClassModel) o;

        return certificateModel.equals(that.certificateModel);
    }
}
