/*
 * Copyright (c) 2026 Athena
 * All rights reserved.
 */

package com.utils;

/**
 * 常量类
 * 定义项目所需的常量配置
 *
 * @author Athena
 * @since 2026-05-29
 */
public class Constants {

    //用例文件地址
    public static final String EXCEL_PATH = "src/test/resources/cases_v3.xlsx";

    

    //响应体回写列号
    public static final int RESPONSE_CELLNUM = 8;

    //断言结果回写列号
    public static final int ASSERT_CELLNUM = 10;

    //jdbcurl
    public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
    //jdbcuser
    public static final String JDBC_USER = "future";
    //jdbcpassword
    public static final String JDBC_PASSWORD = "123456";

}
