package penlk;

import org.apache.commons.lang3.SerializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Bean
    public DefaultErrorHandler errorHandler() {

        var handler = new DefaultErrorHandler(
                (record, ex) -> {
                    System.out.println("SKIPPED BAD MESSAGE: " + record.value());
                },
                new FixedBackOff(0L, 0)
        );

        handler.addNotRetryableExceptions(SerializationException.class);

        return handler;
    }
}