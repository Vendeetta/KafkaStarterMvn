package ru.astondevs.kafkastartermvn.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import ru.astondevs.kafkastartermvn.properties.KafkaTopicProperties;

import java.io.IOException;

@Component
@AllArgsConstructor
@Setter
public class CustomDeserializer<T> extends JsonDeserializer<T> {

    private KafkaTopicProperties kafkaTopicProperties;
    private ObjectMapper mapper;

    public CustomDeserializer() {
        this.kafkaTopicProperties = new KafkaTopicProperties();
        this.mapper = new ObjectMapper();
    }
    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        T deserializer;
        try {
            deserializer = (T) mapper.readValue(data, kafkaTopicProperties.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deserializer;
    }


}
