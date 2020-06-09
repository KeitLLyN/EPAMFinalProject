package entity;

import com.google.protobuf.RpcUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WagonTest {
    Wagon wagon;

    @Before
    public void setUp(){
        wagon = new Wagon();
        wagon.setNumberOfSeats(5);
    }

    @Test
    public void shouldNotChangeValueOfSeats_WhenDecreaseSeatsByValue(){
        int expected = 5;
        wagon.decreaseSeatsByValue(6);
        Assert.assertEquals(expected, wagon.getNumberOfSeats());
    }

    @Test
    public void shouldDecreaseValueOfSeats_WhenDecreaseSeatsByValue(){
        int expected = 2;
        wagon.decreaseSeatsByValue(3);
        Assert.assertEquals(expected,wagon.getNumberOfSeats());
    }
}
