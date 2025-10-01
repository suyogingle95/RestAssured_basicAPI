package basic_api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.io.File;

import java.util.UUID;
import java.util.Random;


public class HTTPMethods {
	@Test (priority = 3)	
	void getUsers() 
	{
		given()
		
		.when()
			.get("https://jsonplaceholder.typicode.com/posts/1")
		
		.then()
			.statusCode(200)
			.body("userId", equalTo(1)) // Validate userId = 1
            .body("id", equalTo(1)) // Validate id = 1
            .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")) // Validate title
            .body("body", containsString("quia et suscipit"))
            .log().all();
		
	}
	
	
	@Test (priority = 2)	
	void createUsers() 
	{	
		
		HashMap data = new HashMap();
		data.put("name", "suyog");
	    data.put("job", "QA Engineer");
	    int hashcode = data.hashCode();
		System.out.println(hashcode);
		
		given()
		.contentType("application/json")
		.header("Content-Type", "application/json")
		.body(data)
		
		
		.when()
			.post("https://jsonplaceholder.typicode.com/posts/")
		
		.then()
			.statusCode(201)
            .log().all();
		
	}


	    @Test (dependsOnMethods = {"createUsers"})
	    void createUsersFromFile() {

	        // Point to your JSON file
	        File payload = new File("src/test/resources/userPayload.json");

	        given()
	            .contentType("application/json")
	            .body(payload)   // 🔥 Pass JSON file directly
	        .when()
	            .post("https://jsonplaceholder.typicode.com/posts")
	        .then()
	            .statusCode(201)
	            .log().all();
	    }

		@Test (priority = 2)	
		void createUsersWithTestData() 
		{	
			
			String randomName = "User_" + UUID.randomUUID().toString().substring(0,5);
			int randomId = new Random().nextInt(1000);
			
			
			HashMap<String, Object> data = new HashMap<>();
			data.put("name", randomName);
			data.put("job", "QA Engineer " + randomId);
			
			given()
			.contentType("application/json")
			.header("Content-Type", "application/json")
			.body(data)
			
			
			.when()
				.post("https://jsonplaceholder.typicode.com/posts/")
			
			.then()
				.statusCode(201)
	            .log().all();
			
		}
	

	
}
