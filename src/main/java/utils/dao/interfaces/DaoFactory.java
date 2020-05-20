package utils.dao.interfaces;


import java.sql.Connection;

public interface DaoFactory {
    GenericDao getDao(Class<?> dtoClass);
    Connection getConnection();
    void closeConnection(Connection connection);
}
