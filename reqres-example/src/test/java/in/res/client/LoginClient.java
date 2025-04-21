package in.res.client;

import in.res.client.base.AbstractApiClient;
import in.res.dto.request.AuthRequest;
import io.restassured.response.Response;

import static in.res.util.ApiEndpoints.LOGIN;

public class LoginClient extends AbstractApiClient {
    public Response login(AuthRequest request) {
        return post(LOGIN, request);
    }
}
