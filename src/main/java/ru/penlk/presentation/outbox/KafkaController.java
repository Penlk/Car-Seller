package ru.penlk.presentation.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.penlk.business.contracts.orders.CommonOrderService;
import ru.penlk.business.contracts.orders.SpecialOrderService;
import ru.penlk.presentation.outbox.models.InboxEvent;

@Controller
public class KafkaController {
    private final CommonOrderService commonOrderService;
    private final SpecialOrderService specialOrderService;

    public KafkaController(CommonOrderService commonOrderService, SpecialOrderService specialOrderService) {
        this.commonOrderService = commonOrderService;
        this.specialOrderService = specialOrderService;
    }

    @Value("kafka.events.input.rejected")
    private String rejected;

    @Value("${kafka.events.input.approved}")
    private String approved;

    @KafkaListener(topics = "${kafka.topics.input}")
    public void consume(String event) {
        ObjectMapper mapper = new ObjectMapper();

        InboxEvent inboxEvent;
        try {
            inboxEvent = mapper.readValue(event, InboxEvent.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (inboxEvent.getEvent().equals(approved)) {

            return;
        }

        if (inboxEvent.getEvent().equals(rejected)) {
            commonOrderService.cancel(inboxEvent.getSourceOrderId());
        }
    }
}
