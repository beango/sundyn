package com.sundyn.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheHelper {
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    private EhCacheHelper(){}
    private static boolean enableCache = false;
    private static CacheManager cacheManager = null;

    public enum CacheKeyEnum {
        ISNOTGENERAL("ISNOTGENERAL", "非常规业务"),
        ALLMENUS("ALLMENUS", "所有菜单数据"),
        ALLMANAGER("ALLMANAGER", "所有管理员数据"),
        ALLDEPT("ALLDEPT", "所有部门数据"),
        ALLDICTINFO("ALLDICTINFO", "所有字典数据");

        private String name;
        private String msg;
        CacheKeyEnum(String name, String msg) {
            this.name = name;
            this.msg = msg;
        }

        public static CacheKeyEnum codeOf(String name) {
            for (CacheKeyEnum ynEnum : CacheKeyEnum.values()) {
                if (ynEnum.name == name) {
                    return ynEnum;
                }
            }
            return null;
        }

        public String getMsg() {
            return msg;
        }
    }

    static{
        //logger.info("java.io.tmpdir:" + ConfigHelper.getValue("enableCache"));
        enableCache =  ConfigHelper.getValue("enableCache").toLowerCase().equals("true");
        if(enableCache)
            cacheManager = CacheManager.create();
    }

    /*public static CacheManager getCacheManager(){
        return cacheManager;
    }*/

    public static void putCache(CacheKeyEnum key, Object data){
        if(!enableCache){
            return;
        }
        final Cache cache = cacheManager.getCache("EhCacheVol1");

        //他建一个数据容器
        final Element putGreeting = new Element(key.name, data);

        //将数据放入到缓存实例中
        cache.put(putGreeting);
        logger.info("写入缓存:" + key.name);
    }

    public static Object getCache(CacheKeyEnum key){
        //logger.info("java.io.tmpdir:" + System.getProperty("java.io.tmpdir")  + enableCache);
        if(!enableCache){
            return null;
        }
        final Cache cache = cacheManager.getCache("EhCacheVol1");
        final Element getGreeting = cache.get(key.name);
        if (getGreeting == null){
            return null;
        }
        else{
            return getGreeting.getObjectValue();
        }
    }

    public static Object getCacheAndPut(CacheKeyEnum key, Object data){
        //logger.info("java.io.tmpdir:" + System.getProperty("java.io.tmpdir")  + enableCache);
        if(!enableCache){
            return null;
        }
        final Cache cache = cacheManager.getCache("EhCacheVol1");
        final Element getGreeting = cache.get(key.name);
        if (getGreeting == null){
            putCache(key, data);
            return null;
        }
        else{
            return getGreeting.getObjectValue();
        }
    }
}
