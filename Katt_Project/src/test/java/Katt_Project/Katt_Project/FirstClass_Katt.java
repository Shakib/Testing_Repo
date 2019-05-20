package Katt_Project.Katt_Project;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class FirstClass_Katt {
	
	private static final String BASE_URI = "https://cat-fact.herokuapp.com";
	
	@BeforeClass
	public void testResponseCode() {
		given().
		when().
			get(baseURI).
		then().
			assertThat().statusCode(200);
	}
	
	@BeforeTest
	public void setup() {
		baseURI = BASE_URI;
	}
	
	@Test
	public void testGetRandomFact_givenAnimalType_Cat() {
		Response response = get(baseURI + "/facts/random?animal_type=cat&amount=1");
		String catt = response.jsonPath().get("type");
		Assert.assertTrue(catt.equalsIgnoreCase("cat"));
	}
	
	@Test
	public void testGetRandomFact_givenAnimalType_Horse() {
		Response response = get(baseURI + "/facts/random?animal_type=horse&amount=1");
		String catt = response.jsonPath().get("type");
		Assert.assertTrue(catt.equalsIgnoreCase("horse"));
	}
	
	@Test
	public void testGetAmountOfTheAnimals_Horse() {
		Response response = get(baseURI + "/facts/random?animal_type=horse&amount=2");
		int amoun = response.jsonPath().getList(DEFAULT_BODY_ROOT_PATH).size();
		assertEquals(amoun, 2);	
	}
	
	@Test
	public void testGetAmountOfTheAnimals_Cat() {
		Response response = get(baseURI + "/facts/random?animal_type=cat&amount=2");
		int amoun = response.jsonPath().getList(DEFAULT_BODY_ROOT_PATH).size();
		assertEquals(amoun, 2);	
	}
	
	@Test
	public void getFactByID() {
		Response factId = get(baseURI + "/facts/5c69c4eec2d7a200140f67ef");
		JsonPath path = factId.jsonPath();
		String valueObj = path.get("user");
		assertEquals(valueObj, "5a9ac18c7478810ea6c06381");
		factId.prettyPrint(); 
		
	}
	
	@Test
	public void testGetQueuedFacts() {
		Response response = get(baseURI + "/facts?animal_type=cat,horse");
		response.prettyPrint();
		Assert.assertEquals(200, response.getStatusCode());
	}
	

			
}
