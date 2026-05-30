/*
 * Copyright (c) 2026 Athena
 * All rights reserved.
 */

package com.utils;

import com.pojo.Member;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * SQL工具类
 * 提供数据库操作相关的方法
 *
 * @author Athena
 * @since 2026-05-29
 */
public class SqlUtils {

    private static Logger logger = Logger.getLogger(SqlUtils.class);

    public static void main(String[] args) throws Exception {
        //DBUtils
        //新增
//        String sql ="insert into member (reg_name,pwd,mobile_phone,type,leave_amount,reg_time) " +
//        "VALUES ('海贼王','25D55AD283AA400AF464C76D713C07AD','18888888888',1,10,NOW());";
//        update(sql);
        //修改
//        String sql = "update member set type = 1 where mobile_phone = '18888888888';";
//        update(sql);

        //查询
//        mapHandler();
//        beanHandler();
//        beanListHandler();
//        scalarHandler();


//        System.out.println(result);

    }

    /**
     * 执行SQL获取单个结果集
     *
     * @param sql SQL语句
     * @return 查询结果对象
     * @throws SQLException SQL异常
     */
    public static Object getSingleResult(String sql) {
        //如果sql为空直接返回null
        if(StringUtils.isBlank(sql)) {
            return null;
        }
        logger.info("数据库断言SQL语句：" + sql);
        //执行sql语句
        Object result = null;
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection();
            ScalarHandler handler = new ScalarHandler();
            result = runner.query(conn, sql, handler);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            //finally 无论是否发生异常，finally都会执行，一般用来释放资源
            JDBCUtils.close(conn);
        }
        return result;
    }

    private static void scalarHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        ScalarHandler handler = new ScalarHandler();
        String sql = "select leave_amount from member a where a.mobile_phone = '18888888888';";
//        String sql = "select count(*) from member;";
        //java.math.BigDecimal cannot be cast to java.lang.Long
        Object result = runner.query(conn, sql, handler);
//        BigDecimal b = (BigDecimal)result;
        System.out.println(result);
        System.out.println(result.getClass());
    }

    private static void beanListHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        BeanListHandler<Member> handler = new BeanListHandler<>(Member.class);
        String sql = "select * from member a limit 5;";
        List<Member> result = runner.query(conn, sql, handler);
        for (Member member : result) {
            System.out.println(member);
        }
    }

    private static void beanHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        BeanHandler<Member> handler = new BeanHandler<>(Member.class);
        String sql = "select * from member a where a.mobile_phone = '18888888888';";
        Member result = runner.query(conn, sql, handler);
        System.out.println(result);
    }

    private static void mapHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        MapHandler handler = new MapHandler();
        String sql = "select * from member a where a.mobile_phone = '18888888888';";
        Map<String, Object> result = runner.query(conn, sql, handler);
        System.out.println(result);
    }

    public static void update(String sql) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        runner.update(conn,sql);
        JDBCUtils.close(conn);
    }
}
