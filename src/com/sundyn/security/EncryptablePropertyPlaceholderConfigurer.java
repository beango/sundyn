package com.sundyn.security;

import java.util.Properties;

import com.sundyn.utils.MyWebConstant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/*import cn.com.dbappsecurity.common.utils.DesEncrypt;
import cn.com.dbappsecurity.common.utils.MyWebConstant;*/

public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final String key = MyWebConstant.JDBC_DESC_KEY;

    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        try {
//                DesEncrypt des = new DesEncrypt();
            System.out.println("111111111111111111111111111");
            String username = props.getProperty(MyWebConstant.JDBC_DATASOURCE_USERNAME_KEY);
            if (username != null) {
                //props.setProperty(MyWebConstant.JDBC_DATASOURCE_USERNAME_KEY, DesEncrypt.Decrypt(username, DesEncrypt.hex2byte(key)));
            }

            String password = props.getProperty(MyWebConstant.JDBC_DATASOURCE_PASSWORD_KEY);
            if (password != null) {
                //props.setProperty(MyWebConstant.JDBC_DATASOURCE_PASSWORD_KEY, DesEncrypt.Decrypt(password, DesEncrypt.hex2byte(key)));
            }

            String url = props.getProperty(MyWebConstant.JDBC_DATASOURCE_URL_KEY);
            if (url != null) {
                //props.setProperty(MyWebConstant.JDBC_DATASOURCE_URL_KEY, DesEncrypt.Decrypt(url, DesEncrypt.hex2byte(key)));
            }

            String driverClassName = props.getProperty(MyWebConstant.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY);
            if(driverClassName != null){
                //props.setProperty(MyWebConstant.JDBC_DATASOURCE_DRIVERCLASSNAME_KEY, DesEncrypt.Decrypt(driverClassName, DesEncrypt.hex2byte(key)));
            }
            String dbtype = props.getProperty(MyWebConstant.JDBC_DATASOURCE_TYPE_KEY);
            if(dbtype != null){
                //props.setProperty(MyWebConstant.JDBC_DATASOURCE_TYPE_KEY, DesEncrypt.Decrypt(dbtype, DesEncrypt.hex2byte(key)));
            }
            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
        }
    }
}

