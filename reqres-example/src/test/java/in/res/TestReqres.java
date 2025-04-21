package in.res;

import in.res.dto.response.UserResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

import static in.res.config.Specification.requestSpecification;
import static in.res.config.Specification.responseSpecification;
import static io.restassured.RestAssured.given;

public class TestReqres {
    @Test(description = "Проверка о совпадении аватаров и имен файлов, проверка формата email")
    @Description(value = "Данный тест выполняет GET запрос второй страницы списка пользоваталей")
    @Owner("Katsiaryna")
    public void checkUsers() {
        List<UserResponse> users = given()
                .spec(requestSpecification)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpecification(200))
                .extract()
                .body()
                .jsonPath()
                .getList("data", UserResponse.class);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(users.stream().allMatch(user -> user.email().endsWith("reqres.in")))
                    .as("Проверка аписания почты с окончанием на reqres.in")
                    .isTrue();
            softAssertions.assertThat(users.stream().allMatch(user -> user.avatar().contains(String.valueOf(user.id()))))
                    .as("Првоерка содержания аватарки пользователя ссылки на id")
                    .isTrue();
        });
    }
}
