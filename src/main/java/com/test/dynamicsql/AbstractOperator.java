package com.test.dynamicsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wiseacre on 2018/7/31.
 */
public abstract class AbstractOperator implements Operator{

    //Driver类全名
    public static String DRIVER="com.mysql.jdbc.Driver";
    //jdbc协议:子协议://ip:端口号/数据库名
    public static String URL="jdbc:mysql://localhost:3306/test2";
    //数据库用户名
    public static String USERNAME="root";
    //数据库密码
    public static String PASSWORD="123456789mmm";

    private Connection connection;

    public AbstractOperator(){
        this.connection = createConnection();
    }

    private Connection createConnection(){
        try {
            //加载驱动程序：它通过反射创建一个driver对象。
            Class.forName(DRIVER);

            //获得数据连接对象。
            // 在返回connection对象之前，DriverManager它内部会先校验驱动对象driver信息对不对,我们只要知道内部过程即可。
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object operator(String json, String tableName) throws Exception {
        return operator(json, tableName, null);
    }

    public Object operator(String json, String tableName, Example e) throws Exception{
        Statement statement = connection.createStatement();
        Object result = doOperator(statement, json, tableName, e);
        closeConnection();
        return result;
    }

    protected void closeConnection(){
        try {
            if(this.connection != null){
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract Object doOperator(Statement statement, String json, String tableName, Example example) throws Exception;
}
