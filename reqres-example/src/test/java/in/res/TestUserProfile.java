package in.res;

import in.res.client.UserClient;
import in.res.dto.request.UserProfileRequest;
import in.res.dto.response.UserProfileResponse;
import in.res.util.UserProfileGenerator;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static in.res.config.Specification.responseSpecification;
import static in.res.config.Specification.responseSpecificationWithContent;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUserProfile {
    private final UserClient userClient = new UserClient();
    private final UserProfileGenerator generator = new UserProfileGenerator();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Test
    @Owner("Katsiaryna")
    @Tag("User profile")
    public void testAddNewUser() {
        UserProfileRequest user = generator.generateRandomUserProfile();

        UserProfileResponse response = userClient
                .createUser(user)
                .then()
                .spec(responseSpecificationWithContent(201, "schemas/UserCreate.json"))
                .log()
                .body()
                .extract()
                .as(UserProfileResponse.class);

        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        assertAll(
                "Проверка добавления нового профиля",
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

        UserProfileResponse response = userClient
                .updateUser(2L, user)
                .then()
                .spec(responseSpecificationWithContent(200, "schemas/UserUpdate.json"))
                .log()
                .body()
                .extract()
                .as(UserProfileResponse.class);

        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        assertAll(
                "Проверка обновления существующего профиля",
                () -> assertEquals(user.job(), response.job()),
                () -> assertEquals(user.name(), response.name()),
                () -> assertEquals(dateTime.format(formatter), response.updatedAt().substring(0, 16))
        );
    }

    @Test
    @DisplayName("Удаление профиля пользователя")
    @Owner("Katsiaryna")
    @Tag("User profile")
    public void testDeleteUserProfile() {
        userClient
                .deleteUser()
                .then()
                .spec(responseSpecification(204));
    }
}
