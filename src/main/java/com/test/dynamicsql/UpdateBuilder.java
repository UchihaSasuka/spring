package com.test.dynamicsql;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.util.Map;

/**
 * Created by wiseacre on 2018/8/1.
 */
public class UpdateBuilder implements SqlBuilder{
    private static final Logger logger = LoggerFactory.getLogger(UpdateBuilder.class);

    public String build(String json, String tableName, Example example) {
        Map map = JSON.parseObject(json);
        StringBuilder sb = new StringBuilder("update " + tableName + " set ");
        int count1 = 1;
        for (Object key : map.keySet()){

            if(!StringUtils.isEmpty(key)){
                Object object = map.get(key);
                if(object != null){
                    if(object instanceof String){
                        if(! StringUtils.isEmpty(object)){
                            sb.append(key).append("=");
                            sb.append("'").append(object).append("'");
                        }
                    }else{
                        sb.append(key).append("=").append(object);
                    }

                }

            }
            if(count1++ < map.size()){
                sb.append(",");
            }
        }

        Map<String, Object> ands = example.getAnds();
        int count2 = 1;
        for (String key : ands.keySet()) {
            if(!StringUtils.isEmpty(key)){
                Object object = ands.get(key);
                if(object != null){
                    if (object instanceof String ){
                        if(!StringUtils.isEmpty(object)){
                            sb.append(" where ").append(key).append("=")
                                    .append("'").append(object).append("'");
                        }
                    }else{
                        sb.append(" where ").append(key).append("=").append(object);
                    }

                }
                if(count2++ < ands.size()){
                    sb.append(" and ");
                }
            }
        }
        logger.info(sb.toString());
        return sb.toString()  ;
    }

}
