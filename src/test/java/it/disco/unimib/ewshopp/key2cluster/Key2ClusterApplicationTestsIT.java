package it.disco.unimib.ewshopp.key2cluster;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.disco.unimib.ewshopp.key2cluster.service.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import lombok.extern.java.Log;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.profiles.active=REDIS",
        "spring.redis.host=localhost",
        "spring.redis.password=yourpassword",
        "spring.redis.port=6379", "keyword.cluster.recreate-data-structure=true"})
@Log
public class Key2ClusterApplicationTestsIT {

    @Autowired
    private IDataManager dataManager;


    @LocalServerPort
    private int port;
    private String baseURI;

    @Before
    public void setupForTest() {

        RestAssured.port = port;
        System.out.println(RestAssured.port);
        baseURI = "http://localhost"; // replace as appropriate
    }

    @ClassRule
    public static DockerComposeContainer compose =
            new DockerComposeContainer(
                    new File("src/test/docker/docker-compose.yml")).withLocalCompose(true);
    //.withExposedService("redis_1", 6379,  Wait.defaultWaitStrategy());


    @Test
    public void getCategoriesPerKeywordUTF8() {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseURI)
                .param("kws", "+bauernhofe +in +niedersachsen")
                .get("key2cluster/api/keywords");

        response.getBody().prettyPrint();
        KeywordCategories[] kc = response.then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(KeywordCategories[].class);
        assertThat(kc[0].getCategories().size(), Matchers.is(0));

    }

    @Test
    public void testCountkeywords() {
        Assert.isTrue(dataManager.countKeywords() == 999, "number of keywords is 999");
    }

    @Test
    public void getCategoriesPerKeyword() {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseURI)
                .pathParam("key", "11 28 cassette")
                .get("key2cluster/api/keyword/{key}");

        response.getBody().prettyPrint();
        KeywordCategories kc = response.then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(KeywordCategories.class);
        assertThat(kc.getCategories().size(), Matchers.is(1));
        assertThat(kc.getCategories(), Matchers.contains("/Ocasiones y regalos/Vacaciones y eventos estacionales/Halloween y 31 de octubre(13740)"));

    }
}
