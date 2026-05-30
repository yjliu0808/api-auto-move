/*
 * Copyright (c) 2026 Athena
 * All rights reserved.
 */

package com.utils;

import com.alibaba.fastjson.JSONObject;
import com.pojo.CaseInfo;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * HTTP工具类
 * 提供HTTP请求相关的方法，包括GET、POST、PATCH请求以及鉴权参数处理
 *
 * @author Athena
 * @since 2026-05-29
 */
public class HttpUtils {

    private static Logger logger = Logger.getLogger(HttpUtils.class);


    /**
     * 在JSON参数中添加鉴权参数：timestamp和sign
     *
     * @param caseInfo 用例信息对象
     */
    public static void authorizationParam(CaseInfo caseInfo) {
        //timestamp 参数
        long timestamp = System.currentTimeMillis() / 1000;
        String token = GlobalVars.vars.get("${token}").toString();
        String token50 = token.substring(0,50);
        //token前50位和timestamp相加
        String sign = token50 + timestamp;
        //sign参数 rsa加密
        //sign = EncryptUtils.rsaEncrypt(sign);
        //params => Map
        Map map = JSONObject.parseObject(caseInfo.getParams(), Map.class);
        map.put("timestamp",timestamp);
        map.put("sign",sign);
        //添加完参数之后转会Json串
        String params = JSONObject.toJSONString(map);
        caseInfo.setParams(params);
    }

    /**
     * 获取默认请求头Map
     *
     * @return 默认请求头Map对象
     */
    public static Map<String,Object> getDefaultHeaders() {
        Map<String,Object> headers = new HashMap<>();
       // headers.put("X-Lemonban-Media-Type", Constants.X_LEMONBAN_MEDIA_TYPE);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    /**
     * 获取带有鉴权信息的请求头
     *
     * @return 包含Authorization的请求头Map
     */
    public static Map<String,Object> getAuthorizationHeaders() {
        Map<String, Object> headers = getDefaultHeaders();
        //取出token，存到headers中
        Object token = GlobalVars.vars.get("${token}");
        System.out.println("Bearer " + token);
        headers.put("Authorization","Bearer " + token);
        return headers;
    }

    /**
     * HTTP请求方法，并返回响应体
     *
     * @param caseInfo 用例信息对象
     * @param headers  请求头Map
     * @return 响应体字符串
     */
    public static String call(CaseInfo caseInfo,Map<String,Object> headers) {
        //1、获取请求参数
        String params = caseInfo.getParams();
        String contentType = caseInfo.getContentType();
        String type = caseInfo.getType();
        String url = caseInfo.getUrl();
        String body = null;
        //2、判断请求类型
        logger.info("请求url：" + url);
        logger.info("请求头：" + headers);
        logger.info("请求参数：" + params);
        //获取时间戳


        if("post".equalsIgnoreCase(type)) {
            //2.1、如果是form类型参数的接口
            if("form".equalsIgnoreCase(contentType)) {
                params = json2KeyValue(params);
                //指定Content-Type为form类型参数
                headers.put("Content-Type", "application/x-www-form-urlencoded");
            }
            body = HttpUtils.myPost(url, headers, params);
        }else if("get".equalsIgnoreCase(type)) {
            body = HttpUtils.myGet(url, headers);
        }else if("patch".equalsIgnoreCase(type)) {
            body = HttpUtils.myPatch(url, headers, params);
        }
        logger.info("响应体：" + body);
        return body;
    }

    /**
     * JSON字符串转成key=value格式字符串
     * 例如：{"mobilephone":"13877788811","pwd":"12345678"} 转换为 mobilephone=13877788811&pwd=12345678
     *
     * @param params JSON参数字符串
     * @return key=value格式的参数字符串
     */
    private static String json2KeyValue(String params) {
        //2.2、json参数转成 jsonStr => map => keyValueStr
        //JSON转成map
        Map<String,String> map = JSONObject.parseObject(params, Map.class);
        //获取所有的key
        Set<String> keySet = map.keySet();
        String formParams = "";
        for (String key : keySet) {
            //key=value&
            formParams += key + "=" + map.get(key) + "&";
        }
        //删除最后一个字符串
        formParams = formParams.substring(0,formParams.length()-1);
        System.out.println(formParams);
        //参数重新赋值
        params = formParams;
        return params;
    }


    /**
     * GET请求
     *
     * @param url     接口地址
     * @param headers 请求头Map
     * @return 响应体字符串
     */
    public static String myGet(String url, Map<String,Object> headers) {
        return given().headers(headers).get(url).asString();
    }

    /**
     * post请求
     * @param url               接口地址
     * @param headers           请求头
     * @param params            json参数：{id:11,name:"ZS"}   form参数：id=123&name=zs
     * @return                  响应体，如果需要响应头，不调用asString,返回response
     */
    public static String myPost(String url, Map<String,Object> headers,String params) {
        return given().headers(headers).body(params).post(url).asString();
    }

    /**
     * PATCH请求
     *
     * @param url     接口地址
     * @param headers 请求头Map
     * @param params  请求参数
     * @return 响应体字符串
     */
    public static String myPatch(String url, Map<String,Object> headers,String params) {
        return given().headers(headers).body(params).patch(url).asString();
    }

}
