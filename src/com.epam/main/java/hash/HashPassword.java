package hash;

import org.apache.commons.codec.digest.DigestUtils;

public class HashPassword {
    public static String md5(String password){
        return DigestUtils.md5Hex(password);
    }
}
