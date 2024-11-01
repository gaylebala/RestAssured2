
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class graphQl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Query
		int characterId = 10103;
		String response = given().log().all().header("Content-Type", "application/json").body(
				"{\"query\":\"query ($characterId: Int!, $episodeId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    type\\n    id\\n  }\\n  location(locationId: 8) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"Rahul\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"hulu\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"
						+ characterId + ",\"episodeId\":11283}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String charactername = js.getString("data.character.name");
		Assert.assertEquals(charactername, "robin");

		// Mutations

		String newCharacter = "Baskin Robin";
		String mutationResponse = given().log().all().header("Content-Type", "application/json").body(
				"{\"query\":\"mutation ($locationName: String!, $characterName: String!, $episodeName: String!) {\\n  createLocation(location: {name: $locationName, type: \\\"south\\\", dimension: \\\"234\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"Macho\\\", status: \\\"dead\\\", species: \\\"fantasy\\\", gender: \\\"male\\\", image: \\\"png\\\", originId: 15008, locationId: 15008}) {\\n    id\\n  }\\n  createEpisode(episode: {name: $episodeName, air_date: \\\"1950 june\\\", episode: \\\"prime\\\"}) {\\n    id\\n  }\\n  deleteLocations(locationIds: [15012]) {\\n    locationsDeleted\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"Newzealand\",\"characterName\":\""+newCharacter+"\",\"episodeName\":\"manifest\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
		System.out.println(mutationResponse);
		JsonPath js1 = new JsonPath(mutationResponse);

	}

}
