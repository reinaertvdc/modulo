package be.lambdaware.mappers;

import be.lambdaware.entities.ParentInfoEntity;
import be.lambdaware.entities.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author martijn
 */
public class ParentInfoMapper implements RowMapper<ParentInfoEntity> {

    @Override
    public ParentInfoEntity mapRow(ResultSet resultSet, int row) throws SQLException {
        ParentInfoEntity parentInfoEntity = new ParentInfoEntity();
        parentInfoEntity.setId(resultSet.getInt("parent_info.id"));
        parentInfoEntity.setUserId(resultSet.getInt("user_id"));
        parentInfoEntity.setFirstName(resultSet.getString("parent_info.first_name"));
        parentInfoEntity.setLastName(resultSet.getString("parent_info.last_name"));

        return parentInfoEntity;
    }
}
