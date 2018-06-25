package com.sundyn.cer;

import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * issuer	证书颁发者
 * subject	证书使用者
 *
 * DN：Distinguish Name
 * 格式：CN=姓名,OU=组织单位名称,O=组织名称,L=城市或区域名称,ST=省/市/自治区名称,C=国家双字母
 *
 */
@SuppressWarnings("deprecation")
public class CertifacateGenerateTest {

    private static final String KEY_PAIR_ALG = "RSA";
    private static final String SIG_ALG = "SHA1withRSA";
    private static final String DN_ZHANGSAN = "CN=zhangsan,OU=development,O=Huawei,L=ShenZhen,ST=GuangDong,C=CN";
    private static final String DN_CA = "CN=ZX,OU=ZX,O=ZX,L=GuangZou,ST=GuangDong,C=CN";
    private static Map<String, String> algorithmMap = new HashMap<>();

    static {
        /**
         * 算法名称与算法标识符映射
         */
        algorithmMap.put("1.2.840.113549.1.1.5", SIG_ALG);
        algorithmMap.put("1.2.840.113549.1.1.1", KEY_PAIR_ALG);
    }

    @Before
    public void before() {
        //注冊BC Provider，由于有些关于证书的操作使用到了BouncyCastle这个第三方库就顺便注冊上了，事实上不注冊也行
        Provider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
    }

    @Test
    public void testsa(){
        try {
            CertifacateGenerateTest cer = new CertifacateGenerateTest();
            cer.GenRootKeyPair();//生成密钥
            cer.GenRootCert();//生成根证书
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成根证书公钥与私钥对
     */
    @Test
    public void GenRootKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_ALG,"BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        writeObject("D:/certtest/zxroot.public", keyPair.getPublic());
        writeObject("D:/certtest/zxroot.private", keyPair.getPrivate());
    }

    /**
     * 生成用户证书公钥与私钥对
     * @throws Exception
     */
    @Test
    public void ZhangsanKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_ALG,"BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        writeObject("D:/certtest/zhangsan.public", keyPair.getPublic());
        writeObject("D:/certtest/zhangsan.private", keyPair.getPrivate());
    }

    /**
     * 生成根证书(被BC废弃，但能够使用)
     */
    @Test
    public void GenRootCert() throws Exception {
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        //设置证书颁发者
        certGen.setIssuerDN(new X500Principal(DN_CA));
        //设置证书有效期
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE,365*10);
        certGen.setNotAfter(cal.getTime());
        certGen.setNotBefore(new Date());
        //设置证书公钥
        certGen.setPublicKey(getRootPublicKey());
        //设置证书序列号
        certGen.setSerialNumber(BigInteger.TEN);
        //设置签名算法
        certGen.setSignatureAlgorithm(SIG_ALG);
        //设置证书使用者
        certGen.setSubjectDN(new X500Principal(DN_CA));
        //使用私钥生成证书。主要是为了进行签名操作
        X509Certificate certificate = certGen.generate(getRootPrivateKey(),"BC");
        PKCS12BagAttributeCarrier bagAttr = (PKCS12BagAttributeCarrier)certificate;
        bagAttr.setBagAttribute(PKCSObjectIdentifiers.pkcs_9_at_friendlyName, new DERBMPString("Kingyea Coperation Certificate"));
        writeFile("D:/certtest/zxroot.cer", certificate.getEncoded());
        System.out.println(certificate);
    }

    /**
     * 生成根证书的第二种方式
     * @throws Exception
     */
    @Test
    public void testGenRootCertWithBuilder() throws Exception {
        final AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find(SIG_ALG);
        final AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);

        PublicKey publicKey = getRootPublicKey();
        PrivateKey privateKey = getRootPrivateKey();


        X500Name issuer = new X500Name(DN_CA);
        BigInteger serial = BigInteger.TEN;
        Date notBefore = new Date();
        Date notAfter = new Date(System.currentTimeMillis()+ 100 * 24 * 60 * 60 * 1000);
        X500Name subject = new X500Name(DN_CA);


        AlgorithmIdentifier algId = AlgorithmIdentifier.getInstance(PKCSObjectIdentifiers.rsaEncryption.toString());
        System.out.println(algId.getAlgorithm());
        AsymmetricKeyParameter publicKeyParameter = PublicKeyFactory.createKey(publicKey.getEncoded());
        SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(publicKeyParameter);
        //此种方式不行，生成证书不完整
        //SubjectPublicKeyInfo publicKeyInfo = new SubjectPublicKeyInfo(algId, publicKey.getEncoded());
        X509v3CertificateBuilder x509v3CertificateBuilder = new X509v3CertificateBuilder(issuer, serial, notBefore, notAfter, subject, publicKeyInfo);

        BcRSAContentSignerBuilder contentSignerBuilder = new BcRSAContentSignerBuilder(sigAlgId, digAlgId);
        AsymmetricKeyParameter privateKeyParameter = PrivateKeyFactory.createKey(privateKey.getEncoded());
        ContentSigner contentSigner = contentSignerBuilder.build(privateKeyParameter);

        X509CertificateHolder certificateHolder = x509v3CertificateBuilder.build(contentSigner);
        Certificate certificate = certificateHolder.toASN1Structure();
        writeFile("D:/certtest/ca.cer", certificate.getEncoded());
    }

    /**
     * 生成用户证书
     */
    @Test
    public void testGenZhangsanCert() throws Exception {
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        certGen.setIssuerDN(new X500Principal(DN_CA));
        certGen.setNotAfter(new Date(System.currentTimeMillis()+ 100 * 24 * 60 * 60 * 1000));
        certGen.setNotBefore(new Date());
        certGen.setPublicKey(getZhangsanPublicKey());
        certGen.setSerialNumber(BigInteger.TEN);
        certGen.setSignatureAlgorithm(SIG_ALG);
        certGen.setSubjectDN(new X500Principal(DN_ZHANGSAN));
        X509Certificate certificate = certGen.generate(getRootPrivateKey());

        writeFile("D:/certtest/zhangsan.cer", certificate.getEncoded());
    }

    /**
     * 验证根证书签名
     */
    @Test
    public void testVerifyRootCert() throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509","BC");
        FileInputStream inStream = new FileInputStream("D:/certtest/ca.cer");
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inStream);
        System.out.println(certificate);
        Signature signature = Signature.getInstance(certificate.getSigAlgName());
        signature.initVerify(certificate);
        signature.update(certificate.getTBSCertificate());
        boolean legal = signature.verify(certificate.getSignature());
        System.out.println(legal);
    }

    /**
     * 验证用户证书签名
     */
    @Test
    public void testVerifyZhangsanCert() throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509","BC");
        FileInputStream inStream = new FileInputStream("D:/certtest/zhangsan.cer");//CCB8A8DB14F8
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inStream);
        System.out.println(certificate);
        Signature signature = Signature.getInstance(certificate.getSigAlgName(),"BC");
        signature.initVerify(getRootPublicKey());
        signature.update(certificate.getTBSCertificate());
        boolean legal = signature.verify(certificate.getSignature());
        System.out.println(legal);
    }


    /**
     * 生成证书请求文件
     */
    @Test
    public void testGenCSR() throws Exception {
        X500Name subject = new X500Name(DN_ZHANGSAN);
        AsymmetricKeyParameter keyParameter = PrivateKeyFactory.createKey(getZhangsanPrivateKey().getEncoded());
        SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(keyParameter);
        PKCS10CertificationRequestBuilder certificationRequestBuilder = new PKCS10CertificationRequestBuilder(subject, publicKeyInfo);
        final AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find(SIG_ALG);
        final AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);
        BcRSAContentSignerBuilder contentSignerBuilder = new BcRSAContentSignerBuilder(sigAlgId, digAlgId);
        PKCS10CertificationRequest certificationRequest = certificationRequestBuilder.build(contentSignerBuilder.build(keyParameter));
        System.out.println(certificationRequest);
        writeFile("D:/certtest/zhangsan.csr", certificationRequest.getEncoded());
    }

    /**
     * 依据证书请求文件生成用户证书，事实上主要是使用根证书私钥为其签名
     */
    @Test
    public void testZhangsanCertWithCSR() throws Exception {
        byte[] encoded = readFile("D:/certtest/zhangsan.csr");
        PKCS10CertificationRequest certificationRequest = new PKCS10CertificationRequest(encoded);

        RSAKeyParameters parameter = (RSAKeyParameters) PublicKeyFactory.createKey(certificationRequest.getSubjectPublicKeyInfo());
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(parameter.getModulus(), parameter.getExponent());
        String algorithm = algorithmMap.get(certificationRequest.getSubjectPublicKeyInfo().getAlgorithm().getAlgorithm().toString());
        PublicKey publicKey = KeyFactory.getInstance(algorithm).generatePublic(keySpec);
        System.out.println(certificationRequest.getSubject());
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        certGen.setIssuerDN(new X500Principal(DN_CA));
        certGen.setNotAfter(new Date(System.currentTimeMillis()+ 100 * 24 * 60 * 60 * 1000));
        certGen.setNotBefore(new Date());

        certGen.setPublicKey(publicKey);
        certGen.setSerialNumber(BigInteger.TEN);
        certGen.setSignatureAlgorithm(algorithmMap.get(certificationRequest.getSignatureAlgorithm().getAlgorithm().toString()));
        certGen.setSubjectDN(new X500Principal(certificationRequest.getSubject().toString()));
        X509Certificate certificate = certGen.generate(getRootPrivateKey());

        writeFile("D:/certtest/zhangsan.cer", certificate.getEncoded());
    }

    @Test
    public void verify() throws Exception {
        InputStream i1 = new FileInputStream("D:/certtest/ca.cer");
        InputStream i2 = new FileInputStream("D:/certtest/zhangsan.cer");

        X509Certificate x509Certificate = (X509Certificate) getCertificate(i1);
        x509Certificate.verify(((X509Certificate) getCertificate(i2)).getPublicKey());
    }

    /**
     * 验证根证书签名
     */
    @Test
    public void verify2() throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509","BC");

        //FileInputStream inStream = new FileInputStream("D:/certtest/ca.cer");
        //X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inStream);
        //System.out.println(certificate.getPublicKey().toString());

        FileInputStream inStream2 = new FileInputStream("D:/certtest/zhangsan.cer");//D:/certtest/CCB8A8DB14F8.cer
        X509Certificate certificate2 = (X509Certificate) certificateFactory.generateCertificate(inStream2);
        System.out.println(certificate2.getPublicKey().toString());

        Signature signature2 = Signature.getInstance(certificate2.getSigAlgName(),"BC");
        signature2.initVerify(getRootPublicKey());
        signature2.update(certificate2.getTBSCertificate());
        boolean legal2 = signature2.verify(certificate2.getSignature());
        System.out.println(legal2);

        certificate2.verify(getRootPublicKey());

        legal2 = verify(certificate2,certificate2.getTBSCertificate(),certificate2.getSignature());
        System.out.println(legal2);
    }

    public static boolean verify(X509Certificate certificate, byte[] decodedText, final byte[] receivedignature) {
        try {
            Signature signature = Signature.getInstance(certificate.getSigAlgName());
            /** 注意这里用到的是证书，实际上用到的也是证书里面的公钥 */
            signature.initVerify(certificate.getPublicKey());
            signature.update(decodedText);
            return signature.verify(receivedignature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private static java.security.cert.Certificate getCertificate(InputStream inputStream) throws Exception {
        return CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
    }

    public PrivateKey getRootPrivateKey() throws Exception {
        return PrivateKey.class.cast(readKey("D:/certtest/zxroot.private"));
    }
    public PublicKey getRootPublicKey() throws Exception {
        return PublicKey.class.cast(readKey("D:/certtest/zxroot.public"));
    }

    public PrivateKey getZhangsanPrivateKey() throws Exception {
        return PrivateKey.class.cast(readKey("D:/certtest/test.private"));
    }
    public PublicKey getZhangsanPublicKey() throws Exception {
        return PublicKey.class.cast(readKey("D:/certtest/zhangsan.public"));
    }


    public byte[] readFile(String path) throws Exception {
        FileInputStream cntInput = new FileInputStream(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b = -1;
        while((b=cntInput.read())!=-1) {
            baos.write(b);
        }
        cntInput.close();
        byte[] contents = baos.toByteArray();
        baos.close();
        return contents;
    }

    public void writeFile(String path, byte[] content) throws Exception {
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(content);
        fos.close();
    }

    public void writeObject(String path, Object object) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(object);
        oos.close();
    }

    public Object readObject(String path) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public Key readKey(String path) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Key key = Key.class.cast(ois.readObject());
        ois.close();
        return key;
    }
}
