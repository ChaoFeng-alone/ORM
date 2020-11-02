package com.jdbc;

import com.jdbc.pool.ConnectionPool;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 负责创建JdbcUtil工具
 */
public class JdbcFactory {

    private String driver;
    private String url;
    private String user;
    private String password;
    private ConnectionPool connectionPool;

    public JdbcFactory() {
        this("db.properties");
    }

    /**
     * 人为规定文件都放在src文件夹下
     *
     * @param fileName
     */
    public JdbcFactory(String fileName) {
        String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath().substring(1);
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(path);
        readFile(file);
    }

    /**
     * 传进来的文件不一定在src文件夹下，可传任意文件
     *
     * @param file
     */
    public JdbcFactory(File file) {
        readFile(file);
    }

    private void readFile(File file) {
        InputStream is = null;
        Properties properties;
        try {
            is = new FileInputStream(file);
            properties = new Properties();
            properties.load(is);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

            connectionPool = this.createConnectionPool();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private ConnectionPool createConnectionPool() throws SQLException, ClassNotFoundException {
        return new ConnectionPool(driver, url, user, password);
    }

    public JdbcUtil getUtil() {
        return new JdbcUtil(driver, url, user, password,connectionPool);
    }

    public SqlSession getSession() {
        return new SqlSession(driver, url, user, password,connectionPool);
    }
}
