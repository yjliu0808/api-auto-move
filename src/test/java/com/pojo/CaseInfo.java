/*
 * Copyright (c) 2026 Athena
 * All rights reserved.
 */

package com.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 用例信息实体类
 * 对应用Excel测试用例表
 *
 * @author Athena
 * @since 2026-05-29
 */
public class CaseInfo {

    @Excel(name="用例编号")
    private int caseId;
    //Name(接口名)

    @Excel(name="请求方式")
    private String type;

    @Excel(name="url")
    private String url;

    @Excel(name="用例描述")
    private String desc;

    @Excel(name="参数")
    private String params;

    @Excel(name="参数类型")
    private String contentType;

    @Excel(name="期望结果")
    private String expectedResult;

    @Excel(name="sql")
    private String sql;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "caseId=" + caseId +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                ", params='" + params + '\'' +
                ", contentType='" + contentType + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
