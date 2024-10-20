import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String Response = given()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when().log().all()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(Response);

		JsonPath js = new JsonPath(Response);// for parsing json
		String AccessToken = js.getString("access_token");
		System.out.println(AccessToken);

		String Response2 = given().queryParams("access_token", AccessToken).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		System.out.println(Response2);

	}

}
