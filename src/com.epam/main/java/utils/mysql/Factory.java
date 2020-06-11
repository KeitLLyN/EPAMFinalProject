package utils.mysql;

import entity.Train;
import entity.User;
import entity.Wagon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.dao.interfaces.JdbcConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Factory implements DaoFactory, JdbcConstants {
    private static final Logger LOG = LogManager.getLogger(Factory.class);
    /**Поле соотношения классов Identified к Dao классам*/
    private final Map<Class<?>, GenericDao> DAO_MAP;

    public Factory(){
        LOG.info("Creating FACTORY class");
        try {
            Class.forName(classDriver);
        }catch (ClassNotFoundException e){
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        DAO_MAP = new HashMap<>();
        DAO_MAP.put(Train.class, new TrainDao());
        DAO_MAP.put(Wagon.class, new WagonDAO());
        DAO_MAP.put(User.class, new UserDao());
    }

    /**
     * Получение Подключения к базе данных
     * @return connection
     * */
    @Override
    public Connection getConnection() {
        LOG.info("Getting connection");
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(jdbcURL,dbUser,dbPassword);
        }catch (SQLException e){
            LOG.error("Couldn't connect to database");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        return connection;
    }
    /**
     * Закрывает существующиее подключение к базе данных*/
    @Override
    public void closeConnection(Connection connection){
        LOG.info("Closing connection");
        try {
            if (connection != null) connection.close();
        }catch (SQLException e){
            LOG.error("Couldn't close connection database");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
    /**
     * Получение класса с доступом к базе данных
     * @param dtoClass - класс наследник от Identified
     * @return DTO class
     * */
    @Override
    public GenericDao getDao(Class<?> dtoClass){
        LOG.info("Returning Dao class");
        return DAO_MAP.get(dtoClass);
    }

}
