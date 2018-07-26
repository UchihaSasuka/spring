package com.test.xmltoexcel;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {

    public static Map<String, String> xmlToMap(String path) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(path);
        Element root = document.getRootElement();
        Map<String, String> map = new HashMap<String, String>();
        return parse(root, map);
    }

    public static void main(String[] args) throws DocumentException {
        xmlToMap("D:\\abc.xml");
    }

    private static Map<String, String> parse(Element element, Map<String, String> map){
        List list = element.elements();
        if(list == null || list.size() == 0){
            parseOneNode(element, map);
        }else{
            for (Object object : list){
                parse((Element) object, map);
                parseOneNode((Element) object, map);
            }
        }
        return map;
    }

    private static Map<String, String> parseOneNode(Element element, Map<String, String> map){
        Attribute attribute = element.attribute("key");
        if(attribute != null){
            String key = attribute.getValue();
            if(!StringUtils.isEmpty(key)){
                Attribute value = element.attribute("value");
                if(!StringUtils.isEmpty(value)){
                    map.put(key, value.getValue());
                }
            }
        }
        return map;
    }
}
