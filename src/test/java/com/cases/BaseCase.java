/*
 * Copyright (c) 2026 Athena
 * All rights reserved.
 */

package com.cases;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.pojo.CaseInfo;
import com.pojo.WriteBackData;
import com.utils.ExcelUtils;
import com.utils.GlobalVars;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.util.Map;
import java.util.Set;

/**
 * 用例基类
 * 提供测试用例的基础方法，包括参数化、响应断言、数据回写等
 *
 * @author Athena
 * @since 2026-05-29
 */
public class BaseCase {

    private Logger logger = Logger.getLogger(BaseCase.class);

    //sheet开始索引
    public int startSheetIndex;
    //sheet个数
    public int sheetNum;

    @BeforeSuite
    public void setup() {
        logger.info("=====================自动化开始=========================");
      /*  GlobalVars.vars.put("${username}","test");
        GlobalVars.vars.put("${password}","123456");*/

    }

    @BeforeClass
    @Parameters({"startSheetIndex","sheetNum"})
    public void beforeClass(int startSheetIndex,int sheetNum) {
        //接受testng.xml中parameters 参数
        this.startSheetIndex = startSheetIndex;
        this.sheetNum = sheetNum;
    }

    @AfterMethod
    public void afterMethod() {
        logger.info("==============================================================");
    }

    @AfterSuite
    public void tearDown() throws Exception {
        //批量回写
        logger.info("====tearDown====");
        ExcelUtils.batchWrite();
    }

    /**
     * 添加回写内容到wbdList中
     * @param rowNum
     * @param cellNum
     * @param sheetIndex
     * @param content
     */
    public void addWriteBackData(int rowNum, int cellNum, int sheetIndex, String content) {
        WriteBackData wbd = new WriteBackData
                (rowNum,cellNum,sheetIndex, content);
        ExcelUtils.wbdList.add(wbd);
    }

    /**
     * 通过jsonpath从响应体中取出值存储到UserData中
     * @param body          响应体
     * @param path          jsonPath表达式
     * @param key           存储key
     */
    public void response2UserData(String body,String path,String key) {
        //read(json字符串，jsonpath表达式)
        //从响应体中获取jsonPath对应的值
        Object value = JSONPath.read(body, path);
        if(value != null) {
            //把value存储到vars（接口数据储存map）
            GlobalVars.vars.put(key, value);
        }
    }

    /**
     *  接口响应断言，直接多字段
     * @param caseInfo              获取期望值
     * @param body                  响应体
     */
    public boolean responseAssert(CaseInfo caseInfo, String body) {
        //断言结果
        boolean assertResult = true;
        //{"$.code":1,"$.msg":"密码为空"} 格式解释：{实际值表达式1：期望值1,实际值表达式2：期望值2}
        String expectedResult = caseInfo.getExpectedResult();
        //转Map<String,Object>
        Map<String,Object> map = JSONObject.parseObject(expectedResult, Map.class);
        //取出所有的key（实际值表达式），断言多个字段
        Set<String> keySet = map.keySet();
        for (String actualJsonPath : keySet) {
            //通过key取出期望值
            Object expectedValue = map.get(actualJsonPath);
            //实际值表达式+响应体=》获取实际值
            Object actualValue = JSONPath.read(body, actualJsonPath);
            //断言：期望值和实际值比较
            if(!expectedValue.equals(actualValue)) {
//                System.out.println("断言失败：实际值表达式："+actualJsonPath+"，实际值："+actualValue+"，期望值：" + expectedValue);
                logger.error("断言失败：实际值表达式："+actualJsonPath+"，实际值："+actualValue+"，期望值：" + expectedValue);
                //break;
                assertResult = false;
            }
        }
        if(assertResult) {
            logger.info("响应断言成功");
        }
        return assertResult;
    }

    /**
     * 参数化
     * @param caseInfo      需要替换的参数
     */
    @Step("参数化")
    public void paramsReplace(CaseInfo caseInfo) {
        String url = caseInfo.getUrl();
        String params = caseInfo.getParams();
        String expectedResult = caseInfo.getExpectedResult();
        String sql = caseInfo.getSql();
        //获取vars中所有占位符
        Set<String> keySet = GlobalVars.vars.keySet();
        for (String placeholder : keySet) {
            //{"mobile_phone":"${register_mb}","pwd":"${register_pwd}"}
            String value = GlobalVars.vars.get(placeholder).toString();
            //如果url有为空，占位符和实际值进行替换
            if(StringUtils.isNotBlank(url)) {
                url = url.replace(placeholder, value);
            }
            //如果params有为空，占位符和实际值进行替换
            if(StringUtils.isNotBlank(params)) {
                params = params.replace(placeholder, value);
            }
            //如果expectedResult有为空，占位符和实际值进行替换
            if(StringUtils.isNotBlank(expectedResult)) {
                expectedResult = expectedResult.replace(placeholder, value);
            }
            //如果sql有为空，占位符和实际值进行替换
            if(StringUtils.isNotBlank(sql)) {
                sql = sql.replace(placeholder, value);
            }
        }
        //重新赋值参数化之后的值
        caseInfo.setUrl(url);
        caseInfo.setParams(params);
        caseInfo.setExpectedResult(expectedResult);
        caseInfo.setSql(sql);
    }

}
