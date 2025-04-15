package in.res;

import in.res.client.LoginClient;
import in.res.client.RegisterClient;
import in.res.dto.request.AuthRequest;
import in.res.dto.response.AuthResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static in.res.config.Specification.responseSpecificationWithContent;
import static in.res.util.TestConstants.DEFAULT_TOKEN;
import static in.res.util.TestConstants.SUCCESS_AUTH_REQUEST;
import static in.res.util.TestConstants.SUCCESS_AUTH_RESPONSE;
import static in.res.util.TestConstants.SUCCESS_LOGIN_REQUEST;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAuth {
    private final RegisterClient registerClient = new RegisterClient();
    private final LoginClient loginClient = new LoginClient();

    @Test
    @Tag("Register")
    @DisplayName("Проверка успешной регистрации")
    public void testRegister() {
        AuthResponse response = registerClient
                .register(SUCCESS_AUTH_REQUEST)
                .then()
                .spec(responseSpecificationWithContent(200, "schemas/RegisterSuccess.json"))
                .log()
                .all()
                .extract()
                .body()
                .as(AuthResponse.class);
        assertAll(
                "Проверка тела ответа при регистрации",
                () -> assertEquals(SUCCESS_AUTH_RESPONSE, response)
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'', '', 'Missing email or username'",
            "'email@email.com', '', 'Missing password'"
    })
    @Tag("Register")
    @DisplayName("Проверка НЕуспешной регистрации. Отсутствие тела запроса")
    public void testUnsuccessfulRegister(String email, String password, String errorMessage) {
        Response response = registerClient
                .register(new AuthRequest(email, password))
                .then()
                .log()
                .all()
                .spec(responseSpecificationWithContent(400, "schemas/Error.json"))
                .extract()
                .response();
        assertAll(
                "Проверка ошибки несупешной регистрации",
                () -> assertEquals(errorMessage, response.jsonPath().getString("error"))
        );
    }

    @Test
    @Tag("Login")
    @DisplayName("Проверка успешного входа")
    public void testLogin() {
        Response response = loginClient
                .login(SUCCESS_LOGIN_REQUEST)
                .then()
                .log()
                .body()
                .spec(responseSpecificationWithContent(200, "schemas/LoginSuccess.json"))
                .extract()
                .response();
        assertAll(
                "Проверка тела ответа при входе",
                () -> assertEquals(DEFAULT_TOKEN, response.jsonPath().getString("token"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'', '', 'Missing email or username'",
            "'email@email.com', '', 'Missing password'"
    })
    @Tag("Login")
    @DisplayName("Проверка НЕуспешного входа")
    public void testLoginError(String email, String password, String errorMessage) {
        Response response = loginClient
                .login(new AuthRequest(email, password))
                .then()
                .log()
                .body()
                .spec(responseSpecificationWithContent(400, "schemas/Error.json"))
                .extract()
                .response();
        assertAll(
                "Проверка ошибки неуспешного входа",
                () -> assertEquals(errorMessage, response.jsonPath().getString("error"))
        );
    }
}
