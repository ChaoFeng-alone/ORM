package com.dao;

import com.domain.Car;
import com.jdbc.JdbcFactory;
import com.jdbc.SqlSession;
import com.util.MysqlFactoryUtil;

import java.util.List;

public class CarDao2 {
    public int save(Car car){
        String sql = "insert into car values(null,#{cname},#{color},#{price})";
        SqlSession session = MysqlFactoryUtil.getFactory().getSession();
        int count = session.insert(sql,car);
        return count;
    }

    public int delete(int cno){
        String sql = "delete from car from where cno = #{cno}";
        SqlSession session = MysqlFactoryUtil.getFactory().getSession();
        int count = session.delete(sql,cno);
        return count;
    }

    public List<Car> findAll(){
        String sql = "select * from car";
        SqlSession session = MysqlFactoryUtil.getFactory().getSession();
        List<Car> cars = session.selectList(sql,null,Car.class);
        return cars;
    }
}
