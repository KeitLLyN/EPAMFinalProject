package utils.mysql;

import utils.dao.JDBCDao;
import utils.dao.interfaces.JdbcConstants;
import entity.User;
import hash.HashPassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao extends JDBCDao<User> implements JdbcConstants {
    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    public UserDao(){}
    public UserDao(Connection connection){
        super(connection);
    }

    @Override
    public List<User> findBy(String ... strings){
        LOG.info("Getting data from findBy method");
        List<User> users = new ArrayList<>();
        String sql = getSelectQuery() + String.format(" WHERE email = '%s'",strings[0]);
        users = prepareStatementFindByParam(sql,users);
        if (users.size() > 0)
            return users.get(0).getPassword().equals(HashPassword.md5(strings[1])) ? users:null;
        return null;
    }

    @Override
    protected String getSelectQuery() {
        return "select * from users";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into users (email, password, name, role) values (?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE users SET email = ?, password = ?, name = ?, " +
                "role = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from users where id = ?";
    }

    @Override
    protected List<User> parseResultSet(ResultSet result) {
        LOG.info("Trying to parse result set");
        List<User> users = new ArrayList<>();
        try {
            while (result.next()){
                User user = new User();
                user.setId(result.getInt("id"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setName(result.getString("name"));
                user.setRole(result.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Couldn't parse data");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        return users;
    }

    @Override
    protected void prepareStatementInsert(PreparedStatement statement, User user) {
        try {
            LOG.info("Trying to set prepare statement for INSERT method");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getRole());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for INSERT");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void prepareStatementUpdate(PreparedStatement statement, User user) {
        try {
            LOG.info("Trying to set prepare statement for UPDATE method");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getRole());
            statement.setInt(5, user.getId());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for UPDATE");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
