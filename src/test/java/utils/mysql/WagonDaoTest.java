package utils.mysql;

import entity.Train;
import entity.Wagon;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;

import java.sql.Connection;
import java.util.List;

public class WagonDaoTest {
    GenericDao<Wagon> wagonDao;
    GenericDao<Train> trainDao;
    DaoFactory factory;
    Connection connection;

    @Before
    public void setUp(){
        factory = new Factory();
        connection = factory.getConnection();
        wagonDao = new WagonDAO(connection);
        trainDao = new TrainDao(connection);
    }

    @Test
    public void shouldReturnListOfWagons_WhenFindBy(){
        int id = trainDao.getAll().get(0).getId();
        List<Wagon> wagons = wagonDao.findBy("train_id",String.valueOf(id));
        Assert.assertNotNull(wagons);
        Assert.assertEquals(7,wagons.get(0).getTrainId());
    }

    @After
    public void clear(){
        factory.closeConnection(connection);
    }
}
