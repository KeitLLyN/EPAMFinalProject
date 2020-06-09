package hash;

import org.junit.Assert;
import org.junit.Test;

public class HashPasswordTest {

    @Test
    public void shouldReturnMD5Hash_WhenMD5(){
        String expected = "25dba6ec20ef7cf06340f7846c79a27f";
        Assert.assertEquals(expected,HashPassword.md5("20583007"));
    }

}
