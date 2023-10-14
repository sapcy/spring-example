package example.actuator.health;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final MutableHealthIndicator indicator;

    public HealthController(MutableHealthIndicator indicator) {
        this.indicator = indicator;
    }

    @GetMapping
    public ResponseEntity health() {
        Health health = indicator.health();
        boolean isUp = health.getStatus().equals(Status.UP);
        return ResponseEntity
                .status(isUp ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
                .build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void down(HttpServletRequest request) {
        indicator.setHealth(Health.down().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void up(HttpServletRequest request) {
        indicator.setHealth(Health.up().build());
    }

}
