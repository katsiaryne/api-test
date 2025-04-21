package in.res;

import in.res.client.LoginClient;
import in.res.client.RegisterClient;
import in.res.dto.request.AuthRequest;
import in.res.dto.response.AuthResponse;
import in.res.provider.AuthDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static in.res.config.Specification.responseSpecificationWithContent;
import static in.res.util.TestConstants.DEFAULT_TOKEN;
import static in.res.util.TestConstants.SUCCESS_AUTH_REQUEST;
import static in.res.util.TestConstants.SUCCESS_AUTH_RESPONSE;
import static in.res.util.TestConstants.SUCCESS_LOGIN_REQUEST;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;

public class TestAuth {
    private final RegisterClient registerClient = new RegisterClient();
    private final LoginClient loginClient = new LoginClient();

    @Test(description = "Проверка успешной регистрации")
    @Description(value = "Данный тест выполняет регистрацию пользователя")
    @Owner("Katsiaryna")
    @Severity(CRITICAL)
    @Story("Authentication")
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
        assertThat(response)
                .as("Проверка тела ответа при регистрации")
                .isEqualTo(SUCCESS_AUTH_RESPONSE);
    }

    @Test(dataProviderClass = AuthDataProvider.class, dataProvider = "error_auth")
    @Owner("Katsiaryna")
    @Severity(CRITICAL)
    @Story("Wrong Authentication")
    public void testUnsuccessfulRegister(String email, String password, String errorMessage) {
        Response response = registerClient
                .register(new AuthRequest(email, password))
                .then()
                .log()
                .all()
                .spec(responseSpecificationWithContent(400, "schemas/Error.json"))
                .extract()
                .response();
        assertThat(response.jsonPath().getString("error"))
                .as("Проверка ошибки несупешной регистрации")
                .isEqualTo(errorMessage);
    }

    @Test
    public void testLogin() {
        Response response = loginClient
                .login(SUCCESS_LOGIN_REQUEST)
                .then()
                .log()
                .body()
                .spec(responseSpecificationWithContent(200, "schemas/LoginSuccess.json"))
                .extract()
                .response();
        assertThat(response.jsonPath().getString("token"))
                .as("Проверка тела ответа при входе")
                .isEqualTo(DEFAULT_TOKEN);
    }

    @Test(dataProviderClass = AuthDataProvider.class, dataProvider = "error_auth")
    public void testLoginError(String email, String password, String errorMessage) {
        Response response = loginClient
                .login(new AuthRequest(email, password))
                .then()
                .log()
                .body()
                .spec(responseSpecificationWithContent(400, "schemas/Error.json"))
                .extract()
                .response();
        assertThat(response.jsonPath().getString("error"))
                .as("Проверка ошибки неуспешного входа")
                .isEqualTo(errorMessage);
    }
}
