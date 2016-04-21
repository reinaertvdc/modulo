package be.lambdaware.model;

import be.lambdaware.dao.CertificateDAO;
import be.lambdaware.dao.StudentBGVScoreDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.CompetenceEntity;
import be.lambdaware.entities.StudentBGVScoreEntity;
import be.lambdaware.entities.StudentInfoEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent
 */
public class StudentModel extends AccountModel {

    private StudentInfoEntity studentInfoEntity;
    private StudentInfoDAO studentInfoDAO;

    private CertificateDAO certificateDAO;
    private StudentBGVScoreDAO studentBGVScoreDAO;

    // TODO StudyProgress bijhouden
    // TODO ClassModel (BGV, PAV) opvragen

    public StudentModel() {
    }

    public StudentModel(UserDAO userDAO, StudentInfoDAO studentInfoDAO) {
        super(userDAO);
        this.studentInfoDAO = studentInfoDAO;
    }

    public List<BGVScoreModel> getBGVScore(int studentId, CertificateDAO certificateDAO, StudentBGVScoreDAO studentBGVScoreDAO) {
        getFromDBByStudentInfoId(studentId);
        int certificateId = studentInfoEntity.getCertificateId();
        this.certificateDAO = certificateDAO;
        this.studentBGVScoreDAO = studentBGVScoreDAO;
        List<CompetenceEntity> competences = certificateDAO.getAllCompetences(certificateId);
        List<BGVScoreModel> scores = new ArrayList<>();
        for (CompetenceEntity competence : competences) {
            try {
                StudentBGVScoreEntity studentBGVScoreEntity = studentBGVScoreDAO.getByStudentAndCompetence(studentInfoEntity.getId(), competence.getId());
                BGVScoreModel scoreModel = new BGVScoreModel();
                scoreModel.setStudentBGVScoreEntity(studentBGVScoreEntity);
                scores.add(scoreModel);
            } catch (Exception e) {

            }
        }
        return scores;
    }


    public StudentInfoEntity getStudentInfoEntity() {
        return studentInfoEntity;
    }

    public void setStudentInfoEntity(StudentInfoEntity studentInfoEntity) {
        this.studentInfoEntity = studentInfoEntity;
    }

    public void setStudentInfoDAO(StudentInfoDAO studentInfoDAO) {
        this.studentInfoDAO = studentInfoDAO;
    }

    @Override
    public void createInDB() throws DataAccessException {
        super.createInDB();

        studentInfoEntity.setUser(userEntity.getId());
        int id = studentInfoDAO.create(studentInfoEntity);
        studentInfoEntity.setId(id);
    }

    @Override
    public void getFromDB(Integer userId) throws DataAccessException {
        super.getFromDB(userId);
        studentInfoEntity = studentInfoDAO.getByUserId(userId);
    }

    public void getFromDBByStudentInfoId(Integer studentInfoId) throws DataAccessException {
        studentInfoEntity = studentInfoDAO.get(studentInfoId);
        userEntity = userDAO.get(studentInfoEntity.getUser());
    }

    public static ArrayList<StudentModel> getAll(StudentInfoDAO studentInfoDAO, UserDAO userDAO) {
        ArrayList<StudentModel> students = new ArrayList<StudentModel>();

        for (StudentInfoEntity entity : studentInfoDAO.getAll()) {
            StudentModel student = new StudentModel();
            student.setStudentInfoEntity(entity);
            student.setUserEntity(userDAO.get(entity.getUser()));
            students.add(student);
        }

        return students;
    }

    @Override
    public void deleteFromDB() throws DataAccessException {
        studentInfoDAO.delete(studentInfoEntity.getId());
        super.deleteFromDB();
    }

    @Override
    public void updateInDB() throws DataAccessException {
        super.updateInDB();
        studentInfoDAO.update(studentInfoEntity);
    }

    public ParentModel getParent(UserDAO userDAO) throws DataAccessException {
        ParentModel parentModel = new ParentModel(userDAO);
        parentModel.getFromDB(studentInfoEntity.getParent());
        return parentModel;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "userEntity=" + userEntity +
                "studentInfoEntity=" + studentInfoEntity +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StudentModel that = (StudentModel) o;

        return studentInfoEntity.equals(that.studentInfoEntity);

    }
}
