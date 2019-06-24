package cn.edu.scut.sse.supply.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * 签名与验签工具
 * @author Yukino Yukinoshita
 */

public class SignVerifyUtil {

    public static boolean verify(String publicKeyStr, String hash, String signature) throws Exception {
        if (publicKeyStr == null || "".equals(publicKeyStr)) {
            return false;
        }
        if (hash == null || "".equals(hash)) {
            return false;
        }
        if (signature == null || "".equals(signature)) {
            return false;
        }
        PublicKey publicKey = RSAUtil.convertPublicKey(publicKeyStr);
        return RSAUtil.verify(Base64.getDecoder().decode(signature), hash.getBytes(), publicKey);
    }

    public static String sign(String privateKey, String hash) {
        PrivateKey privKey = RSAUtil.convertPrivateKey(privateKey);
        return Base64.getEncoder().encodeToString(RSAUtil.sign(hash.getBytes(), privKey));
    }

}
