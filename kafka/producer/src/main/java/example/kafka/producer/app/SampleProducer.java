package example.kafka.producer.app;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

// e.g. https://velog.io/@jwpark06/Kafka-Producer-Application-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
public class SampleProducer {
    private final static Logger logger = LoggerFactory.getLogger(SampleProducer.class);

    private final static String BOOTSTRAP_SERVER = "localhost:9092";
    private final static String TOPIC = "sample-topic";

    private final static Properties configs = new Properties();

    public SampleProducer() {
        /**
         * 프로듀서의 인스턴스에 사용할 '필수 옵션'을 설정한다.
         * [참고] 선언하지 않은 '선택 옵션'은 기본 옵션값으로 설정되어 동작한다.
         */
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);

        /**
         * 메시지 키, 값을 직렬화 하기 위해 StringSerializer를 사용한다.
         * StringSerializer는 String을 직렬화하는 카프카의 라이브러리이다.
         * (org.apache.kafka.common.serialization)
         */
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }

    public void send(String key, String message) {
        /**
         * 프로듀서 인스턴스를 생성하며, 위에서 설정한 설정을 파라미터로 사용한다.
         */
        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        /**
         * 전달할 메시지 값을 생성한다.
         * (여기서는 애플리케이션 실행 시점의 날짜와 시간을 조합하여서 메시지 값으로 생성한다.)
         */
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String messageValue = message + " [" + dateFormat.format(today) + "]";

        /**
         * 레코드를 생성하고 전달한다.
         * 이때, 레코드를 전달할 토픽과 레코드의 메시지 값을 지정한다.
         */
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, key, messageValue);
        producer.send(record);

        logger.info("{}", record);

        /**
         * 애플리케이션을 안전하게 종료한다.
         */
        producer.flush();
        producer.close();
    }

}
