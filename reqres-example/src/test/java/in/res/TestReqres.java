package in.res;

import in.res.dto.response.UserResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static in.res.config.Specification.requestSpecification;
import static in.res.config.Specification.responseSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestReqres {
    @Test
    @DisplayName("Проверка о совпадении автаров и имен файлов, проверка формата email")
    @Description(value = "Данный тест выполняет GET запрос второй страницы списка пользоваталей")
    @Owner("Katsiaryna")
    public void checkUsers() {
        List<UserResponse> users = given()
                .spec(requestSpecification)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(responseSpecification(200))
                .extract()
                .body()
                .jsonPath()
                .getList("data", UserResponse.class);
        assertAll(
                "",
                () -> assertTrue(users.stream().allMatch(user -> user.email().endsWith("reqres.in"))),
                () -> {
                    for (UserResponse user : users) {
                        assertTrue(user.avatar().contains(String.valueOf(user.id())));
                    }
                }
        );
    }
}
