package com.globalroam.gnum.api.auth;

import com.globalroam.gnum.api.HttpAPIProperties;
import org.apache.commons.codec.digest.Md5Crypt;
import sun.misc.BASE64Encoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangjian on 2016/3/29.
 */
public class Authentication {

    private Map<String, String> headers;

    private static Authentication instance;

    private static String API_KEY = "api.auth.apikey";
    private static String SECRET_KEY = "api.auth.secretkey";

    private static String[] headerKeys = {"api_key", "ts", "nonce", "X-Security-Sign"};

    private Authentication() {
        initHeaders();
    }

    private void initHeaders() {
        //获取api_key的的值
        String apiKey = HttpAPIProperties.getParam(API_KEY);
        String secretKey = HttpAPIProperties.getParam(SECRET_KEY);

        if (apiKey == null || secretKey == null) {
            throw new RuntimeException("apiKey or secretKey is empty");
        }

        //获取当前的timestamp
        String ts = new SimpleDateFormat("yyyy-MM-dd HH24:mm:sss +0000").format(new Date());
        String nonce = UUID.randomUUID().toString();

        // X-Security-Sign = Base64 (MD5 (concat (api_key, secret_key, timestamp, nonce))
        String xSecuritySign = this.getxSecuritySign(apiKey, secretKey, ts, nonce);


        this.headers = new HashMap<String, String>();
        String authorization = new StringBuilder()
                .append(headerKeys[0]).append("=").append(apiKey)
                .append(",")
                .append(headerKeys[1]).append("=").append(ts)
                .append(",")
                .append(headerKeys[2]).append("=").append(nonce)
                .append(",")
                .append(headerKeys[3]).append("=").append(xSecuritySign)
                .toString();

        this.headers.put("Authorization", authorization);
        this.headers.put("content-type","application/json");
        this.headers.put("accept","application/json");
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getxSecuritySign(String apiKey, String secretKey, String timestamp, String nonce) {
        //拼接认证字符串
        String concatStr = new StringBuilder().append(apiKey).append(secretKey).append(timestamp).append(nonce).toString();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str = encoder.encode(concatStr.getBytes());
        String md5Str = Md5Crypt.md5Crypt(base64Str.getBytes());
        return md5Str;
    }

    public static synchronized Authentication getInstance() {
        if (instance == null) {
            synchronized (Authentication.class) {
                if (instance == null) {
                    instance = new Authentication();
                }
            }
        }
        return instance;
    }


}
