package com.mycompany.chiroSupport.sample;

import java.security.MessageDigest;

/**
 * Created by Salaka on 5/16/2017.
 */
public class MD5hash {
    public static byte[] createChecksum(String password) throws Exception {
        byte[] bytesOfMessage = password.getBytes("UTF-8");

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        return thedigest;
    }

    // see this How-to for a faster way to convert
    // a byte array to a HEX string
    public static String getMD5Checksum(String password) throws Exception {
        byte[] b = createChecksum(password);
        String result = "";

        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
}
