package com.jdbc;

import com.jdbc.pool.ConnectionPool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JdbcUtil {

    private String driver;
    private String url;
    private String user;
    private String password;
    private ConnectionPool pool;

    public JdbcUtil(String driver, String url, String user, String password,ConnectionPool pool) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        this.pool = pool;
    }

    public int insert(String sql, Object... param) {
        if (sql.trim().substring(0, 6).equalsIgnoreCase("insert")) {
            JdbcUpdateTemplate t = new JdbcUpdateTemplate(url, user, password, driver,pool);
            return (int) t.executeJdbc(sql, param);
        } else {
            throw new SqlFormatException("Not a insert sql :" + sql);
        }
    }

    public int update(String sql, Object... param) {
        if (sql.trim().substring(0, 6).equalsIgnoreCase("update")) {
            JdbcUpdateTemplate t = new JdbcUpdateTemplate(url, user, password, driver,pool);
            return (int) t.executeJdbc(sql, param);
        } else {
            throw new SqlFormatException("Not a update sql :" + sql);
        }
    }

    public int delete(String sql, Object... param) {
        if (sql.trim().substring(0, 6).equalsIgnoreCase("delete")) {
            JdbcUpdateTemplate t = new JdbcUpdateTemplate(url, user, password, driver,pool);
            return (int) t.executeJdbc(sql, param);
        } else {
            throw new SqlFormatException("Not a delete sql :" + sql);
        }
    }

    public List<Map<String, Object>> selectListMap(String sql, Object... param) {
        if (sql.trim().substring(0, 6).equalsIgnoreCase("select")) {
            JdbcQueryTemplate t = new JdbcQueryTemplate(url, user, password, driver,pool);
            List<Map<String, Object>> rs = (List<Map<String, Object>>) t.executeJdbc(sql, param);
            return rs;
        } else {
            throw new SqlFormatException("Not a select sql :" + sql);
        }
    }

    public <T> List<T> selectList(String sql, RowMapper<T> strategy, Object... param) {
        List<Map<String, Object>> rs = selectListMap(sql, param);
        List<T> rows = new ArrayList<T>();
        try {
            for (Map<String, Object> r : rs) {
                T row = strategy.mapping(r);
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public <T> T selectOne(String sql, RowMapper<T> strategy, Object... param) {
        List<T> rows = selectList(sql, strategy, param);
        if (rows.size() == 1)
            return rows.get(0);
        else
            return null;
    }

    public <T> T selectOne(String sql, Class<T> type, Object... param) {
        List<T> rows = selectList(sql, type, param);
        if (rows.size() == 1)
            return rows.get(0);
        else
            return null;
    }

    public Map<String, Object> selectMap(String sql, Object... param) {
        List<Map<String, Object>> rows = selectListMap(sql, param);
        if (rows.size() == 1)
        return rows.get(0);
        else
        return null;
    }

    //反射↓

    public <T> List<T> selectList(String sql, Class<T> type, Object... param) {
        if (sql.trim().substring(0, 6).equalsIgnoreCase("select")) {
            JdbcQueryTemplate t = new JdbcQueryTemplate(url, user, password, driver,pool);
            List<Map<String, Object>> rs = (List<Map<String, Object>>) t.executeJdbc(sql, param);
            List<T> rows = new ArrayList<T>();

            for (Map<String, Object> r : rs) {
                Object row = null;

                if (type == int.class || type == Integer.class) {
                    Collection cs = r.values();
                    for (Object c:cs){
                        row = ((Number)c).intValue();
                    }
                }else if(type == long.class||type==Long.class){
                    Collection cs = r.values();
                    for (Object c:cs){
                        row = ((Number)c).longValue();
                    }
                }else if(type == double.class||type==Double.class){
                    Collection cs = r.values();
                    for (Object c:cs){
                        row = ((Number)c).doubleValue();
                    }
                }else if(type==String.class){
                    Collection cs = r.values();
                    for (Object c:cs){
                        row = (String)c;
                    }
                }else{
                    try {
                        row = type.newInstance();
                        Method[] methods = type.getMethods();

                        for(Method m:methods){
                            String mname = m.getName();
                            //是一个set方法，找到对应的属性
                            if(mname.startsWith("set")){
                                mname = mname.substring(3);
                                mname = mname.toLowerCase();
                                Object value = r.get(mname);
                                if(value==null){
                                    //当前对象属性没有对应的表数据
                                    continue;
                                }else{
                                    //当前属性有对应的表数据，使用set方法赋值
                                    //使用反射调用方法，并赋值
                                    Class p = m.getParameterTypes()[0];
                                    if(p==int.class||p==Integer.class)
                                        m.invoke(row,((Number)value).intValue());
                                    else if(p==long.class||p==Long.class)
                                        m.invoke(row,((Number)value).longValue());
                                    else if(p==double.class||p==Double.class)
                                        m.invoke(row,((Number)value).doubleValue());
                                    else if(p==String.class)
                                        m.invoke(row,(String)value);

                                }
                            }
                        }
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                rows.add((T)row);
            }
            return rows;
        } else {
            throw new SqlFormatException("Not a select sql :" + sql);
        }
    }
}
