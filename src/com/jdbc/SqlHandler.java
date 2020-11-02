package com.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlHandler {
    public static SqlAndParam execute(String sql, Object param) {
        //字符串处理时记录原sql中的参数表
        List<String> keys = new ArrayList<>();

        while (true) {
            int i1 = sql.indexOf("#{");
            int i2 = sql.indexOf("}");
            //没找到成对的{}，该退出了
            if (i1 == -1 || i2 == -1 || i1 > i2)
                break;
            String key = sql.substring(i1 + 2, i2).trim();
            keys.add(key);

            //处理到达结尾
            if (i2 == sql.length() - 1) {
                sql = sql.substring(0, i1) + "?";
                break;
            } else {
                sql = sql.substring(0, i1) + "?" + sql.substring(i2 + 1);
                continue;
            }
        }

        List<Object> params = new ArrayList<>();

        Class c = param.getClass();
        if (c == int.class || c == Integer.class || c == double.class || c == Double.class || c == String.class) {
            params.add(param);
        } else if (c == Map.class || c == HashMap.class) {
            for (String key:keys){
                Map map = (Map)param;
                Object value = map.get(key);
                params.add(value);
            }
        } else {
            try {
                for (String key : keys) {
                    String mname = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);

                    Method method = c.getMethod(mname);
                    Object value = method.invoke(param);
                    params.add(value);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return new SqlAndParam(sql,params);
    }
}
