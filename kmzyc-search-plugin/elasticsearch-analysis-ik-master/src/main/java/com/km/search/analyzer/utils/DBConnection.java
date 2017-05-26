package com.km.search.analyzer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBConnection {

    private static Logger logger = Logger.getLogger(DBConnection.class);

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    private DBConnection() throws Exception {
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
                            .getParent()
                    + "/jdbc.properties");
            Properties prop = new Properties();
            // prop.load(this.getClass().getResourceAsStream("/jdbc.properties"));
            prop.load(is);
            Class.forName(prop.getProperty("driverClassName"));
            conn = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("username"), prop.getProperty("password"));
            stmt = conn.createStatement();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static DBConnection openConnection() throws Exception {
        return new DBConnection();
    }


    public int queryCount(String querySql) throws SQLException {
        String countSql = "select count(1) from (" + querySql + ")";
        logger.info(" executeSql: " + countSql);
        rs = stmt.executeQuery(countSql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public ResultSet queryPage(String querySql, int page, int pageSize) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM (SELECT t1.*, ROWNUM as rn FROM (");
        sb.append(querySql);
        sb.append(") t1 WHERE ROWNUM <= ");
        sb.append(page * pageSize);
        sb.append(") t2 where t2.rn > ");
        sb.append((page - 1) * pageSize);
        logger.info(" executeSql: " + sb.toString());
        rs = stmt.executeQuery(sb.toString());
        return rs;
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("dbconnection close stream excepion", e);
        }
    }

}
