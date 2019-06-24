package cn.edu.scut.sse.supply.util;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Yukino Yukinoshita
 */

public class RSAUtil {

    /**
     * 生成 4096bit RSA 密钥对
     *
     * @return 返回密钥对
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(4096);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 由PublicKey转String
     *
     * @param publicKey 公钥，PublicKey对象
     * @return 公钥String
     */
    public static String convertPublicKey(PublicKey publicKey) {
        return new String(Base64.getEncoder().encode(publicKey.getEncoded()));
    }

    /**
     * 由PrivateKey转String
     *
     * @param privateKey 私钥，PrivateKey对象
     * @return 私钥String
     */
    public static String convertPrivateKey(PrivateKey privateKey) {
        return new String(Base64.getEncoder().encode(privateKey.getEncoded()));
    }

    /**
     * 由公钥String转PublicKey
     *
     * @param publicKey 公钥String
     * @return 公钥PublicKey对象
     */
    public static PublicKey convertPublicKey(String publicKey) {
        byte[] keys = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keys);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 由私钥String转PrivateKey
     *
     * @param privateKey 私钥String
     * @return 私钥PrivateKey对象
     */
    public static PrivateKey convertPrivateKey(String privateKey) {
        byte[] keys = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keys);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param content 需要加密的内容
     * @param publicKey 公钥对象
     * @return 加密后的内容
     */
    public static byte[] encrypt(byte[] content, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return content;
        }
    }

    /**
     * 私钥解密
     *
     * @param content 需要解密的内容
     * @param privateKey 私钥对象
     * @return 解密后的内容
     */
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return content;
        }
    }

    /**
     * 私钥签名
     *
     * @param content 需要签名的内容
     * @param privateKey 私钥对象
     * @return 签名后的内容
     */
    public static byte[] sign(byte[] content, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(content);
            return signature.sign();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥验证签名
     *
     * @param sign 经过签名的内容
     * @param oriContent 原内容
     * @param publicKey 公钥
     * @return true if verify
     * @throws Exception NoSuchAlgorithmException 当签名方法实例不存在时抛出异常
     */
    public static boolean verify(byte[] sign, byte[] oriContent, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(oriContent);
        return signature.verify(sign);
    }

}
