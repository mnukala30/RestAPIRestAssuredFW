package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExcelToJsonConvertor {
	
	
	
	
	public  Object[][] readExcelFileAsJsonObject_RowWise(String filePath) {
       List<Map<String,String>>queryParamsObj = new ArrayList<Map<String,String>>();
       Map<String, String>queryParams = null;
        try {

            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            for (Sheet sheet : workbook) {
                int lastRowNum = sheet.getLastRowNum();
                int lastColumnNum = sheet.getRow(0).getLastCellNum();
                Row firstRowAsKeys = sheet.getRow(0); 
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    queryParams= new HashMap<String,String>();
                    if (row != null) {
                        for (int j = 0; j < lastColumnNum; j++) {
                        	String columnHeader=firstRowAsKeys.getCell(j).getStringCellValue();
                        	if(columnHeader.contains("TestCaseID_isEnabled") && row.getCell(j).getStringCellValue().equalsIgnoreCase("N")) {
                    			break;
	                        }else if(columnHeader.contains("_isEnabled")&&row.getCell(j).getStringCellValue().equalsIgnoreCase("Y")){
	                        	queryParams.put(firstRowAsKeys.getCell(j-1).getStringCellValue(),
	                        		                                       row.getCell(j-1).getStringCellValue());
	                        			
	                        	}
                        	}
                    }
                    if(!queryParams.isEmpty()) {
                        queryParamsObj.add(queryParams);
                    }
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asTwoDimensionalArray(queryParamsObj);
    }
	private static Object[][] asTwoDimensionalArray(List<Map<String, String>> interimResults) {
	    Object[][] results = new Object[interimResults.size()][1];
	    int index = 0;
	    for (Map<String, String> interimResult : interimResults) {
	      results[index++] = new Object[] {interimResult};
	    }
	    return results;
	  }

	public static void main(String args[]) {
		ExcelToJsonConvertor excelConvertor = new ExcelToJsonConvertor();
        String filePath = "C:\\Users\\Maheshn\\git\\RestAssuredFramework\\src\\main\\java\\com\\qa\\testdata\\APITestData.xlsx";
        Object[][] results= excelConvertor.readExcelFileAsJsonObject_RowWise(filePath);
        System.out.println(results);
	}
}
