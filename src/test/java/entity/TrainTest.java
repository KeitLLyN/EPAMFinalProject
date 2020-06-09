package entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrainTest {
    Train train;
    List<Wagon> wagons = new ArrayList<>();

    @Before
    public void setUp(){
        train = new Train();
        wagons.add(new Wagon("P1",33,33,2));
        wagons.add(new Wagon("C1",22,22,1));
        wagons.add(new Wagon("P1",22,22,1));
        train.setWagons(wagons);
    }

    @Test
    public void shouldReturnSizeOfHashSet_WhenMakeMapsOfData(){
        int expected = 2;
        Assert.assertEquals(expected,train.getServiceTypes().size());
    }

    @Test
    public void shouldReturnSumOfSeatsInP1Service_WhenMakeMapsOfData(){
        int expected = 22+33;
        int actual = train.getServiceToSeats().get("P1");
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void shouldReturnMinPriceForP1Service_WhenMakeMapsOfData(){
        int expected = 22;
        int actual = train.getServiceToPrice().get("P1");
        Assert.assertEquals(expected,actual);
    }
}
