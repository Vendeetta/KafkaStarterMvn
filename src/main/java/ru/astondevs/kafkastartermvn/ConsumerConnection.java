package ru.astondevs.kafkastartermvn;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.astondevs.kafkastartermvn.listener.Listener;
import ru.astondevs.kafkastartermvn.properties.KafkaProperties;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public class ConsumerConnection {

    private final Consumer<String, String> consumer;
    private final KafkaProperties properties;
    private final Listener<String> listener;

    @EventListener(ApplicationReadyEvent.class)
    public void connect() {
        consumer.subscribe(List.of(properties.getTopic()));
        while (true) {
            var resultSet = new LinkedHashSet<String>();
            ConsumerRecords<String, String> records = consumer.poll(Duration.ZERO);
            records.forEach(record -> resultSet.add(record.value()));
            if (!resultSet.isEmpty())
                listener.receive(resultSet);
        }
    }
}
