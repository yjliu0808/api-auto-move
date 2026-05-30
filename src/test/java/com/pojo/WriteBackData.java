/*
 * Copyright (c) 2026 Athena
 * All rights reserved.
 */

package com.pojo;

/**
 * 回写数据类
 * 用于存储Excel回写所需的数据
 *
 * @author Athena
 * @since 2026-05-29
 */
public class WriteBackData {

    //回写行号
    private int rowNum;
    //回写列号
    private int cellNum;
    //回写sheetIndex
    private int sheetIndex;
    //回写内容
    private String content;

    public WriteBackData() {
    }

    public WriteBackData(int rowNum, int cellNum, int sheetIndex, String content) {
        this.rowNum = rowNum;
        this.cellNum = cellNum;
        this.sheetIndex = sheetIndex;
        this.content = content;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WriteBackData{" +
                "rowNum=" + rowNum +
                ", cellNum=" + cellNum +
                ", sheetIndex=" + sheetIndex +
                ", content='" + content + '\'' +
                '}';
    }
}
