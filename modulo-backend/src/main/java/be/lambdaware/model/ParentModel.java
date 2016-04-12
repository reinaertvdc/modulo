package be.lambdaware.model;

import be.lambdaware.dao.ParentInfoDAO;
import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.StudentInfoEntity;

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
    public boolean createInDB() {
        if (!super.createInDB())
            return false;

        parentInfoEntity.setUserId(userEntity.getId());
        int id = parentInfoDAO.create(parentInfoEntity);
        parentInfoEntity.setId(id);

        return  true;
    }

    @Override
    public boolean getFromDB(Integer userId) {
        if (!super.getFromDB(userId))
            return false;

        parentInfoEntity = parentInfoDAO.getByUserId(userId);
        if (parentInfoEntity == null)
            return false;

        return true;
    }

    public boolean getFromDBByParentInfoId(Integer parentInfoId) {
        parentInfoEntity = parentInfoDAO.get(parentInfoId);
        if (parentInfoEntity == null)
            return false;

        userEntity = userDAO.get(parentInfoEntity.getUserId());
        if (userEntity == null)
            return false;

        return true;
    }

    @Override
    public boolean deleteFromDB() {
        parentInfoDAO.delete(parentInfoEntity.getId());

        if (!super.deleteFromDB())
            return false;

        return true;
    }

    @Override
    public boolean updateInDB() {
        if (!super.updateInDB())
            return false;

        parentInfoDAO.update(parentInfoEntity);
        return true;
    }

    public ArrayList<StudentModel> getChildren(StudentInfoDAO studentInfoDAO) {
        ArrayList<StudentModel> children = new ArrayList<>();

        // lus over alle entiteiten die teruggekregen zijn
        for (StudentInfoEntity entity : studentInfoDAO.getByParentId(parentInfoEntity.getId())) {
            // child maken, child getten met id
            StudentModel child = new StudentModel(userDAO, studentInfoDAO);
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
