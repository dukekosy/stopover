package ca.northland.stopover;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
class StopoverApplicationTests {
// comment
  @LocalServerPort private int port;

  @Value("${local.management.port}")
  private int mgt;

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void shouldReturn200WhenSendingRequestToController() {

    String url = "http://localhost:";
    URI uri =
        UriComponentsBuilder.fromHttpUrl(url)
            .path("/airport/hotels")
            .port(this.port)
            .queryParam("cityCode", "MUC")
            .queryParam("checkInDate", "20.09.2020")
            .queryParam("checkOutDate", "21.09.2020")
            .build()
            .toUri();

    ResponseEntity entity = testRestTemplate.getForEntity(uri, Set.class);

    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void shouldReturn200WhenSendingRequestToManagementEndpoint() {
    @SuppressWarnings("rawtypes")
    ResponseEntity<Map> entity =
        this.testRestTemplate.getForEntity(
            "http://localhost:" + this.mgt + "/actuator/info", Map.class);

    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
