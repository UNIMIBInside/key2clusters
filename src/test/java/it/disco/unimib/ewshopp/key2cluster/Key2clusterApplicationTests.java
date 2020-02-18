package it.disco.unimib.ewshopp.key2cluster;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import lombok.extern.java.Log;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log
public  class Key2clusterApplicationTests {

	@LocalServerPort
	private int port;
	private String baseURI;

	@Before
	public void setupForTest() {

		RestAssured.port = port;
		System.out.println(RestAssured.port);
		baseURI = "http://localhost"; // replace as appropriate
	}

//	@Test
//	public void getCategoriesPerMultiKeyword() {
//
//		List<String> lstKeyword = new ArrayList<>(2);
//		lstKeyword.add("11 28 cassette");
//		lstKeyword.add("casa");
//		Response response = given()
//				.contentType(ContentType.JSON)
//				.accept(ContentType.JSON)
//				.baseUri(baseURI)
//				.body(lstKeyword)
//				.get("key2cluster/api/keyword");
//
//		response.getBody().prettyPrint();
//		KeywordCategories[] kc = response.then().assertThat()
//				.statusCode(HttpStatus.OK.value())
//				.extract()
//				.as(KeywordCategories[].class);
//		assertThat(kc.length, Matchers.is(2));
//		assertThat(kc[0].getCategories().size(), Matchers.is(1));
//		assertThat(kc[0].getCategories(), Matchers.contains("/Ocasiones y regalos/Vacaciones y eventos estacionales/Halloween y 31 de octubre(13740)"));
//
//	}

	@Test
	public void getCategoriesPerMultiKeyword() {

		List<String> lstKeyword = new ArrayList<>(2);
		lstKeyword.add("11 28 cassette");
		lstKeyword.add("casa");
		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.baseUri(baseURI)
				.queryParam("kws", "11 28 cassette,casa")
				.body(lstKeyword)
				.get("key2cluster/api/keywords");

		response.getBody().prettyPrint();
		KeywordCategories[] kc = response.then().assertThat()
				.statusCode(HttpStatus.OK.value())
				.extract()
				.as(KeywordCategories[].class);
		assertThat(kc.length, Matchers.is(2));
		assertThat(kc[0].getCategories().size(), Matchers.is(1));
		assertThat(kc[0].getCategories(), Matchers.contains("/Ocasiones y regalos/Vacaciones y eventos estacionales/Halloween y 31 de octubre(13740)"));


		Response response2 = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.baseUri(baseURI)
				.queryParam("kws", "ciao,parco")
				.body(lstKeyword)
				.get("key2cluster/api/keywords");

		response2.getBody().prettyPrint();


	}



}
