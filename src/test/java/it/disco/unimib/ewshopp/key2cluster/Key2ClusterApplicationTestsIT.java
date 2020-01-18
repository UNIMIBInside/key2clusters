package it.disco.unimib.ewshopp.key2cluster;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import it.disco.unimib.ewshopp.key2cluster.components.IDataManager;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.profiles.active=REDIS",
        "spring.redis.host=localhost",
        "spring.redis.password=yourpassword",
        "spring.redis.port=6379", "keyword.cluster.recreate-data-structure=false"})
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
                    new File("src/test/docker/docker-compose.yml"))
                    .withExposedService("redis_1", 6379,  Wait.defaultWaitStrategy());



    @Test
    public void getCategoriesPerKeyword() {
        System.out.println("number of keywords: " +dataManager.countKeywords());
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseURI)
                .pathParam("key", "11 28 cassette")
                .get("/api/keyword/{key}");

        response.getBody().prettyPrint();
    }
}
