package in.res;

import in.res.client.UserClient;
import in.res.dto.request.UserProfileRequest;
import in.res.dto.response.UserProfileResponse;
import in.res.retry.RetryFailedTest;
import in.res.util.UserProfileGenerator;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.annotations.Test;

import static in.res.config.Specification.responseSpecification;
import static in.res.config.Specification.responseSpecificationWithContent;
import static in.res.util.UserValidator.validateModifyingProfile;
import static io.qameta.allure.SeverityLevel.NORMAL;

public class TestUserProfile {
    private final UserClient userClient = new UserClient();
    private final UserProfileGenerator generator = new UserProfileGenerator();

    @Test(retryAnalyzer = RetryFailedTest.class)
    @Owner("Katsiaryna")
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

        validateModifyingProfile(response, user);
    }

    @Test(retryAnalyzer = RetryFailedTest.class)
    @Owner("Katsiaryna")
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
        validateModifyingProfile(response, user);
    }

    @Test(description = "Проверка удаления профиля")
    @Owner("Katsiaryna")
    @Severity(NORMAL)
    public void testDeleteUserProfile() {
        userClient
                .deleteUser()
                .then()
                .spec(responseSpecification(204));
    }
}
