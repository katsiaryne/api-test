package in.res.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public final class Specification {
    public static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("https://reqres.in/api")
            .build();

    public static ResponseSpecification responseSpecificationWithContent(int expectedStatusCode, String schema){
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .expectContentType(ContentType.JSON)
                .expectBody(matchesJsonSchemaInClasspath(schema))
                .expectResponseTime(lessThanOrEqualTo(3000L))
                .build();
    }

    public static ResponseSpecification responseSpecification(int expectedStatusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .expectResponseTime(lessThanOrEqualTo(3000L))
                .build();
    }
}
