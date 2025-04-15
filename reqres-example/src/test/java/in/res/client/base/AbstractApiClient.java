package in.res.client.base;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static in.res.config.Specification.requestSpecification;
import static io.restassured.RestAssured.given;

public abstract class AbstractApiClient {
    protected void validate(ValidatableResponse response) {

    }

    protected Response post(String path, Object body) {
        return given()
                .spec(requestSpecification)
                .body(body)
                .post(path);
    }

    protected Response get(String path) {
        return given()
                .spec(requestSpecification)
                .get(path);
    }

    protected Response getById(String path, Long id) {
        return given()
                .spec(requestSpecification)
                .get(path, id);
    }

    protected Response delete(String path) {
        return given()
                .delete(path);
    }

    protected Response put(String path, Long id, Object body) {
        return given()
                .spec(requestSpecification)
                .body(body)
                .when()
                .put(path, id);
    }
}
