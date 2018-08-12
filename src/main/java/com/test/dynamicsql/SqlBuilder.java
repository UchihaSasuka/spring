package com.test.dynamicsql;

/**
 * Created by wiseacre on 2018/7/31.
 */
public interface SqlBuilder {

    String build(String json, String tableName, Example example);
}
