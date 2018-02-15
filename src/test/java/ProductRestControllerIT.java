import de.bringmeister.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Application.class)
public class ProductRestControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testRetrieveProducts() {

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products"),
                HttpMethod.GET, entity, String.class);

        assertThat(response.getBody(),hasJsonPath("$[*]", hasSize(2)));
        assertThat(response.getBody(), hasJsonPath("$[*].name", hasItem("Tomato")));
    }

    @Test
    public void testRetrieveProductDetail() {

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());

        String productId = "b867525e-53f8-4864-8990-5f13a5dd9d14";

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/products/" + productId),
                HttpMethod.GET, entity, String.class);

        assertThat(response.getBody(), hasJsonPath("$.name", equalTo("Tomato")));
        assertThat(response.getBody(), hasJsonPath("$.productPrices[*].unit", hasItems("package","piece")));
    }

    @Test
    public void testRetrieveProductUnitPrice() {

        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());

        String productId = "b867525e-53f8-4864-8990-5f13a5dd9d14";
        String unit = "package";

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(String.format("/products/%s/prices/?unit=%s" ,productId , unit)),
                HttpMethod.GET, entity, String.class);

        assertThat(response.getBody(), hasJsonPath("$[*].currency", hasItem("EUR")));
        assertThat(response.getBody(), hasJsonPath("$[*].value", hasItem(32.18)));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));

        return headers;
    }


}