package penlk.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import penlk.business.contracts.OrderStockService;
import penlk.business.internal.OrderStockFactoryImpl;

@Controller
@AllArgsConstructor
public class KafkaController {
    private final OrderStockService orderStockService;
    private final KafkaIncomingProperties incomingProperties;

    @KafkaListener(topics = "${kafka.topics.input}")
    public void consume(String event) {
        ObjectMapper mapper = new ObjectMapper();

        KafkaEvent kafkaEvent;

        try {
            kafkaEvent = mapper.readValue(event, KafkaEvent.class);
        } catch (Exception e) {
            System.out.println("Invalid event: " + event);
            return;
        }

        if (!kafkaEvent.name().equals(incomingProperties.getCreatingEvent())) {
            return;
        }

        orderStockService.create(new OrderStockFactoryImpl(kafkaEvent));
    }
}
