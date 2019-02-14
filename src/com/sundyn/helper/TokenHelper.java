package com.sundyn.helper;

import com.sundyn.util.Base64;
import com.xuan.xutils.utils.StringUtils;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

public class TokenHelper {
    private String accessKey;

    private String secretKey;

    private static final String TOKEN_VERSION = "v2";

    public static final String X_IBEE_AUTH_TOKEN = "X-IbeeAuth-Token";

    private final List<String> allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD");

    public TokenHelper() {
    }

    public TokenHelper(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * generate the token according the request or response contents
     *
     * @param urlPath    the url of request
     * @param method     request method, must be one of 'GET', 'POST', 'DELETE', 'HEAD',
     *                   'PUT'
     * @param queryParam the query string of request
     * @param body       the post body for request, or response body
     * @param expireTime the token expired time
     * @return the token
     */
    public String generateToken(String urlPath, String method, String queryParam, String body, long expireTime) {
        if (accessKey == null || accessKey.isEmpty() || secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("Invalid AK or SK");
        }
        if (urlPath == null || urlPath.isEmpty()) {
            throw new IllegalArgumentException("Empty url path");
        }
        if (!allowedMethods.contains(method)) {
            throw new IllegalArgumentException("invalid request method");
        }
        String token;
        try {
            // |v2-{AK}-{ExpireTime}|{SK}|
            StringBuffer sbSign = new StringBuffer(String.format("|%s-%s-%d|%s|", TOKEN_VERSION, accessKey, expireTime, secretKey));
            // {UrlPath}|
            sbSign.append(decodeUtf8(urlPath)).append("|");

            // {Method}|
            sbSign.append(method).append("|");

            // {QueryParam}|
            if (!StringUtils.isBlank(queryParam)) {
                List<String> qsArray = new ArrayList<String>();
                for (String kv : queryParam.split("&")) {
                    String[] t = kv.split("=");
                    if (t[0].equals("token"))
                        continue;
                    if (t.length > 1) {
                        qsArray.add(String.format("%s=%s", decodeUtf8(t[0]), decodeUtf8(t[1])));
                    } else {
                        qsArray.add(String.format("%s=", decodeUtf8(t[0])));
                    }
                }
                Collections.sort(qsArray);
                boolean first = true;
                for (String s : qsArray) {
                    if (first) {
                        first = false;
                    } else {
                        sbSign.append("&");
                    }
                    sbSign.append(s);
                }
            }
            sbSign.append("|");

            // {body}|
            if (!StringUtils.isBlank(body)) {
                sbSign.append(body);
            }
            sbSign.append("|");

            sbSign = new StringBuffer(String.format("|%s|", secretKey));
            if (!StringUtils.isBlank(body)) {
                sbSign.append(body);
            }
            sbSign.append("|");
            System.out.println("token数据：" + sbSign.toString());
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(sbSign.toString().getBytes("UTF-8"));

            // v2-{AK}-{ExpireTime}-{Signature}
            token = String.format("%s-%s", TOKEN_VERSION, new String(Hex.encodeHex(digest.digest())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Bad encoded url path or query string");
        }
        return token;
    }

    public static String MD5(String sbSign){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(sbSign.toString().getBytes("UTF-8"));

            return  new String(Hex.encodeHex(digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decodeUtf8(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return url;
        }
    }

    /**
     * verify the token
     *
     * @param token      the token for verification
     * @param urlPath    the url of request
     * @param method     request method, must be one of 'GET', 'POST', 'DELETE', 'HEAD',
     *                   'PUT'
     * @param queryParam the query string of request
     * @param body       the post body for request, or response body
     */
    public boolean verifyToken(String token, String urlPath, String method, String queryParam, String body) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            String[] tokenParts = token.split("-");
            /*if (tokenParts.length != 4) {
                System.out.println("invalid token format");
                return false;
            }*/
            if (!TOKEN_VERSION.equals(tokenParts[0])) {
                System.out.println("invalid token protocol version");
                return false;
            }
            long expireTime = 0;
            /*long expireTime = Long.parseLong(tokenParts[2]);
            if (expireTime < System.currentTimeMillis() / 1000) {
                System.out.println("expired token");
                return false;
            }*/
            String tokenVerify = generateToken(urlPath, method, queryParam, body, expireTime);
            if (token.equals(tokenVerify)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public static void main(String[] args){
        String s1 = new TokenHelper("AK","SK").generateToken("http://localhost:8080/inte_login","GET", "s=1&k=1", "s=1&k=1", (new Date().getTime()/1000+10));
        System.out.println(s1);
        boolean b = new TokenHelper("AK","SK").verifyToken(s1, "http://localhost:8080/inte_login","GET", "k=1&s=1", "k=1&s=1");
        System.out.println(b);

        try {
            // Convert from Unicode to UTF-8
            String string = "abc\u5639\u563b";
            byte[] utf8 = string.getBytes("UTF-8");
            // Convert from UTF-8 to Unicode
            string = new String(utf8, "iso-8859-1");
            System.out.println(string);
        } catch (UnsupportedEncodingException e) {
        }
    }
}
