package com.test.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MyClient {
    public static void main(String [] args) throws SQLException {
        Connection connection=null;
        Statement statement=null;

        connection=JDBCUtil.getConnection();
        statement=connection.createStatement();
        //需要在自己的数据库当中建立一张user表
        Map<String, String> map = new HashMap<String, String>();
        map.put("A","1");
        map.put("B","2");
        map.put("C","3");
        String sql=SQLUtil.createSql(map,SQLUtil.INSERT,"test");
        statement.executeUpdate(sql);
    }
}
