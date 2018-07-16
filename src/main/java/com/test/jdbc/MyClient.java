package com.test.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MyClient {
    public static void main(String [] args) throws SQLException {
        Connection connection=null;
        Statement statement=null;

        connection=JDBCUtil.getConnection();
        statement=connection.createStatement();
        //需要在自己的数据库当中建立一张user表
        String sql="insert into user(loginName,userName,password,sex)values('tom123','tom','123456',1)";
        statement.executeUpdate(sql);
    }
}
