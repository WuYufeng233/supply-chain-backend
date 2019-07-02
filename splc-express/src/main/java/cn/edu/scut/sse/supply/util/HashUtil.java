package cn.edu.scut.sse.supply.util;

import org.fisco.bcos.web3j.crypto.Hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Yukino Yukinoshita
 */

public class HashUtil {

    /**
     * Generate user token
     *
     * @param obj user object
     * @return MD5 hash user token
     */
    public static String findToken(Object obj) {
        String token = obj.toString();
        SecureRandom random = new SecureRandom();
        int hashCode = obj.hashCode() == Integer.MIN_VALUE ? random.nextInt(Integer.MAX_VALUE) : obj.hashCode();
        int randomInt = random.nextInt(Math.abs(hashCode));
        BigInteger i1 = BigInteger.valueOf(randomInt);
        BigInteger i2 = BigInteger.valueOf(obj.hashCode());
        BigInteger i3 = BigInteger.valueOf(System.currentTimeMillis());
        BigInteger resultInt = i1.multiply(i2).multiply(i3);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            token = byteArrayToHex(messageDigest.digest(resultInt.toString().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * Get keccak256 hash
     *
     * @param src source content
     * @return keccak256 hash
     */
    public static String keccak256(byte[] src) {
        return byteArrayToHex(Hash.sha3(src));
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
