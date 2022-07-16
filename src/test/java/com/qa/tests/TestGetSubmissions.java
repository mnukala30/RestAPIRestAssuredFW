package com.qa.tests;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.util.ExcelToJsonConvertor;
import com.qa.util.TestBase;
import com.qa.util.TestUtil;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestGetSubmissions extends TestBase{
	
	
	@BeforeMethod
	public void setUp(){
		TestBase.init();
	}
	
	  @DataProvider 
	  public Object[][] getMapData(){
	 
		  ExcelToJsonConvertor jsonConvertor= new ExcelToJsonConvertor();
		  Object[][] testData=jsonConvertor.readExcelFileAsJsonObject_RowWise(TestUtil.TESTDATA_SHEET_PATH);
		  return testData;
	  }
	@Test(dataProvider = "getMapData")
	public void getWeatherDetailsWithCorrectCityNameTest(Map<String, String> queryParams){
		int responseCode=Integer.parseInt(queryParams.get("ResponseCode"));
		String testCaseID=queryParams.get("TestCaseID");
		queryParams.remove("ResponseCode");
		queryParams.remove("TestCaseID");
		RestAssured.baseURI = "https://snap-naga.qa.nasinsurance.com";
		Response response=(Response) RestAssured.given()
						  .header("Content-type","application/json")
						  .queryParams(queryParams)
						  .get("/submission/broker_portal")	
						  .then()
						  .statusCode(responseCode);
		JsonPath jsonPath=response.jsonPath();
		String shoppingCode = jsonPath.get("data.item[0].shoppingCode");
		System.out.println(shoppingCode);
	}
	
	
	
	
	
	
	

}
