package com.test.jdbc;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLUtil {


    public static String createSql(Map<String, String> map, String tableName){
        String sql = "insert into " + tableName + " (";
        List<String> valueList = new ArrayList<String>(map.size());
        int count = 1;
        for (String key : map.keySet()){
            if(count++ < map.size()){
                sql = sql + key + ",";
            }else {
                sql = sql + key + ")";
            }
            String value = map.get(key);
            valueList.add(StringUtils.isEmpty(map.get(key)) ? null : value);
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
        return sql;
    }
}
