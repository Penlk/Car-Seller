package ru.penlk.presentation.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.internal.OrderCancelService;
import ru.penlk.business.internal.OrderReserveService;
import ru.penlk.dao.entities.outbox.OrderType;
import ru.penlk.presentation.outbox.models.InboxEvent;

@Controller
public class KafkaController {
    private final OrderReserveService orderReserveService;
    private final OrderCancelService orderCancelService;

    public KafkaController(OrderReserveService orderReserveService, OrderCancelService orderCancelService) {
        this.orderReserveService = orderReserveService;
        this.orderCancelService = orderCancelService;
    }

    @Value("${kafka.events.input.rejected}")
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
            try {
                orderReserveService.reserve(inboxEvent.getSourceOrderId(), inboxEvent.getOrderType());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return;
        }

        if (inboxEvent.getEvent().equals(rejected)) {
            try {
                orderCancelService.cancel(inboxEvent.getSourceOrderId(), inboxEvent.getOrderType());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(inboxEvent.getExplanation());

            return;
        }

        System.out.println("Not found " + inboxEvent.getEvent());
    }
}
