package com.xuan.xutils.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

public class DESUtils2 {
    // 算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM_ECB = "DES";//ECB/ZeroBytePadding
    private static String KEY_ALGORITHM = "DES";
      //主要看填充方式 PKCS5Padding  PKCS7Padding  还有其他的填充方式没用过
      //    public static final String CIPHER_ALGORITHM_CBC = "DES/CBC/ZerosPadding";

    public static void main(String[] args) throws Exception {
        /*
         * 使用 ECB mode
         * 密钥生成器 生成密钥
         * ECB mode cannot use IV
         */
        byte[] key = "DAF3100DCD39CAEB5DD35E9651712A86".getBytes();
        byte[] encrypt = encrypt("test3".getBytes(),key);
        String encodeBase64String = Base64.encodeBase64String(encrypt);
        System.out.println(encodeBase64String);
        System.out.println(new String(encrypt));

        System.out.println(new String(decrypt(encrypt, key)));


        byte[]  aa = ("2F6EYHr2Iww=").getBytes("utf-8");
        System.out.println(new String(decrypt(aa, key)));
    }

    static byte[] getIV() {
        String iv = "asdfivh7"; //IV length: must be 8 bytes long
        return iv.getBytes();
    }

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    private static byte[] generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(56); //des 必须是56, 此初始方法不必须调用
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 还原密钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec des = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(des);
        return secretKey;
    }

    /**
     * 加密
     * @param data 原文
     * @param key
     * @return 密文
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        byte[] doFinal = cipher.doFinal(data);
        return cipher.doFinal(data);
    }
    /**
     * 解密
     * @param data 密文
     * @param key
     * @return 明文、原文
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}

