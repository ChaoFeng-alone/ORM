package com.jdbc;

import com.jdbc.annotations.Delete;
import com.jdbc.annotations.Insert;
import com.jdbc.annotations.Select;
import com.jdbc.annotations.Update;
import com.jdbc.pool.ConnectionPool;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * 实现jdbc交互
 * 提供新的sql语法
 * 与JdbcUtil作用相同
 * insert into car values(null,#{cname},#{color},#{price});  ,  car
 */
public class SqlSession {
    ConnectionPool pool;
    JdbcUtil util;

    public SqlSession(String driver, String url, String user, String password,ConnectionPool pool) {
        util = new JdbcUtil(driver, url, user, password,pool);
    }

    public int insert(String sql, Object param) {
        SqlAndParam sp = SqlHandler.execute(sql, param);
        return util.insert(sp.getSql(), sp.getParam().toArray());
    }

    public int insert(String sql) {
        return util.insert(sql);
    }

    public int update(String sql, Object param) {
        SqlAndParam sp = SqlHandler.execute(sql, param);
        return util.update(sp.getSql(), sp.getParam().toArray());
    }

    public int update(String sql) {
        return util.update(sql);
    }

    public int delete(String sql, Object param) {
        SqlAndParam sp = SqlHandler.execute(sql, param);
        return util.delete(sp.getSql(), sp.getParam().toArray());
    }

    public int delete(String sql) {
        return util.delete(sql);
    }

    public List<Map<String, Object>> selectListMap(String sql, Object param) {
        SqlAndParam sp = SqlHandler.execute(sql, param);
        return util.selectListMap(sp.getSql(), sp.getParam().toArray());
    }

    public List<Map<String, Object>> selectListMap(String sql) {
        return util.selectListMap(sql);
    }

    public Map<String, Object> selectMap(String sql, Object param) {
        SqlAndParam sp = SqlHandler.execute(sql, param);
        return util.selectMap(sp.getSql(), sp.getParam().toArray());
    }

    public Map<String, Object> selectMap(String sql) {
        return util.selectMap(sql);
    }

    public <T> List<T> selectList(String sql, Object param, Class<T> type) {
        SqlAndParam sp = SqlHandler.execute(sql, param);
        return util.selectList(sp.getSql(), type, sp.getParam().toArray());
    }

    public <T> List<T> selectList(String sql, Class<T> type) {
        return util.selectList(sql, type);
    }

    public <T> T selectOne(String sql, Object param, Class<T> type) {
        SqlAndParam sp = SqlHandler.execute(sql, param);
        return util.selectOne(sp.getSql(), type, sp.getParam().toArray());
    }

    public <T> T selectOne(String sql, Class<T> type) {
        return util.selectOne(sql, type);
    }

    /**
     * 根据指定的dao接口规则，创建其对应的实现类
     *
     * @param daoInterface
     * @param <T>
     * @return
     */
    public <T> T creatDaoImpl(Class<T> daoInterface) {
        return (T) Proxy.newProxyInstance(
                daoInterface.getClassLoader(),
                new Class[]{daoInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Annotation a = method.getAnnotations()[0];

                        //获取到了注解中的sql
                        String sql = (String) a.getClass().getMethod("value").invoke(a);

                        //获取到了参数u
                        Object param = args == null ? null : args[0];

                        Object result = null;//返回值
                        if (a.annotationType() == Insert.class) {
                            if (param == null)
                                result = insert(sql);
                            else
                                result = insert(sql, param);
                        } else if (a.annotationType() == Update.class) {
                            if (param == null)
                                result = update(sql);
                            else
                                result = update(sql, param);
                        } else if (a.annotationType() == Delete.class) {
                            if (param == null)
                                result = delete(sql);
                            else
                                result = delete(sql, param);
                        } else if (a.annotationType() == Select.class) {
                            Class<?> returnType = method.getReturnType();
                            if (returnType == List.class) {
                                Type genericReturnType = method.getGenericReturnType();
                                ParameterizedType type = (ParameterizedType) genericReturnType;
                                Class fx = (Class) type.getActualTypeArguments()[0];
                                if (fx == Map.class) {
                                    if (param == null) {
                                        result = selectListMap(sql);
                                    } else {
                                        result = selectListMap(sql, param);
                                    }
                                } else {
                                    if (param == null) {
                                        result = selectList(sql, fx);
                                    } else {
                                        result = selectList(sql, param, fx);
                                    }
                                }
                            } else {
                                if (returnType == Map.class) {
                                    if (param == null) {
                                        result = selectMap(sql);
                                    } else {
                                        result = selectMap(sql, param);
                                    }
                                } else {
                                    if (param == null) {
                                        result = selectOne(sql, returnType);
                                    } else {
                                        result = selectOne(sql, param, returnType);
                                    }
                                }
                            }
                        }
                        return result;
                    }
                }
        );
    }
}
