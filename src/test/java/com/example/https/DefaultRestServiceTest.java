package com.example.https;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Rule;
import org.junit.Test;

public class DefaultRestServiceTest {
	@Rule public HttpsServerRule server = HttpsServerRule$.MODULE$.apply(65200);
	
	@Test
	public void testServerIsUpAndRunning() {
		given()
			.baseUri("https://localhost:" + server.getPort())
			.auth().certificate("/sample-https-server.jks", "change-me-please")
			.when()
			.get("/")
			.then()
			.body(containsString("OK!"));
	}
}
