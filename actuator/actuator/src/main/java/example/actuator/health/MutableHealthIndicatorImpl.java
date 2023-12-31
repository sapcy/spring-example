package example.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class MutableHealthIndicatorImpl implements MutableHealthIndicator {

    private final AtomicReference<Health> healthRef = new AtomicReference<>(Health.up().build());

    @Override
    public Health health() {
        return healthRef.get();
    }

    @Override
    public void setHealth(Health health) {
        healthRef.set(health);
    }
}
