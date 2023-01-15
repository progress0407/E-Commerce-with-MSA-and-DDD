package codereview.simpleorder.support;

import codereview.simpleorder.repository.command.ClothesRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractAcceptanceTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected ClothesRepository clothesRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> get(String uri) {
        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .when()
                .get(uri)
                .then().log().all().extract();
    }

    protected <T> ExtractableResponse<Response> post(String uri, T requestBody) {
        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when()
                .post(uri)
                .then().log().all().extract();
    }

    protected <T> Executable assertEquality(T actual, T expected) {
        return () -> assertThat(actual).isEqualTo(expected);
    }

    protected <T> Executable assertNotNull(T actual) {
        return () -> assertThat(actual).isNotNull();
    }
}
