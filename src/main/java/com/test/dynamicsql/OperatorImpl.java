package com.test.dynamicsql;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wiseacre on 2018/7/31.
 */
public class OperatorImpl extends AbstractOperator {

    private SqlBuilder sqlBuilder;

    public OperatorImpl(SqlBuilder sqlBuilder){
        super();
        this.sqlBuilder = sqlBuilder;
    }


    public Object doOperator(Statement statement, String json, String tableName, Example example) throws Exception{
        String sql = sqlBuilder.build(json, tableName, example);
        return statement.execute(sql);
    }

}
