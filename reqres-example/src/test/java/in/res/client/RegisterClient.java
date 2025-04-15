package in.res.client;

import in.res.client.base.AbstractApiClient;
import in.res.dto.request.AuthRequest;
import io.restassured.response.Response;

import static in.res.util.ApiEndpoints.REGISTER;

public class RegisterClient extends AbstractApiClient {
    public Response register(AuthRequest request) {
        return post(REGISTER, request);
    }
}
