package in.res.util;

import in.res.dto.request.UserProfileRequest;
import in.res.dto.response.UserProfileResponse;
import org.assertj.core.api.SoftAssertions;

import static in.res.helper.DateFormatHelper.formatRequestToDateTime;
import static in.res.helper.DateFormatHelper.getCurrentDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class UserValidator {
    public static void validateModifyingProfile(UserProfileResponse response, UserProfileRequest user){
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(response.job()).isEqualTo(user.job());
            assertThat(response.name()).isEqualTo(user.name());
            assertThat(formatRequestToDateTime(response.updatedAt())).isEqualTo(getCurrentDateTime());
        });
    }

    public static void validateCreatingProfile(UserProfileResponse response, UserProfileRequest user){
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(response.job()).isEqualTo(user.job());
            assertThat(response.name()).isEqualTo(user.name());
            assertThat(formatRequestToDateTime(response.createdAt())).isEqualTo(getCurrentDateTime());
        });
    }
}
