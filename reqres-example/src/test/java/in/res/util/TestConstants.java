package in.res.util;

import in.res.dto.request.AuthRequest;
import in.res.dto.response.AuthResponse;
import in.res.dto.response.ResourceResponse;

public final class TestConstants {
    public static final String DEFAULT_TOKEN = "QpwL5tke4Pnpja7X4";
    public static AuthResponse SUCCESS_AUTH_RESPONSE = new AuthResponse(4, DEFAULT_TOKEN);
    public static AuthRequest SUCCESS_AUTH_REQUEST = new AuthRequest("eve.holt@reqres.in", "pistol");
    public static AuthRequest SUCCESS_LOGIN_REQUEST = new AuthRequest("eve.holt@reqres.in", "cityslicka");
    public static AuthRequest ERROR_AUTH_REQUEST = new AuthRequest("sydney@fife", "");
    public static ResourceResponse DEFAULT_RESOURCE_RESPONSE = new ResourceResponse(2, "fuchsia rose", 2001, "#C74375", "17-2031");
}
