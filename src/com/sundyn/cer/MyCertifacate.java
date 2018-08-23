package com.sundyn.cer;

import com.sundyn.util.CAConfig;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MyCertifacate {
    private static final String STORE_PASS = "1qaz2wsx";
    private static final String ALIAS = "ZXEvalCertificate";
    private static final String KEYSTORE_PATH = "D:\\rootCA1.pfx";
    private static final String CERT_PATH = "D:\\c1.cer";
    private static final String PLAIN_TEXT = "MANUTD is the most greatest club in the world.";
    /** JDK6只支持X.509标准的证书 */
    private static final String CERT_TYPE = "X.509";

    protected static KeyPairGenerator kpg = null;
    protected static KeyPair kp = null;
    private static PublicKey pubKey;
    private static PrivateKey priKey;

    public void initkey(){
        try {
            // 采用 RSA 非对称算法加密
            kpg = KeyPairGenerator.getInstance("RSA");
            // 初始化为 1023 位
            kpg.initialize(1024);
            kp = this.kpg.generateKeyPair();
            if (!new File("d:/pubKey").exists()){
                FileOutputStream fs = new FileOutputStream("d:/pubKey");
                // 公钥
                pubKey = kp.getPublic();
                fs.write(pubKey.getEncoded());
                fs.close();
            }
            else {
                FileInputStream fsPublicKey = new FileInputStream("d:/pubKey");
                BufferedInputStream bfsPublicKey = new BufferedInputStream(fsPublicKey);
                byte[] bytePublicKey = new byte[bfsPublicKey.available()];
                bfsPublicKey.read(bytePublicKey);
                bfsPublicKey.close();

                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(bytePublicKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                pubKey = keyFactory.generatePublic(pubKeySpec);
            }
            if (!new File("d:/priKey").exists()){
                // 私钥
                priKey = kp.getPrivate();
                FileOutputStream fs2 = new FileOutputStream("d:/priKey");
                fs2.write(priKey.getEncoded());
                fs2.close();
            }
            else {
                FileInputStream fsPublicKey = new FileInputStream("d:/priKey");
                BufferedInputStream bfsPublicKey = new BufferedInputStream(fsPublicKey);
                byte[] bytePublicKey = new byte[bfsPublicKey.available()];
                bfsPublicKey.read(bytePublicKey);
                bfsPublicKey.close();

                PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(bytePublicKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                priKey = keyFactory.generatePrivate(priKeySpec);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
    public static void generateCert(String certpath, String mID) throws IOException {
        KeyStore keyStore = getKeyStore(STORE_PASS, KEYSTORE_PATH);
        PrivateKey privateKey = getPrivateKey(keyStore, ALIAS, STORE_PASS);
        X509Certificate certificate = getCertificateByKeystore(keyStore, ALIAS);

        /** 加密和签名 */
        byte[] encodedText = encode(mID.getBytes(), privateKey);
        byte[] signature = sign(certificate, privateKey, mID.getBytes());

        // 导出为 cer 证书
        try {
            FileOutputStream fos = new FileOutputStream(certpath);
            fos.write(certificate.getEncoded());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
        /**
         * 假设现在有这样一个场景 。A机器上的数据，需要加密导出，然后将导出文件放到B机器上导入。 在这个场景中，A相当于服务器，B相当于客户端
         */
        /** A */
        KeyStore keyStore = getKeyStore(STORE_PASS, KEYSTORE_PATH);
        PrivateKey privateKey = getPrivateKey(keyStore, ALIAS, STORE_PASS);
        X509Certificate certificate = getCertificateByKeystore(keyStore, ALIAS);

        /** 加密和签名 */
        byte[] encodedText = encode(PLAIN_TEXT.getBytes(), privateKey);
        byte[] signature = sign(certificate, privateKey, encodedText);

        // 导出为 cer 证书
        try {
            FileOutputStream fos = new FileOutputStream(CERT_PATH);
            fos.write(signature);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** 现在B收到了A的密文和签名，以及A的可信任证书 */
        X509Certificate receivedCertificate = getCertificateByCertPath(CERT_PATH, CERT_TYPE);
        PublicKey publicKey = getPublicKey(receivedCertificate);
        byte[] decodedText = decode(encodedText, publicKey);
        System.out.println("Decoded Text : " + new String(decodedText));
        System.out.println("Signature is : " + verify(receivedCertificate, decodedText, signature));
    }

    /**
     * 加载密钥库，与Properties文件的加载类似，都是使用load方法
     *
     * @throws IOException
     */
    public static KeyStore getKeyStore(String storepass, String keystorePath)
            throws IOException {
        InputStream inputStream = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(keystorePath);
            keyStore.load(inputStream, storepass.toCharArray());
            return keyStore;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        return null;
    }

    public static void creatRootCA() throws NoSuchAlgorithmException, NoSuchProviderException, CertificateException, FileNotFoundException {
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        KeyPair  kp = kpg.generateKeyPair();
        PublicKey pubk = kp.getPublic();
        PrivateKey prik = kp.getPrivate();

        //X509Certificate x509ca = generateV3SelfSignedCertificate(pubk,prik, CAConfig.CA_DEFAULT_SUBJECT+"test");
        //byte[] caByte =  x509ca.getEncoded();

        CertificateFactory factory = CertificateFactory.getInstance(CERT_TYPE);
        // 取得证书文件流
        InputStream inputStream = new FileInputStream(CERT_PATH);
        // 生成证书
        X509Certificate certificate = (X509Certificate)factory.generateCertificate(inputStream);

        String pfxPath = "d:/rootCA.pfx";
        KeyStore keyStore;
        try {

            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, null);

            keyStore.setKeyEntry("ljttest", kp.getPrivate(), "1qaz2wsx".toCharArray(), new X509Certificate[]{certificate});

            FileOutputStream fos = new FileOutputStream(new File(pfxPath));
            keyStore.store(fos, "1qaz2wsx".toCharArray());
            fos.flush();
            fos.close();

        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取私钥
     *
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias,
                                           String password) {
        try {
            return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取公钥
     *
     * @param certificate
     * @return
     */
    public static PublicKey getPublicKey(Certificate certificate) {
        return certificate.getPublicKey();
    }

    /**
     * 通过密钥库获取数字证书，不需要密码，因为获取到Keystore实例
     *
     * @param keyStore
     * @param alias
     * @return
     */
    public static X509Certificate getCertificateByKeystore(KeyStore keyStore, String alias) {
        try {
            return (X509Certificate) keyStore.getCertificate(alias);
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过证书路径生成证书，与加载密钥库差不多，都要用到流。
     *
     * @param path
     * @param certType
     * @return
     * @throws IOException
     */
    public static X509Certificate getCertificateByCertPath(String path, String certType) throws IOException {
        InputStream inputStream = null;
        try {
            // 实例化证书工厂
            CertificateFactory factory = CertificateFactory.getInstance(certType);
            // 取得证书文件流
            inputStream = new FileInputStream(path);
            // 生成证书
            Certificate certificate = factory.generateCertificate(inputStream);

            return (X509Certificate) certificate;
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        return null;
    }

    /**
     * 从证书中获取加密算法，进行签名
     *
     * @param certificate
     * @param privateKey
     * @param plainText
     * @return
     */
    public static byte[] sign(X509Certificate certificate, PrivateKey privateKey, byte[] plainText) {
        /** 如果要从密钥库获取签名算法的名称，只能将其强制转换成X509标准，JDK 6只支持X.509类型的证书 */
        try {
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            signature.initSign(privateKey);
            signature.update(plainText);
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 验签，公钥包含在证书里面
     *
     * @param certificate
     * @param decodedText
     * @param receivedignature
     * @return
     */
    public static boolean verify(X509Certificate certificate, byte[] decodedText, final byte[] receivedignature) {
        try {
            Signature signature = Signature.getInstance(certificate
                    .getSigAlgName());
            /** 注意这里用到的是证书，实际上用到的也是证书里面的公钥 */
            signature.initVerify(certificate);
            signature.update(decodedText);
            return signature.verify(receivedignature);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 加密。注意密钥是可以获取到它适用的算法的。
     *
     * @param plainText
     * @param privateKey
     * @return
     */
    public static byte[] encode(byte[] plainText, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 解密，注意密钥是可以获取它适用的算法的。
     *
     * @param encodedText
     * @param publicKey
     * @return
     */
    public static byte[] decode(byte[] encodedText, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(encodedText);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }
}
