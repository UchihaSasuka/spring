package com.test.dynamicsql;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiseacre on 2018/7/31.
 */
public class Test{

    @org.junit.Test
    public void test() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("A","1");
        map.put("B","2");
        map.put("C","3");
        String json = JSON.toJSONString(map);
        Operator operator = new InsertOperator(new InsertSqlBuild());
        operator.operator(json, "user");
    }

}
