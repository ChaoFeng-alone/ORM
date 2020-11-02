package com.jdbc;

import com.jdbc.pool.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JdbcQueryTemplate extends JdbcTemplate {
    public JdbcQueryTemplate(String url, String user, String password, String drive, ConnectionPool pool) {
        super(drive, url, user, password, pool);
    }

    @Override
    protected Object five() throws SQLException {
        ResultSet rs = statement.executeQuery();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<String, Object>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                String key = rs.getMetaData().getColumnName(i);
                Object value = rs.getObject(i);
                row.put(key, value);
            }
            rows.add(row);
        }
        return rows;
    }
}
