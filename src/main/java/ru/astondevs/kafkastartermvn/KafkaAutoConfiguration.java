package ru.astondevs.kafkastartermvn;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import ru.astondevs.kafkastartermvn.properties.KafkaTopicProperties;
import ru.astondevs.kafkastartermvn.recieve.ConsumerFactoryBuilder;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackageClasses = KafkaAutoConfiguration.class)
@EnableConfigurationProperties(KafkaTopicProperties.class)
@AutoConfigureAfter(KafkaProperties.class)
public class KafkaAutoConfiguration {

    private final KafkaTopicProperties properties;
    private final ConsumerFactoryBuilder builder;


    @Bean
    @ConditionalOnMissingBean
    public Consumer<?, ?> consumerFactory() {
        ConsumerFactory<?, ?> consumerFactory = builder.buildConsumerFactory();
        return consumerFactory.createConsumer();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate(
//            ProducerFactory<String, String> producerFactory
//    ) {
//        return new KafkaTemplate<>(producerFactory);
//    }
}
