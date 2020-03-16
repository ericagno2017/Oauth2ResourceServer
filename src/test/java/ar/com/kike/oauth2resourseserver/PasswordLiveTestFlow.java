package ar.com.kike.oauth2resourseserver;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//Both, authorization server and and resource server must be running

public class PasswordLiveTestFlow {

	@Test
	public void givenUser_whenUseSuperClient_thenOkForSuperResourceOnly() {
		final String accessToken = obtainAccessToken("superClientIdPassword", "john", "123*");

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get("http://localhost:8082/oauth-resource/super/1");
		assertEquals(200, fooResponse.getStatusCode());
		assertNotNull(fooResponse.jsonPath().get("name"));

		final Response barResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get("http://localhost:8082/oauth-resource/normal/1");
		assertEquals(403, barResponse.getStatusCode());
	}

	@Test
	public void givenUser_whenUseNormalClient_thenOkForNormalResourceReadOnly() {
		final String accessToken = obtainAccessToken("normalClientIdPassword", "john", "123*");

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get("http://localhost:8082/oauth-resource/super/1");
		assertEquals(403, fooResponse.getStatusCode());

		final Response barReadResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get("http://localhost:8082/oauth-resource/normal/1");
		assertEquals(200, barReadResponse.getStatusCode());
		assertNotNull(barReadResponse.jsonPath().get("name"));

		final Response barWritResponse = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + accessToken).body("{\"id\":1,\"name\":\"MyBar\"}")
				.post("http://localhost:8082/oauth-resource/normal");
		assertEquals(403, barWritResponse.getStatusCode());
	}

	@Test
	public void givenAdmin_whenUseNormalClient_thenOkFoNormalResourceReadWrite() {
		final String accessToken = obtainAccessToken("normalClientIdPassword", "tom", "111");

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get("http://localhost:8082/oauth-resource/super/1");
		assertEquals(403, fooResponse.getStatusCode());

		final Response barResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get("http://localhost:8082/oauth-resource/normal/1");
		assertEquals(200, barResponse.getStatusCode());
		assertNotNull(barResponse.jsonPath().get("name"));

		final Response barWritResponse = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", "Bearer " + accessToken).body("{\"id\":1,\"name\":\"MyBar\"}")
				.post("http://localhost:8082/oauth-resource/normal");
		assertEquals(201, barWritResponse.getStatusCode());
		assertEquals("MyBar", barWritResponse.jsonPath().get("name"));
	}

	//

	private String obtainAccessToken(String clientId, String username, String password) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);
		final Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with()
				.params(params).when().post("http://localhost:8081/oauth-server/oauth/token");
		return response.jsonPath().getString("access_token");
	}

}
