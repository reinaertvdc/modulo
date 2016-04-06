package be.lambdaware.jdbc;

import be.lambdaware.dao.ParentInfoDAO;
import be.lambdaware.entities.ParentInfo;
import be.lambdaware.mappers.ParentInfoMapper;
import be.lambdaware.mappers.UserMapper;

import java.util.List;

/**
 * Created by jensv on 06-Apr-16.
 */
public class ParentInfoDAOImpl extends AbstractDAOImpl implements ParentInfoDAO{

    @Override
    public int create(ParentInfo entity) {
        return 0;
    }

    @Override
    public ParentInfo get(Integer id) {
        String SQL = "SELECT * FROM `users` as U INNER JOIN `student_info` AS t ON U.`id` = t.`user_id` WHERE `parent_id` = ?";
        ParentInfo entity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ParentInfoMapper());
        //TODO catch SQL Exception
        return entity;
    }

    @Override
    public List<ParentInfo> getAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(ParentInfo entity) {

    }
}
