package com.test.xmltoexcel;

import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws DocumentException, IOException {
        Map<String, String> map = XmlUtil.xmlToMap("D:\\abc.xml");
        String path = "D:\\abc.xlsx";
        List<String> keys = new ArrayList<String>(map.size());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(map);
        for(String key : map.keySet()){
            keys.add(key);
        }
        ExcelUtil.export(path, keys, list);
       // ExcelUtil.insetValue(path, 1, map, keys);
    }
}
