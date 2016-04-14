package com.globalroam.gnum.api;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by zhangjian on 2016/3/29.
 */
public class HttpAPIProperties {

    private static Properties properties;
    public static String httpType;
    public static String address;
    public static Integer port;
    public static String rootPath;

    static {
        try {
            properties = new Properties();
            properties.load(HttpAPIProperties.class.getClassLoader().getResourceAsStream("http-rest-api.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置api的基础属性
       httpType = (String) properties.get("api.http.type");
       address = (String) properties.get("api.http.address");
       port = Integer.valueOf((String) properties.get("api.http.port"));
       rootPath = (String) properties.get("api.uri.root");
    }

    //获取properties中的参数
    public static String getParam(String key) {
        return (String) properties.get(key);
    }

    //往properties中加入配置参数
    public static boolean addParam(Object key, Object value) {
        return properties.put(key, value) == null ? false : true;
    }

}
