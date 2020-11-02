package com.test;

import com.dao.CarDao2;
import com.dao.CarDao3;
import com.domain.Car;
import com.util.MysqlFactoryUtil;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
//        CarDao2 dao = new CarDao2();
        Car car = new Car(null, "benz", "black", 40);

        CarDao3 dao = MysqlFactoryUtil.getFactory().getSession().creatDaoImpl(CarDao3.class);
//        dao.save(car);
//        dao.delete(8);
        List<Car> cars = dao.findAll();
        for(Car c:cars){
            System.out.println(c);
        }
    }
}
