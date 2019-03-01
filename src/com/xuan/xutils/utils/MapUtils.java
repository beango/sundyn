package com.xuan.xutils.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class MapUtils {
    private Map map;

    public MapUtils(Map map){
        this.map = map;
    }

    /**
     * <p>
     * 根据Key返回一个Double型
     * </p>
     * @param key
     * @return Double
     */
    public Double getDouble(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Double){
                return (Double)map.get(key);
            }else{
                return 0.0;
            }
        }else{
            return 0.00;
        }
    }

    /**
     * <p>
     * 根据Key返回一个String
     * </p>
     * @param key
     * @return String
     */
    public String getString(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof String){
                return (String)map.get(key);
            }else{
                return null;
            }
        }else{
            return "";
        }
    }

    /**
     * <p>
     * 根据Key返回一个Date
     * </p>
     * @param key
     * @return Date
     */
    public Date getDate(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Date){
                return (Date)map.get(key);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * <p>
     * 根据Key返回一个Integer
     * </p>
     * @param key
     * @return Integer
     */
    public Integer getInteger(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Integer){
                return (Integer)map.get(key);
            }else{
                return null;
            }
        }else{
            return 0;
        }
    }

    /**
     * <p>
     * 根据一个Key返回一个Map<String,String>
     * </p>
     * @param key
     * @return Map<String,String>
     */
    @SuppressWarnings("unchecked")
    public Map<String,String> getMap(String key){
        if(map.get(key)!=null){
            if(map.get(key) instanceof Map){
                return (Map<String,String>)map.get(key);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * <p>
     * 根据key返回BigDecimal
     * 如果为null,则返回 new BigDecimal(0)
     * </p>
     * @param key
     * @return BigDecimal
     */
    public BigDecimal getBigDecimal(String key) {
        if (map.containsKey(key)) {
            if (map.get(key) instanceof BigDecimal) {
                return (BigDecimal) map.get(key);
            } else {
                return new BigDecimal(0);
            }
        } else {
            return new BigDecimal(0);
        }
    }
}
