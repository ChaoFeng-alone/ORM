package com.jdbc;

import com.jdbc.pool.ConnectionPool;

import java.sql.SQLException;

class JdbcUpdateTemplate extends JdbcTemplate {

    public JdbcUpdateTemplate(String url, String user, String password, String drive, ConnectionPool pool) {
        super(drive, url, user, password, pool);
    }

    @Override
    protected Object five() throws SQLException {
        return statement.executeUpdate();
    }
}
