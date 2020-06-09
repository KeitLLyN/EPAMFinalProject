package utils.mysql;

import entity.Train;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;

import java.sql.Connection;
import java.util.List;

public class TrainDaoTest {
    GenericDao<Train> trainDao;
    DaoFactory factory;
    Connection connection;

    @Before
    public void setUp(){
        factory = new Factory();
        connection = factory.getConnection();
        trainDao = new TrainDao(connection);
    }


    @Test
    public void shouldReturnListOfTrainsByDate_WhenFindBy(){
        List<Train> trains = trainDao.findBy(new String[]{"Belarus","Russia","2020-05-10"});
        Assert.assertNotNull(trains);
    }

    @After
    public void clear(){
        factory.closeConnection(connection);
    }
}
