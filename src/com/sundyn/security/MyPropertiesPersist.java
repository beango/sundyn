package com.sundyn.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.xuan.xutils.utils.DESUtils;
import org.springframework.util.DefaultPropertiesPersister;

public class MyPropertiesPersist extends DefaultPropertiesPersister {
    public static String deskey="gzcgs_zx_2019";
    public void load(Properties props, InputStream is) throws IOException {
        Properties properties = new Properties();
        properties.load(is);
        try {
            Object url = properties.get("jdbc.url");
            System.out.println("原始url:" + url);
            if ( (url != null) ){
                String org = DESUtils.decrypt(url.toString(), deskey);
                System.out.println("解密url:" + org);
                properties.setProperty("jdbc.url" , org);
            }
            Object username = properties.get("jdbc.username");
            System.out.println("原始username:" + username);
            if ( (username != null) ){
                String org = DESUtils.decrypt(username.toString(), deskey);
                System.out.println("解密username:" + username);
                properties.setProperty("jdbc.username" , org);
            }
            Object pwd = properties.get("jdbc.password");
            System.out.println("原始密码:" + pwd);
            if ( (pwd != null) ){

                String org = DESUtils.decrypt(pwd.toString(), deskey);
                System.out.println("解密密码:" + org);
                properties.setProperty("jdbc.password" , org);
            }
        }
        catch (Exception e){
            System.out.println("解密出错");
        }

        OutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            properties.store(outputStream, "");
            is = outStream2InputStream(outputStream);
            super.load(props, is);
        }catch(IOException e) {
            throw e;
        }finally {
            outputStream.close();
        }
    }


    private InputStream outStream2InputStream(OutputStream out){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos = (ByteArrayOutputStream) out ;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(bos.toByteArray());
        return swapStream;
    }

    public static void main(String[] args){
        System.out.println("url" + DESUtils.encrypt("jdbc:sqlserver://localhost:1433;DatabaseName=CGS", deskey));
        System.out.println("账号" + DESUtils.encrypt("sa", deskey));
        System.out.println("密码" + DESUtils.encrypt("sa123456.", deskey));
    }

}