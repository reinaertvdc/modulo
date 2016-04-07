package be.lambdaware.entities;

/**
 * Created by Vincent on 07/04/16.
 */
public class ClassCertificateEntity {
    private ClassEntity classEntity;
    private CertificateEntity certificateEntity;


    public ClassCertificateEntity() {}

    public ClassCertificateEntity(CertificateEntity certificateEntity, ClassEntity classEntity) {
        this.certificateEntity = certificateEntity;
        this.classEntity = classEntity;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public CertificateEntity getCertificateEntity() {
        return certificateEntity;
    }

    public void setCertificateEntity(CertificateEntity certificateEntity) {
        this.certificateEntity = certificateEntity;
    }

    @Override
    public String toString() {
        return "ClassCertificateEntity{" +
                "classEntity=" + classEntity +
                ", certificateEntity=" + certificateEntity +
                '}';
    }
}
