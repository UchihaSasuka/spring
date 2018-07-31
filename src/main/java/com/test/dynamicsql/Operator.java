package com.test.dynamicsql;

import java.sql.SQLException;

/**
 * Created by wiseacre on 2018/7/31.
 */
public interface Operator {

    Object operator(String json, String tableName) throws Exception;

    Object operator(String json, String tableName, Example example) throws Exception;
}
