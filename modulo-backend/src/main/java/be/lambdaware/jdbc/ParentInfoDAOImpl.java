package be.lambdaware.jdbc;

import be.lambdaware.dao.ParentInfoDAO;
import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.mappers.ParentInfoMapper;

/**
 * @author hendrik
 */
public class ParentInfoDAOImpl extends AbstractDAOImpl implements ParentInfoDAO {

    @Override
    public ParentInfoEntity get(Integer id) {
        String SQL = "SELECT * FROM `parent_info` WHERE `id` = ?";
        ParentInfoEntity parentInfoEntity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ParentInfoMapper());
        return parentInfoEntity;
    }
}
