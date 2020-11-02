package com.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接的静态代理
 */
public class ConnectionProxy extends AbstrctConnection{

    //判断此时连接能否复用
    private boolean closeFlag = false;
    //使用状态
    private boolean useFlag = false;

    public ConnectionProxy(Connection conn){
        super.conn=conn;
    }

    @Override
    public void close() throws SQLException {
        if (closeFlag) {
                conn.close();
        } else {
            //释放连接
            useFlag = false;
        }
    }

    public boolean isCloseFlag() {
        return closeFlag;
    }

    public void setCloseFlag(boolean closeFlag) {
        this.closeFlag = closeFlag;
    }

    public boolean isUseFlag() {
        return useFlag;
    }

    public void setUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }
}
