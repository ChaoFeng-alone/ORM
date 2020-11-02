package com.jdbc;

import com.jdbc.pool.ConnectionPool;

import java.sql.*;

abstract class JdbcTemplate {
    private String drive;
    private String url;
    private String user;
    private String password;

    protected ConnectionPool pool;

    protected Connection conn;
    protected PreparedStatement statement;
    protected ResultSet result;

    public JdbcTemplate(String drive,String url, String user, String password, ConnectionPool pool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.drive = drive;
        this.pool = pool;
    }

    public Object executeJdbc(String sql, Object[] param) {
        try {
            one();
            two();
            three();
            four(sql, param);
           Object res = five();
           return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                six();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void one() {

    }

    private void two() throws ClassNotFoundException {
//        Class.forName(drive);
    }

    private void three() throws SQLException {
//        conn = DriverManager.getConnection(url, user, password);
        conn = pool.getConnection();
    }

    private void four(String sql, Object[] param) throws SQLException {
        statement = conn.prepareStatement(sql);
        if(param==null)
            return;
        for (int i = 0; i < param.length; i++) {
            statement.setObject(i + 1, param[i]);
        }
    }

    protected abstract Object five() throws SQLException;

    private void six() throws SQLException {
        if (result != null)
            result.close();
        if (statement != null)
            statement.close();
        if (conn != null)
            conn.close();
    }
}
