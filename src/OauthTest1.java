import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OauthTest1 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

//		System.setProperty("webdriver.chrome.driver",
//				"C:/Softwares/chromedriver-win64/chromedriver-win64/chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get(
//				"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("gaylebala");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("!226vishnuarcot");
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
		// String url = driver.getCurrentUrl();
		// String url =
		// "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVG7fiTyPJLUiQZpYDE7cm6Glm5QZqcsMc259JGs7MbgSEl6gA8FG_VjInbhc2EXiwlmHw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=consent";
		// String partialcode = url.split("code=")[1];
		// String code = partialcode.split("&scope")[0];

		String code = "4%2F0AVG7fiRmNdaCQo561Y7JZrYhjho6nGCbZNFZauYUnK5nhG11fgluw8o1sWPFyFUHHTer9w";
		System.out.println(code);

		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		System.out.println(accessTokenResponse);
		JsonPath jp = new JsonPath(accessTokenResponse);
		String access_token = jp.getString("access_token");

		System.out.println(access_token);

		/**
		 * String response = given().queryParam("access_token", access_token).when()
		 * .get("https://rahulshettyacademy.com/getCourse.php").asString();
		 * 
		 * System.out.println(response);
		 **/

		String r2 = given().log().all().queryParams("access_token", access_token).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php").asString();

		System.out.println(r2);

	}

}
