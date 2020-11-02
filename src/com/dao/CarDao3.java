package com.dao;

import com.domain.Car;
import com.jdbc.annotations.Delete;
import com.jdbc.annotations.Insert;
import com.jdbc.annotations.Select;

import java.util.List;

public interface CarDao3 {
    @Insert("insert into car values(null,#{cname},#{color},#{price})")
    public int save(Car car);

//    public int update();

    @Delete("delete from car where cno = #{cno}")
    public int delete(int cno);

    @Select("select * from car")
    public List<Car> findAll();
}
