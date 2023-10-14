package example.kafka.producer.app;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SampleConsumer {
    private final static Logger logger = LoggerFactory.getLogger(SampleConsumer.class);

    private final static String BOOTSTRAP_SERVER = "localhost:9092";
    private final static String TOPIC = "sample-topic";

    private final static Properties configs = new Properties();

    public SampleConsumer() {
        /**
         * 프로듀서의 인스턴스에 사용할 '필수 옵션'을 설정한다.
         * [참고] 선언하지 않은 '선택 옵션'은 기본 옵션값으로 설정되어 동작한다.
         */
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);

        /**
         * 메시지 키, 값을 직렬화 하기 위해 StringSerializer를 사용한다.
         * StringSerializer는 String을 직렬화하는 카프카의 라이브러리이다.
         * (org.apache.kafka.common.serialization)
         */
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, TOPIC);
    }

    public void poll() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Collections.singletonList(TOPIC));

        String message = null;
        try {
            do {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100000));

                for (ConsumerRecord<String, String> record : records) {
                    message = record.value();
                    System.out.println(message);
                }
            } while (!message.startsWith("exit"));
        } catch(Exception e) {
            // exception
        } finally {
            consumer.close();
        }
    }

}
