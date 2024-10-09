package restAPI;

import java.util.HashMap;

import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {

	String empId;

	@Test
	public void Test01_GetAllEmployee() {

		// BasicConfigurator.configure();
		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();
		Response response = request.get();

		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(resBody);
		System.out.println(resCode);
	}

	@Test

	public void Test02_GetSingleEmployee() {

		RestAssured.baseURI = "http://localhost:3000/employees";

		RequestSpecification request = RestAssured.given();

		Response response = request.get("/1");

		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(resBody);
		System.out.println(resCode);

		AssertJUnit.assertEquals(resCode, 200);

		JsonPath json = response.jsonPath();
		String fristName = json.get("name");
		System.out.println(fristName);

	}

	@Test

	public void Test03_CreateEmployee() {
		HashMap<String, String> obj = new HashMap<String, String>();

		obj.put("id", "105");
		obj.put("name", "Asad");
		obj.put("salary", "9000");
		
		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(obj).post();

		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		Assert.assertEquals(resCode, 201);
		System.out.println(resBody);
		System.out.println(resCode);
		JsonPath json = response.jsonPath();
		empId = json.get("id");

		System.out.println("Employee ID: " + empId);

	}

	@Test
	public void Test04_DeleteEmployee() {

		RestAssured.baseURI = "http://localhost:3000/employees/" + empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete();
		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(resCode);
		System.out.println("Response Body: " + resBody);
		Assert.assertEquals(resCode, 200);
	}
	@Test
	
	public void Test05_TestDeletedEmployee() {
		
	

			RestAssured.baseURI = "http://localhost:3000/employess/"+ empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete();
		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(resCode);
		System.out.println("Response Body: " + resBody);
		Assert.assertEquals(resCode, 404);
		
		
	}


}
