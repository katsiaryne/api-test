package in.res.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThanOrEqualTo;

public final class Specification {
    public static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("https://reqres.in/")
            .build();

    public static ResponseSpecification responseSpecificationWithContent(int expectedStatusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .expectContentType(ContentType.JSON)
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
