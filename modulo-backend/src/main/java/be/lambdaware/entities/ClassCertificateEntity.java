package be.lambdaware.entities;

/**
 * Created by Vincent on 07/04/16.
 */
public class ClassCertificateEntity {
    private Integer classId;
    private Integer certificateId;

    public ClassCertificateEntity() {}

    public ClassCertificateEntity(Integer classId, Integer certificateId) {
        this.classId = classId;
        this.certificateId = certificateId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public String toString() {
        return "ClassCertificateEntity{" +
                "classId=" + classId +
                ", certificateId=" + certificateId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassCertificateEntity that = (ClassCertificateEntity) o;

        if (classId != null ? !classId.equals(that.classId) : that.classId != null) return false;
        return certificateId != null ? certificateId.equals(that.certificateId) : that.certificateId == null;

    }
}
