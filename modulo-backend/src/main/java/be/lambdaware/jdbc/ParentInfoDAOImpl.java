package be.lambdaware.jdbc;

import be.lambdaware.dao.ParentInfoDAO;
import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.mappers.ParentInfoMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author martijn
 */
public class ParentInfoDAOImpl extends AbstractDAOImpl implements ParentInfoDAO {

    @Override
    public int create(ParentInfoEntity entity) throws DataAccessException  {
        String SQL = "INSERT INTO `parent_info` (`user_id`,`first_name`,`last_name`) VALUES (?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getUserId());
                statement.setString(2, entity.getFirstName());
                statement.setString(3, entity.getLastName());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public ParentInfoEntity get(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `parent_info` WHERE `id` = ?";
        ParentInfoEntity parentInfoEntity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ParentInfoMapper());
        return parentInfoEntity;
    }

    @Override
    public List<ParentInfoEntity> getAll() throws DataAccessException {
        String SQL = "SELECT * FROM `parent_info`";
        List<ParentInfoEntity> parentInfoEntities = jdbcTemplate.query(SQL, new ParentInfoMapper());
        return parentInfoEntities;
    }

    @Override
    public ParentInfoEntity getByUserId(Integer id) throws DataAccessException {
        String SQL = "SELECT * FROM `parent_info` WHERE `user_id` = ?";
        ParentInfoEntity parentInfoEntity = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ParentInfoMapper());
        return parentInfoEntity;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String SQL = "DELETE FROM `parent_info` WHERE `id` = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void update(ParentInfoEntity entity) throws DataAccessException {
        String SQL = "UPDATE `parent_info` SET `user_id` = ?, `first_name` = ?,`last_name` = ? WHERE `id` = ?";
        jdbcTemplate.update(SQL, entity.getUserId(),entity.getFirstName(), entity.getLastName(),entity.getId());
    }
}
