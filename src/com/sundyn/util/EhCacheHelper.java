package com.sundyn.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

public class EhCacheHelper {
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    private EhCacheHelper(){}
    private static boolean enableCache = false;
    private static CacheManager cacheManager = null;

    static{
        logger.info("java.io.tmpdir:" + ConfigHelper.getValue("enableCache"));
        enableCache =  ConfigHelper.getValue("enableCache").toLowerCase().equals("true");
        if(enableCache)
            cacheManager = CacheManager.create();
    }

    /*public static CacheManager getCacheManager(){
        return cacheManager;
    }*/

    public static void putCache(String key, Object data){
        if(!enableCache){
            return;
        }
        final Cache cache = cacheManager.getCache("helloworld1");

        //他建一个数据容器
        final Element putGreeting = new Element(key, data);

        //将数据放入到缓存实例中
        cache.put(putGreeting);
        logger.info("写入缓存:" + key);
    }

    public static Object getCache(String key){
        logger.info("java.io.tmpdir:" + System.getProperty("java.io.tmpdir")  + enableCache);
        if(!enableCache){
            return null;
        }
        final Cache cache = cacheManager.getCache("helloworld1");
        final Element getGreeting = cache.get(key);
        if (getGreeting == null){
            logger.info("读取缓存:" + key + "为空");
            return null;
        }
        else{
            logger.info("读取缓存:" + key + "有数据了");
            return getGreeting.getObjectValue();
        }

    }
}
