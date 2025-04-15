package in.res.client;

import in.res.client.base.AbstractApiClient;
import in.res.dto.request.UserProfileRequest;
import io.restassured.response.Response;

import static in.res.util.ApiEndpoints.USER_PROFILE;
import static in.res.util.ApiEndpoints.USER_PROFILE_BY_ID;

public class UserClient extends AbstractApiClient {
    public Response deleteUser() {
        return delete(USER_PROFILE);
    }

    public Response updateUser(Long id, UserProfileRequest request) {
        return put(USER_PROFILE_BY_ID, id, request);
    }

    public Response createUser(UserProfileRequest request) {
        return post(USER_PROFILE, request);
    }
}
