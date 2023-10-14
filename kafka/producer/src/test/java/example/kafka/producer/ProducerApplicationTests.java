package example.kafka.producer;

import example.kafka.producer.app.SampleProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerApplicationTests {

    private static final SampleProducer sampleProducer = new SampleProducer();
    @Test
    void send() {
        sampleProducer.send("key1", "hello, I'm kafka sample producer!!");
        sampleProducer.send("key2", "hello, I'm kafka sample producer!!");
        sampleProducer.send("key2", "hello, I'm kafka sample producer!!");
        sampleProducer.send("key3", "hello, I'm kafka sample producer!!");
        sampleProducer.send("key3", "hello, I'm kafka sample producer!!");
        sampleProducer.send("key3", "hello, I'm kafka sample producer!!");
    }

    @Test
    void exitConsumer() {
        sampleProducer.send("key1", "exit");
    }
}
