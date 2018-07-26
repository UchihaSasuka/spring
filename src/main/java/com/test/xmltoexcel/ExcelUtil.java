package com.test.xmltoexcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static void insetValue(String fileName, int colNum, Map<String, String> map, List<String> keys) throws IOException {

        Workbook workbook = getWorkbook(fileName);
        Sheet sheet = workbook.createSheet();
        Row row ;
        Cell cell;
        int rowNum = 0;
        for(int i = rowNum; i < keys.size(); i++){
            row = sheet.createRow(i);
            cell = row.createCell(colNum);
            String key = keys.get(i);
            cell.setCellValue(map.get(key));
        }
        if(writeToFile(workbook, fileName)){
            logger.info("导出成功");
        }else {
            logger.info("导出失败");

        }
    }

    public static void export(String fileName, List<String> keys,  List<Map<String, String>> list) throws IOException {

        Workbook workbook = getWorkbook(fileName);
        Sheet sheet = workbook.createSheet();
        Row row ;
        Cell[] cell = new Cell[list.size() + 1];
        String key;
        Map<String, String> map ;
        for(int i = 0; i < keys.size(); i++){
            row = sheet.createRow(i);
            //设置键
            key = keys.get(i);
            cell[0] = row.createCell(0);
            cell[0].setCellValue(key);
            //设置值
            for(int j = 1 ; j <= list.size() ; j++){
                cell[j] = row.createCell(j);
                map = list.get(j-1);
                cell[j].setCellValue(map.get(key));
            }

        }

        if(writeToFile(workbook, fileName)){
            logger.info("导出成功");
        }else {
            logger.info("导出失败");

        }

    }

    private static Workbook getWorkbook(String fileName) throws IOException {
        Workbook workbook = null;
        if (fileName.endsWith("xlsx"))
        {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls"))
        {
            workbook = new HSSFWorkbook();
        } else
        {
            try
            {
                throw new Exception("invalid file name, should be xls or xlsx");
            } catch (Exception e)
            {
                logger.info("必须是xls或者xlsx结尾的文件.");
                e.printStackTrace();
            }

        }
        return workbook;
    }

    private static boolean writeToFile(Workbook workbook, String fileName){
        FileOutputStream fos;
        boolean flag;
        try
        {
            fos = new FileOutputStream(fileName);
            workbook.write(fos);
            fos.close();
            flag = true;
        } catch (FileNotFoundException e)
        {
            logger.info("文件不存在");
            flag = false;
            e.printStackTrace();
        } catch (IOException e)
        {
            logger.info("文件写入错误");
            flag = false;
            e.printStackTrace();

        }
        return flag;
    }
}
