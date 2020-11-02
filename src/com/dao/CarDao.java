//package com.dao;
//
//import com.domain.Car;
//import com.jdbc.JdbcFactory;
//import com.jdbc.JdbcUtil;
//import com.jdbc.SqlSession;
//import com.util.MysqlFactoryUtil;
//
//import java.sql.ResultSet;
//import java.util.List;
//
//public class CarDao {
//    public void save(Car car){
//        String sql = "insert into car(cname,color,price) values(?,?,?)";
//        JdbcUtil t = new JdbcUtil(
//                "com.mysql.jdbc.Driver",
//                "jdbc:mysql:///dymysql?characterEncoding=utf8",
//                "root",
//                "330212"
//        );
//        t.insert(sql,new Object[]{car.getCname(),car.getColor(),car.getPrice()});
//    }
//
//    public void save2(Car car){
//        String sql = "insert into car(cname,color,price) values(?,?,?)";
//        JdbcFactory factory = MysqlFactoryUtil.getFactory();
//        JdbcUtil util = factory.getUtil();
//        util.insert(sql,car.getCname(),car.getColor(),car.getPrice());
//    }
//
//    public  void save5(Car car){
//        String sql = "insert into car  values(null,#{cname},#{color},#{price})";
//        JdbcFactory factory = MysqlFactoryUtil.getFactory();
//        SqlSession session = factory.getSession();
//        session.insert(sql,car);
//    }
//
//    public void findAll(){
//        String sql = "select * from car";
//
//        JdbcUtil t = new JdbcUtil(
//                "com.mysql.jdbc.Driver",
//                "jdbc:mysql:///dymysql?characterEncoding=utf8",
//                "root",
//                "330212"
//        );
//
////        List<Car> rows = t.selectList(sql,new CarRowMapper(),null);
//        List<Car> rows = t.selectList(sql,Car.class);
//        System.out.println(rows);
//    }
//
//    public void findAll2(){
//        String sql = "select * from car";
//        JdbcFactory factory = MysqlFactoryUtil.getFactory();
//        JdbcUtil util = factory.getUtil();
//
////        List<Car> rows = t.selectList(sql,new CarRowMapper(),null);
//        List<Car> rows = util.selectList(sql,Car.class);
//        System.out.println(rows);
//    }
//
//    public void findCount(){
//        String sql = "select count(*) from car";
//        JdbcUtil t = new JdbcUtil(
//                "com.mysql.jdbc.Driver",
//                "jdbc:mysql:///dymysql?characterEncoding=utf8",
//                "root",
//                "330212"
//        );
//        List<Integer> cars = t.selectList(sql,Integer.class);
//        System.out.println(cars);
//    }
//}
