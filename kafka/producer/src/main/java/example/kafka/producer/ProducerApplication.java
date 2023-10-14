package example.kafka.producer;

import example.kafka.producer.app.SampleConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
        SampleConsumer consumer = new SampleConsumer();
        consumer.poll();
    }

}
