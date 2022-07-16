package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToHashMap {

	 public static String ExcelSheetName = "C:\\Users\\Maheshn\\git\\RestAssuredFramework\\src\\main\\java\\com\\qa\\testdata\\APITestData.xlsx";
	    public static FileInputStream fis;
	    public static XSSFWorkbook workbook;
	    public static XSSFSheet sheet;
	    public static XSSFRow row;

	    public static void loadExcel() {

	        System.out.println("Load Excel Sheet.........");
	        File file = new File(ExcelSheetName);

	        try {
	            fis = new FileInputStream(file);
	            workbook = new XSSFWorkbook(fis);
	            sheet = workbook.getSheet("GetAdmissions");

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }


	        try {
	            fis.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }   
	    }

	    public static Map<String,Map<String,Object>> getDataMap() { 
	        if(sheet==null){
	            loadExcel();
	        }

	        Map<String, Map<String,Object>> parentMap = new HashMap<String, Map<String,Object>>();
	        Map<String, Object> childMap = new HashMap<String, Object>();

	        Iterator<Row> rowIterator = sheet.iterator();

	        while( rowIterator.hasNext() )
	        {
	            Row row = rowIterator.next();
	            childMap.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
	        }

	        parentMap.put("MASTERDATA", childMap);

	        return parentMap;


	    }

	    public static Object getValue(String key) {
	        Map<String,Object> mapValue = getDataMap().get("MASTERDATA");
	        Object retValue =	mapValue.get(key);

	        return retValue;
	    }

	public static void main(String[] args) {
		System.out.println(getValue("brokerId"));
	}

}
