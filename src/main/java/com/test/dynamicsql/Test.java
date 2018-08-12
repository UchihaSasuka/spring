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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("A","4");
        map.put("B","5");
        map.put("C","6");
        String json = JSON.toJSONString(map);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("id",1);
        Example example = new Example();
        example.setAnds(map2);
        Operator operator = new OperatorImpl(new UpdateBuilder());
        operator.operator(json, "user", example);
    }

}
