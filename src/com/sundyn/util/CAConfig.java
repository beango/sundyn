package com.sundyn.util;

/**
 * Created With IntelliJ IDEA.
 *
 * @author : lee
 * @group : sic-ca
 * @Date : 2014/12/30
 * @Comments : 配置接口
 * @Version : 1.0.0
 */
public interface CAConfig {

    /**
     * C
     */
    String CA_C = "CN";
    /**
     * ST
     */
    String CA_ST = "BJ";
    /**
     * L
     */
    String CA_L = "BJ";
    /**
     */
    String CA_O = "SICCA";

    /**
     * CA_ROOT_ISSUER
     */
    String CA_ROOT_ISSUER="C=CN,ST=GZ,L=GZ,O=ZX,OU=SC,CN=ZX";
    /**
     * CA_DEFAULT_SUBJECT
     */
    String CA_DEFAULT_SUBJECT="C=CN,ST=GZ,L=GZ,O=ZX,OU=SC,CN=";

    String CA_SHA="SHA512withRSA";//"SHA256WithRSAEncryption";

}
