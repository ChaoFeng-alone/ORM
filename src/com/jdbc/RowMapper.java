package com.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface RowMapper<T> {
    public T mapping(Map<String,Object> row) throws SQLException;
}
