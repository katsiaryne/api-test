package in.res.client.base;

import io.restassured.response.Response;

import static in.res.config.Specification.requestSpecification;
import static in.res.util.ApiEndpoints.PATH_PARAM_ID;
import static io.restassured.RestAssured.given;

public abstract class AbstractApiClient {
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
                .pathParam(PATH_PARAM_ID, id)
                .get(path);
    }

    protected Response delete(String path) {
        return given()
                .delete(path);
    }

    protected Response put(String path, Long id, Object body) {
        return given()
                .spec(requestSpecification)
                .body(body)
                .pathParam(PATH_PARAM_ID, id)
                .put(path);
    }
}
