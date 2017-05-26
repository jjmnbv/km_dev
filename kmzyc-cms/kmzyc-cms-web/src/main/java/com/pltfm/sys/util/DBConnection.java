package com.pltfm.sys.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DBConnection {
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);

    private static Connection conn;


    public static Connection getConnection() throws java.sql.SQLException {
        try {
            //建立数据库连接
            Context ctx = new InitialContext();
            DataSource bds = (DataSource) ctx.lookup("java:/jdbc/search");
            ctx.close();
            ////System.out.println("------------> 准备获取数据库连接...2005,当前活动连接＝"+bds.getNumActive());
            conn = bds.getConnection();
            ////System.out.println("<------------ 获取链接成功！！！2005");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * 释放所有的数据库操作资源，请用在finally块里
     *
     * @param Connection, PreparedStatement, ResultSet
     * @author gaobo
     */
    public static void releaseConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
                //System.out.println("--------------- rs.close()！！！");
            } catch (Exception e) {
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
                pstmt = null;
                //System.out.println("--------------- pstmt.close()！！！");
            } catch (Exception e) {
            }
        }
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    conn = null;
                    //System.out.println("--------------- conn.close()！！！");
                }
            } catch (Exception e) {
            	logger.error("DBConnection.releaseConnection异常：" + e.getMessage(), e);
            }
        }
    }


    /**
     * 释放所有的数据库操作资源，请用在finally块里
     *
     * @param Connection, CallableStatement, ResultSet
     * @author gaobo
     */
    public static void releaseConnection(Connection conn, CallableStatement cstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
                //System.out.println("--------------- rs.close()！！！");
            } catch (Exception e) {
            }
        }
        if (cstmt != null) {
            try {
                cstmt.close();
                cstmt = null;
                //System.out.println("--------------- cstmt.close()！！！");
            } catch (Exception e) {
            }
        }
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    conn = null;
                    //System.out.println("--------------- conn.close()！！！");
                }
            } catch (Exception e) {
            	logger.error("DBConnection.releaseConnection异常：" + e.getMessage(), e);
            }
        }
    }


}

