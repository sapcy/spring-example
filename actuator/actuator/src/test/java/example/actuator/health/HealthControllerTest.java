package example.actuator.health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerTest {

    private static final String HEALTH_CONTROLLER_ENDPOINT = "/health";
    private static final String HEALTH = "/actuator/health";

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void downAndUp() throws InterruptedException {
        // before
        expectUrlStatus(HEALTH_CONTROLLER_ENDPOINT, HttpStatus.OK);
        expectUrlStatus(HEALTH, HttpStatus.OK);

        // down
        restTemplate.delete(HEALTH_CONTROLLER_ENDPOINT);
        // then down
        TimeUnit.MILLISECONDS.sleep(1000);
        expectUrlStatus(HEALTH_CONTROLLER_ENDPOINT, HttpStatus.SERVICE_UNAVAILABLE);
        expectUrlStatus(HEALTH, HttpStatus.SERVICE_UNAVAILABLE);

        // up
        restTemplate.postForEntity(HEALTH_CONTROLLER_ENDPOINT, null, Object.class);
        // then up
        TimeUnit.MILLISECONDS.sleep(1000);
        expectUrlStatus(HEALTH_CONTROLLER_ENDPOINT, HttpStatus.OK);
        expectUrlStatus(HEALTH, HttpStatus.OK);
    }

    private void expectUrlStatus(String url, HttpStatus status) {
        ResponseEntity<Object> res = restTemplate.getForEntity(url, Object.class);
        assertThat(res.getStatusCode(), is(status));
    }
}