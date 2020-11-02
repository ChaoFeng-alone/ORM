package com.jdbc.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private List<ConnectionProxy> connections;

    public ConnectionPool(String driver,String url ,String user,String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        //创建连接池时，创建初始连接对象
        connections = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Connection conn = DriverManager.getConnection(url,user,password);
            ConnectionProxy cp = new ConnectionProxy(conn);
            connections.add(cp);
        }
    }

    public Connection getConnection(){
        //找到空闲连接,改变使用状态，返回连接对象
        int wait_time = 0;
        while (true) {
            for (ConnectionProxy cp : connections) {
                if (!cp.isUseFlag()) {
                    synchronized (cp) {
                        if(!cp.isUseFlag()) {
                            cp.setUseFlag(true);
                            return cp;
                        }else {
                            break;
                        }
                    }
                }
            }
            wait_time+=100;
            if (wait_time==2100){
                throw new ConnectionPoolException("connect time out");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //连接池管理线程
    private class ConnecetionGenerator extends Thread{
        @Override
        public void run() {

        }

        public void checkAndCreate(){
            while (true) {
                //剩余空闲连接个数
                int count = 0;
                for (ConnectionProxy cp:connections){
                    if(!cp.isUseFlag()){
                        count++;
                    }
                }

                //空闲个数少于5个开始补充，每次扩容一半，100封顶
                if(count<=5){

                }

                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
