import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		String Response = given()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when().log().all()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(Response);

		JsonPath js = new JsonPath(Response);// for parsing json
		String AccessToken = js.getString("access_token");
		System.out.println(AccessToken);

		GetCourse gc = given().queryParams("access_token", AccessToken).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		List<Api> apiCourses = gc.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());

			}
		}

		// Get the Course names of Web Automation

		ArrayList<String> a = new ArrayList<String>();
		List<WebAutomation> w = gc.getCourses().getWebAutomation();
		
		for (int j = 0; j < w.size(); j++) {

			a.add(w.get(j).getCourseTitle());

		}
		List<String> expectedList = Arrays.asList(courseTitles);
		Assert.assertTrue(a.equals(expectedList));

	}

}
