package com.sundyn.util;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class BaseCert {
    /**
     * BouncyCastleProvider
     */
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    /**
     *
     */
    protected static KeyPairGenerator kpg = null;
    protected static KeyPair kp = null;
    private static PublicKey pubKey;
    private static PrivateKey priKey;
    /**
     *
     */
    public BaseCert() throws InvalidKeySpecException {
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
        }
    }

    /**
     * 生成 X509 证书
     * @param user
     * @return
     */
    public X509Certificate generateCert(String user) {
        X509Certificate cert = null;
        try {
            //KeyPair keyPair = this.kpg.generateKeyPair();
            // 公钥
            //PublicKey pubKey = kp.getPublic();
            // 私钥
            //PrivateKey priKey = kp.getPrivate();
            X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
            // 设置序列号
            certGen.setSerialNumber(new BigInteger(128, new Random()));
            // 设置颁发者
            certGen.setIssuerDN(new X500Principal(CAConfig.CA_ROOT_ISSUER));
            // 设置有效期
            certGen.setNotBefore(DateHelper.getInstance().getUtcNow());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE,365*10);
            certGen.setNotAfter(cal.getTime());
            // 设置使用者
            certGen.setSubjectDN(new X500Principal(CAConfig.CA_DEFAULT_SUBJECT + user));
            // 公钥
            certGen.setPublicKey(pubKey);
            // 签名算法
            certGen.setSignatureAlgorithm(CAConfig.CA_SHA);
            cert = certGen.generateX509Certificate(priKey, "BC");
        } catch (Exception e) {
        }
        return cert;
    }

    public static void main(String[] arg){
        try {
            creatRootCA();//创建私钥
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        genCA();


        try {
            String PLAIN_TEXT = "PLAIN_TEXTPLAIN_TEXTPLAIN_TEXT";
            InputStream i2 = new FileInputStream("d:/zxroot.cer");
            InputStream i1 = new FileInputStream("d:/CCB8A8DB14F8.cer");

            X509Certificate x509Certificate = (X509Certificate) getCertificate(i1);
            byte[] encodedText = encode(PLAIN_TEXT.getBytes(), priKey);
            byte[] signature = sign(x509Certificate, priKey, encodedText);

            // 导出为 cer 证书
            try {
                FileOutputStream fos = new FileOutputStream("CCB8A8DB14F8-1.cer");
                fos.write(signature);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            x509Certificate.verify(((X509Certificate) getCertificate(i2)).getPublicKey());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] encode(byte[] plainText, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] sign(X509Certificate certificate, PrivateKey privateKey, byte[] plainText) {
        /** 如果要从密钥库获取签名算法的名称，只能将其强制转换成X509标准，JDK 6只支持X.509类型的证书 */
        try {
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            signature.initSign(privateKey);
            signature.update(plainText);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static void readCA() throws IOException, CertificateException {
        String filepath = "d:/ljtTest1.cer";
        CertificateFactory certificate_factory = CertificateFactory.getInstance("X.509");
        FileInputStream file_inputstream = new FileInputStream(filepath);
        X509Certificate x509certificate = (X509Certificate) certificate_factory.generateCertificate(file_inputstream);

        String Field = x509certificate.getType();
        Date nobefore  = x509certificate.getNotAfter();
        System.out.println("nobefore:"+nobefore + "," + x509certificate.getSubjectDN());
    }

    public static void genCA(){

        KeyStore ks= null;
        try {
            FileInputStream in=new FileInputStream("d:/rootCA1.pfx");
            ks = KeyStore.getInstance("JKS");
            ks.load(in,"1qaz2wsx".toCharArray());
            java.security.cert.Certificate c1=ks.getCertificate("ljttest");
            System.out.println(c1.toString());
            String filePath = "d:/ljtTest2.cer";
            File f = new File(filePath);
            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(c1.getEncoded());
            fos.flush();
            fos.close();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rootCA() throws NoSuchAlgorithmException, NoSuchProviderException, CertificateEncodingException, IOException {
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        KeyPair  kp = kpg.generateKeyPair();

        X509Certificate x509ca = generateV1SelfSignedCertificate(kp,"ljtTest");
        byte[] caByte =  x509ca.getEncoded();


        String filePath = "d:/ljtTest1.cer";
        File f = new File(filePath);
        if (!f.exists()) {
            f.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(caByte);
        fos.flush();
        fos.close();
    }

    //Generate version 3 self signed X509Certificate
    private static X509Certificate generateV1SelfSignedCertificate(KeyPair kp, String subject) {
        try {
            X500Name subjectDN = new X500Name("CN=" + subject);
            BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
            Date startDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            Date endDate = new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000);

            SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(kp.getPublic().getEncoded());

            X509v3CertificateBuilder builder = new X509v3CertificateBuilder(subjectDN, serialNumber, startDate,
                    endDate, subjectDN, subPubKeyInfo);
            X509CertificateHolder holder = builder.build(createSigner(kp.getPrivate()));

            return new JcaX509CertificateConverter().getCertificate(holder);
        } catch (Exception e) {
            throw new RuntimeException("Error creating X509v3Certificate.", e);
        }
    }

    private static ContentSigner createSigner(PrivateKey privKey) throws OperatorCreationException {
        return   new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(privKey);
    }

    private static void readKeyStore() {
        String pass="1qaz2wsx";
        String filepath = "E:/test.pfx";
        System.out.println("begin ca.");
        try {

            FileInputStream file_inputstream = new FileInputStream(filepath);
            KeyStore store = KeyStore.getInstance("PKCS12", "BC");
            store.load(file_inputstream, pass.toCharArray());

            PrivateKey key = (PrivateKey)store.getKey("ljttest", pass.toCharArray());
            System.out.println("PrivateKey:"+key);
            java.security.cert.Certificate ca =  store.getCertificate("ljttest");

            if (store.getCertificate("ljttest") == null)
            {
                System.out.println("Failed to find UTF cert.");
            }
            System.out.println("ca."+ca);

        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException | NoSuchProviderException | UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void creatRootCA() throws NoSuchAlgorithmException, NoSuchProviderException, CertificateEncodingException {
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        KeyPair  kp = kpg.generateKeyPair();
        PublicKey pubk = kp.getPublic();
        PrivateKey prik = kp.getPrivate();

        X509Certificate x509ca = generateV3SelfSignedCertificate(pubk,prik, CAConfig.CA_DEFAULT_SUBJECT+"test");
        byte[] caByte =  x509ca.getEncoded();

        String pfxPath = "d:/rootCA.pfx";
        KeyStore keyStore;
        try {

            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, null);

            keyStore.setKeyEntry("ljttest", kp.getPrivate(), "1qaz2wsx".toCharArray(), new X509Certificate[]{x509ca});

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

    //Generate version 3 self signed X509Certificate
    private static X509Certificate generateV3SelfSignedCertificate(PublicKey pubk, PrivateKey prik, String subject) {
        try {
            X500Name subjectDN = new X500Name(subject);
            BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());

            Calendar c = Calendar.getInstance();
            Date startDate = c.getTime();
            c.add(Calendar.YEAR, 1);
            Date endDate = c.getTime();

            SubjectPublicKeyInfo subPubKeyInfo = SubjectPublicKeyInfo.getInstance(pubk.getEncoded());

            X509v3CertificateBuilder builder = new X509v3CertificateBuilder(subjectDN, serialNumber, startDate, endDate, subjectDN, subPubKeyInfo);
            X509CertificateHolder holder = builder.build(createSigner(prik));

            return new JcaX509CertificateConverter().getCertificate(holder);
        } catch (Exception e) {
            throw new RuntimeException("Error creating X509v3Certificate.", e);
        }
    }

    private static Certificate getCertificate(InputStream inputStream) throws Exception {
        return CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
    }

}
