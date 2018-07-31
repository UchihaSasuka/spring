package com.test.dynamicsql;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wiseacre on 2018/7/31.
 */
public class InsertOperator extends AbstractOperator {

    private SqlBuilder sqlBuilder;

    public InsertOperator(SqlBuilder sqlBuilder){
        super();
        this.sqlBuilder = sqlBuilder;
    }

    public Object doOperator(Statement statement, String json, String tableName) throws Exception {
        String sql = sqlBuilder.build(json, tableName);
        return statement.execute(sql);
    }

    public Object doOperator(Statement statement, String json, String tableName, Example example) throws Exception{
        return null;
    }

}
