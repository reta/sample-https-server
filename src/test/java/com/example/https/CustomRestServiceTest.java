package com.example.https;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Rule;
import org.junit.Test;

public class CustomRestServiceTest {
	@Rule public HttpsServerRule server = HttpsServerRule$.MODULE$.apply(65201, CustomRestService$.MODULE$.route());
	
	@Test
	public void testServerIsUpAndRunning() {
		given()
			.baseUri("https://localhost:" + server.getPort())
			.auth().certificate("/sample-https-server.jks", "change-me-please")
			.when()
			.get("/api/user/1")
			.then()
			.body(containsString("a@b.com"));
	}
}
