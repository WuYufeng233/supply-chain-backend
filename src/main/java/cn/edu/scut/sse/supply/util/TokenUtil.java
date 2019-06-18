package cn.edu.scut.sse.supply.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Yukino Yukinoshita
 */

public class TokenUtil {

    public static String findToken(Object obj) {
        String token = obj.toString();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            SecureRandom random = new SecureRandom();
            int randomInt = random.nextInt(Math.abs(obj.hashCode()));
            long l1 = (long) randomInt * (long) obj.hashCode() * System.currentTimeMillis();
            token = byteArrayToHex(messageDigest.digest(String.valueOf(l1).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

}
