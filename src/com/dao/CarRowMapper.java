package com.dao;

import com.domain.Car;
import com.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapping(Map<String, Object> row) throws SQLException {
        int cno = (int) row.get("cno");
        String cname = (String) row.get("cname");
        String color = (String) row.get("color");
        int price = (int) row.get("price");
        return new Car(cno,cname,color,price);
    }
}
