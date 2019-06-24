package cn.edu.scut.sse.supply.util;

import cn.edu.scut.sse.supply.contracts.HashCalculator;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static cn.edu.scut.sse.supply.util.ContractUtil.HASH_CALCULATOR_ADDRESS;

/**
 * @author Yukino Yukinoshita
 */

public class HashUtil {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
    private static String address = HASH_CALCULATOR_ADDRESS;

    /**
     * Generate user token
     *
     * @param obj user object
     * @return MD5 hash user token
     */
    public static String findToken(Object obj) {
        String token = obj.toString();
        SecureRandom random = new SecureRandom();
        int randomInt = random.nextInt(Math.abs(obj.hashCode()));
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
        Web3j web3j = Web3jUtil.getWeb3j();
        HashCalculator contract = HashCalculator.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        try {
            return byteArrayToHex(contract.cal(src).send());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
