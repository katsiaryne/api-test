package in.res;

import in.res.client.ResourceClient;
import in.res.dto.response.ResourceResponse;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.annotations.Test;

import java.util.List;

import static in.res.config.Specification.responseSpecification;
import static in.res.config.Specification.responseSpecificationWithContent;
import static in.res.util.TestConstants.DEFAULT_RESOURCE_RESPONSE;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

public class TestResource {
    private final ResourceClient resourceClient = new ResourceClient();

    @Test
    @Owner("Katsiaryna")
    @Severity(NORMAL)
    public void checkListResource() {
        List<ResourceResponse> resources = resourceClient
                .getAll()
                .then()
                .spec(responseSpecificationWithContent(200, "schemas/ResourceList.json"))
                .extract()
                .body()
                .jsonPath()
                .getList("data", ResourceResponse.class);

        List<Integer> actualYears = resources.stream().map(ResourceResponse::year).toList();
        List<Integer> expectedYears = actualYears.stream().sorted().toList();

        assertThat(actualYears).isEqualTo(expectedYears);
    }

    @Test
    @Owner("Katsiaryna")
    public void testGetSingleResource() {
        ResourceResponse resource = resourceClient
                .getResource(2L)
                .then()
                .log()
                .body()
                .spec(responseSpecificationWithContent(200, "schemas/Resource.json"))
                .extract()
                .body()
                .jsonPath()
                .getObject("data", ResourceResponse.class);

        assertThat(resource).isEqualTo(DEFAULT_RESOURCE_RESPONSE);
    }

    @Test
    @Owner("Katsiaryna")
    public void testGetSingleResourceError() {
        resourceClient
                .getResource(23L)
                .then()
                .spec(responseSpecification(404));
    }
}
