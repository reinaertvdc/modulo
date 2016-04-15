package be.lambdaware.model;

import be.lambdaware.dao.ParentInfoDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.StudentInfoEntity;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;

/**
 * @author hendrik
 */
public class ParentModel extends AccountModel {
    private ParentInfoEntity parentInfoEntity;
    private ParentInfoDAO parentInfoDAO;

    public ParentModel() {}

    public ParentModel(UserDAO userDAO, ParentInfoDAO parentInfoDAO) {
        super(userDAO);
        this.parentInfoDAO = parentInfoDAO;
    }

    public ParentInfoEntity getParentInfoEntity() {
        return parentInfoEntity;
    }

    public void setParentInfoEntity(ParentInfoEntity parentInfoEntity) {
        this.parentInfoEntity = parentInfoEntity;
    }

    public void setParentInfoDAO(ParentInfoDAO parentInfoDAO) {
        this.parentInfoDAO = parentInfoDAO;
    }

    @Override
    public void createInDB() throws DataAccessException {
        super.createInDB();

        parentInfoEntity.setUserId(userEntity.getId());
        int id = parentInfoDAO.create(parentInfoEntity);
        parentInfoEntity.setId(id);
    }

    @Override
    public void getFromDB(Integer userId) throws DataAccessException {
        super.getFromDB(userId);
        parentInfoEntity = parentInfoDAO.getByUserId(userId);
    }

    public void getFromDBByParentInfoId(Integer parentInfoId) throws DataAccessException {
        parentInfoEntity = parentInfoDAO.get(parentInfoId);
        userEntity = userDAO.get(parentInfoEntity.getUserId());
    }

    @Override
    public void deleteFromDB() throws DataAccessException {
        parentInfoDAO.delete(parentInfoEntity.getId());

        super.deleteFromDB();
    }

    @Override
    public void updateInDB() throws DataAccessException {
        super.updateInDB();
        parentInfoDAO.update(parentInfoEntity);
    }

    public ArrayList<StudentModel> getChildren(StudentInfoDAO studentInfoDAO) throws DataAccessException {
        ArrayList<StudentModel> children = new ArrayList<>();

        // lus over alle entiteiten die teruggekregen zijn
        for (StudentInfoEntity entity : studentInfoDAO.getByParentId(parentInfoEntity.getId())) {
            // child maken
            StudentModel child = new StudentModel(userDAO, studentInfoDAO);
            // child getten met id
            child.getFromDBByStudentInfoId(entity.getId());
            // child toevoegen aan lijst
            children.add(child);
        }

        // lijst returnen
        return children;
    }

    @Override
    public String toString() {
        return "ParentModel{" +
                "userEntity=" + userEntity +
                "parentInfoEntity=" + parentInfoEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParentModel that = (ParentModel) o;

        return parentInfoEntity.equals(that.parentInfoEntity);

    }
}
