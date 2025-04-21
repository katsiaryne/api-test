package in.res.util;

import com.github.javafaker.Faker;
import in.res.dto.request.UserProfileRequest;

public class UserProfileGenerator {
    private final Faker faker = new Faker();

    public UserProfileRequest generateRandomUserProfile() {
        return new UserProfileRequest(
                faker.name().name(),
                faker.job().seniority()
        );
    }
}
