package ru.astondevs.kafkastartermvn.recieve;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;
import ru.astondevs.kafkastartermvn.deserializer.CustomDeserializer;
import ru.astondevs.kafkastartermvn.properties.KafkaTopicProperties;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ConsumerFactoryBuilder {

    private final KafkaProperties kafkaProperties;
    private final KafkaTopicProperties kafkaTopicProperties;

    public ConsumerFactory<?, ?> buildConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaTopicProperties.getGroupId());
        Deserializer<?> keyDeserializer;
        Deserializer<?> valueDeserializer;
        try {
            keyDeserializer = (Deserializer<?>) kafkaProperties.getConsumer().getKeyDeserializer().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            keyDeserializer = new StringDeserializer();
        }
        try {
            valueDeserializer = (Deserializer<?>) kafkaProperties.getConsumer().getValueDeserializer().newInstance();
            if (valueDeserializer instanceof CustomDeserializer) {
                ((CustomDeserializer<?>) valueDeserializer).setKafkaTopicProperties(kafkaTopicProperties);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            keyDeserializer = new StringDeserializer();
            valueDeserializer = new StringDeserializer();
        }
        return new DefaultKafkaConsumerFactory<>(config, keyDeserializer, valueDeserializer);
    }

}
