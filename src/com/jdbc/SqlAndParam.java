package com.jdbc;

import java.util.List;

public class SqlAndParam {
    private String sql;
    private List<Object> param;

    public SqlAndParam(String sql, List<Object> param) {
        this.sql = sql;
        this.param = param;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParam() {
        return param;
    }

    public void setParam(List<Object> param) {
        this.param = param;
    }
}
