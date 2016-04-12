package be.lambdaware.dao;

import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.StudentInfoEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author martijn
 */
public interface ParentInfoDAO {

    public int create(ParentInfoEntity entity) throws DataAccessException;

    public ParentInfoEntity get(Integer id) throws DataAccessException;

    public List<ParentInfoEntity> getAll() throws DataAccessException;

    public ParentInfoEntity getByUserId(Integer id) throws DataAccessException;

    public void delete(Integer id) throws DataAccessException;

    public void update(ParentInfoEntity entity) throws DataAccessException;
}
