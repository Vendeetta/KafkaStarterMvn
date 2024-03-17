package ru.astondevs.kafkastartermvn.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(KafkaTopicProperties.BASE_PREFIX + ".kafka")
public class KafkaTopicProperties {

    public final static String BASE_PREFIX = "ru.astondevs.app";

    private String topic;
    private String groupId = "groupId";
    private String bootstrapServers = "localhost:9092";
    private Class<?> type;
}
