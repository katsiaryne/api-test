package in.res;

import in.res.dto.request.UserProfileRequest;
import in.res.dto.response.UserProfileResponse;
import in.res.util.UserProfileGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static in.res.config.Specification.requestSpecification;
import static in.res.config.Specification.responseSpecificationWithContent;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUserProfile {
    private final UserProfileGenerator generator = new UserProfileGenerator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Test
    @Owner("Katsiaryna")
    @Tag("User profile")
    public void testAddNewUser() {
        UserProfileRequest user = generator.generateRandomUserProfile();

        UserProfileResponse response = given()
                .spec(requestSpecification)
                .body(user)
                .when()
                .post("/api/users")
                .then()
                .spec(responseSpecificationWithContent(201))
                .log()
                .body()
                .extract()
                .as(UserProfileResponse.class);

        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        assertAll(
                "Проверка добавления нового профиля",
                () -> assertNotNull(response),
                () -> assertNotNull(response.id()),
                () -> assertNotNull(response.createdAt()),
                () -> assertNotNull(response.name()),
                () -> assertNotNull(response.job()),
                () -> assertEquals(user.job(), response.job()),
                () -> assertEquals(user.name(), response.name()),
                () -> assertEquals(dateTime.format(formatter), response.createdAt().substring(0, 16))
        );
    }

    @Test
    @Owner("Katsiaryna")
    @Tag("User profile")
    public void testUpdateUser() {
        UserProfileRequest user = generator.generateRandomUserProfile();

        UserProfileResponse response = given()
                .spec(requestSpecification)
                .body(user)
                .when()
                .put("/api/users/2")
                .then()
                .spec(responseSpecificationWithContent(200))
                .log()
                .body()
                .extract()
                .as(UserProfileResponse.class);

        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        assertAll(
                "Првоерка обновления сущетсвующег профиля",
                () -> assertNotNull(response),
                () -> assertNotNull(response.updatedAt()),
                () -> assertNotNull(response.name()),
                () -> assertNotNull(response.job()),
                () -> assertEquals(user.job(), response.job()),
                () -> assertEquals(user.name(), response.name()),
                () -> assertEquals(dateTime.format(formatter), response.updatedAt().substring(0, 16))
        );
    }
}
