package penlk.business.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka.topics.output")
public class KafkaConstants {
    private String storageEvent;

    public String getApprovedEvent() {
        return "OrderApproved";
    }

    public String getRejectedEvent() {
        return "OrderRejected";
    }
}
