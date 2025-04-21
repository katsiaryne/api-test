package in.res.client;

import in.res.client.base.AbstractApiClient;
import io.restassured.response.Response;

import static in.res.util.ApiEndpoints.RESOURCE;
import static in.res.util.ApiEndpoints.RESOURCE_BY_ID;

public class ResourceClient extends AbstractApiClient {
    public Response getAll() {
        return get(RESOURCE);
    }

    public Response getResource(Long id) {
        return getById(RESOURCE_BY_ID, id);
    }
}
