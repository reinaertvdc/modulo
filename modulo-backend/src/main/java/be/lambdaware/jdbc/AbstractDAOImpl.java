package be.lambdaware.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author hendrik
 */
public abstract class AbstractDAOImpl {

    protected DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;

    /**
     * Sets the datasource for this DAO. Gets injected by Spring.
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
