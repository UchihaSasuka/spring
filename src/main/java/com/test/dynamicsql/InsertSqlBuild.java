package com.test.dynamicsql;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wiseacre on 2018/7/31.
 */
public class InsertSqlBuild implements SqlBuilder{

    public String build(String json, String tableName) {
        Map map = JSON.parseObject(json);
        String sql = "insert into " + tableName + " (";
        List<String> valueList = new ArrayList<String>(map.size());
        int count = 1;
        for (Object key : map.keySet()){
            if(count++ < map.size()){
                sql = sql + key + ",";
            }else {
                sql = sql + key + ")";
            }
            Object value = map.get(key);
            valueList.add(StringUtils.isEmpty(map.get(key)) ? null : (String) value);
        }
        sql += " values(";
        count = 1;
        for(String value : valueList){
            value = StringUtils.isEmpty(value) ? null : value;
            if(count++ < valueList.size()){
                sql = sql + value + ",";
            }else {
                sql = sql + value + ")";
            }
        }
        return sql  ;
    }
}
