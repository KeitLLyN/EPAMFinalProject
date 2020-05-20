package utils.mysql;

import entity.Train;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.dao.JDBCDao;
import utils.dao.interfaces.DaoFactory;

import utils.dao.interfaces.GenericDao;
import utils.dao.interfaces.JdbcConstants;
import entity.Wagon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Factory implements DaoFactory, JdbcConstants {
    private static final Logger LOG = LogManager.getLogger(Factory.class);
    private Map<Class<?>, GenericDao> daoMap;

    public Factory(){
        try {
            Class.forName(classDriver);
        }catch (ClassNotFoundException e){
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        daoMap = new HashMap<>();
        daoMap.put(Train.class, new TrainDao());
        daoMap.put(Wagon.class, new WagonDAO());
        daoMap.put(User.class, new UserDao());
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(jdbcURL,dbUser,dbPassword);
        }catch (SQLException e){
            LOG.error("Couldn't connect to database");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        return connection;
    }

    @Override
    public void closeConnection(Connection connection){
        try {
            if (connection != null) connection.close();
        }catch (SQLException e){
            LOG.error("Couldn't close connection database");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public GenericDao getDao(Class<?> dtoClass){
        return daoMap.get(dtoClass);
    }

}
