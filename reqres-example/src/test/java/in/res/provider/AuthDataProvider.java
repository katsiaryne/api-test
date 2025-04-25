package in.res.provider;

import org.testng.annotations.DataProvider;

public class AuthDataProvider {
    @DataProvider(name = "error_auth")
    public Object[][] getWrongAuthData() {
        return new Object[][]{
                {"", "", "Missing email or username"},
                {"email@email.com", "", "Missing password"}
        };
    }
}
