package cn.edu.scut.sse.supply.util;

import org.fisco.bcos.web3j.crypto.ECDSASign;
import org.fisco.bcos.web3j.crypto.ECDSASignature;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Sign;

import java.math.BigInteger;

/**
 * Utility for Sign and Verify signature
 *
 * Sign and verify by using Elliptic Curve Digital Signature Algorithm (ECDSA).
 * The KeyPair should meet the Ethereum rule, that is created by using protocol
 * BIP32, BIP39, BIP44, with the format m/44'/60'/0'/0/0. Therefore the length
 * of hexadecimal private key string should be 64.
 *
 * @author Yukino Yukinoshita
 */

public class SignVerifyUtil {

    /**
     * 验签
     *
     * @param publicKeyStr 公钥
     * @param message 原文本
     * @param signature 文本签名，以{@code r@s}格式
     * @return true if verify
     */
    public static boolean verify(String publicKeyStr, String message, String signature) {
        if (publicKeyStr == null || "".equals(publicKeyStr)) {
            return false;
        }
        if (message == null || "".equals(message)) {
            return false;
        }
        if (signature == null || "".equals(signature) || !signature.contains("@")) {
            return false;
        }
        String[] rs = signature.split("@");
        if (rs.length != 2) {
            return false;
        }
        String rStr = rs[0];
        String sStr = rs[1];
        ECDSASignature ecdsaSignature = new ECDSASignature(new BigInteger(rStr, 16), new BigInteger(sStr, 16));
        for (int i = 0; i < 4; ++i) {
            BigInteger testPublicKey = Sign.recoverFromSignature(i, ecdsaSignature, message.getBytes());
            if (testPublicKey == null) {
                continue;
            }
            if (publicKeyStr.equals(testPublicKey.toString(16))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文本签名
     *
     * @param privateKey 私钥
     * @param message 待签名文本
     * @return 签名，以{@code r@s}格式返回
     */
    public static String sign(String privateKey, String message) {
        if (privateKey == null || "".equals(privateKey)) {
            return null;
        }
        if (message == null || "".equals(message)) {
            return null;
        }
        ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        ECDSASignature signature = ECDSASign.sign(message.getBytes(), keyPair.getPrivateKey());
        return signature.r.toString(16) + "@" + signature.s.toString(16);
    }

}
