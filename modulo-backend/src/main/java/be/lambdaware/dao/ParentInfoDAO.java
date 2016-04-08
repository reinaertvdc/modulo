package be.lambdaware.dao;

import be.lambdaware.entities.ParentInfoEntity;

import java.util.List;

/**
 * @author martijn
 */
public interface ParentInfoDAO {

    public int create(ParentInfoEntity entity);

    public ParentInfoEntity get(Integer id);

    public List<ParentInfoEntity> getAll();

    public List<ParentInfoEntity> getByUserId(Integer id);

    public void delete(Integer id);

    public void update(ParentInfoEntity entity);
}
