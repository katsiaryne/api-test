package in.res;

import in.res.client.ResourceClient;
import in.res.dto.response.ResourceResponse;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static in.res.config.Specification.responseSpecification;
import static in.res.config.Specification.responseSpecificationWithContent;
import static in.res.util.TestConstants.DEFAULT_RESOURCE_RESPONSE;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestResource {
    private final ResourceClient resourceClient = new ResourceClient();

    @Test
    @DisplayName("Проверка порядка ресурсов по возрастанию года")
    @Owner("Katsiaryna")
    @Severity(NORMAL)
    @Tag("Resources")
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
        assertAll(
                "",
                () -> assertEquals(expectedYears, actualYears)
        );
    }

    @Test
    @Tag("Resources")
    @Owner("Katsiaryna")
    @DisplayName("Проверка получения ресурса")
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
        assertAll(
                "",
                () -> assertEquals(DEFAULT_RESOURCE_RESPONSE, resource)
        );
    }

    @Test
    @Tag("Resources")
    @Owner("Katsiaryna")
    @DisplayName("Проверка получения несуществующего ресурса")
    public void testGetSingleResourceError() {
        resourceClient
                .getResource(23L)
                .then()
                .spec(responseSpecification(404));
    }
}
