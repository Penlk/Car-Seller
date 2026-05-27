package penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import penlk.business.contracts.OrderStockService;
import penlk.business.internal.OrderStockFactory;
import penlk.business.kafka.KafkaConstants;
import penlk.dao.entities.CarPartStock;
import penlk.dao.entities.CarStock;
import penlk.dao.entities.Outbox;
import penlk.dao.entities.orders.OrderStatus;
import penlk.dao.entities.orders.OrderStock;
import penlk.dao.entities.orders.OrderType;
import penlk.dao.repositories.CarPartStockRepository;
import penlk.dao.repositories.CarStockRepository;
import penlk.dao.repositories.OrderStockRepository;
import penlk.dao.repositories.OutboxRepository;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class OrderStockServiceImpl implements OrderStockService {
    private final CarStockRepository carStockRepository;
    private final CarPartStockRepository carPartStockRepository;
    private final OrderStockRepository orderStockRepository;
    private final OutboxRepository outboxRepository;

    private final KafkaTemplate<Long, Outbox> orderStockKafkaTemplate;
    private final KafkaConstants topic;

    @Override
    public OrderStock create(OrderStockFactory factory) throws RuntimeException {
        OrderStock order = factory.create(carPartStockRepository, carStockRepository);
        CarStock car = order.getCarStock();

        if (car == null ) {
            outboxRepository.save(new Outbox(
                    order.getOrderType(),
                    order.getOrderSourceId(),
                    topic.getRejectedEvent(),
                    "Cannot find car"
            ));

            order.setStatus(OrderStatus.FAIL);
            return orderStockRepository.save(order);
        } else if (!car.isAvailable()) {
            outboxRepository.save(new Outbox(
                    order.getOrderType(),
                    order.getOrderSourceId(),
                    topic.getRejectedEvent(),
                    "Each car is reserved"
            ));
            order.setStatus(OrderStatus.FAIL);
            return orderStockRepository.save(order);
        }

        Set<CarPartStock> carPartStocks = order.getCarPartStocks();
        if (order.getOrderType() == OrderType.SPECIAL && carPartStocks == null) {
            outboxRepository.save(new Outbox(
                    order.getOrderType(),
                    order.getOrderSourceId(),
                    topic.getRejectedEvent(),
                    "Cannot find all car's parts"
            ));

            order.setStatus(OrderStatus.FAIL);
            return orderStockRepository.save(order);
        } else if (order.getOrderType() == OrderType.SPECIAL) {
            try {
                carPartStocks.forEach(carPartStock -> {
                    if (!carPartStock.isAvailable()) {
                        throw new RuntimeException(String.format("Each car's part with id %s is reserved", carPartStock.getId()));
                    }
                });
            } catch (RuntimeException e) {
                outboxRepository.save(new Outbox(
                        order.getOrderType(),
                        order.getOrderSourceId(),
                        topic.getRejectedEvent(),
                        e.getMessage()
                ));

                order.setStatus(OrderStatus.FAIL);
                return orderStockRepository.save(order);
            }
        }

        car.setReserved(car.getReserved() + 1);
        if (order.getOrderType() == OrderType.SPECIAL) {
            carPartStocks.forEach(carPartStock -> {
                carPartStock.setReserved(carPartStock.getReserved() + 1);
            });
        }

        carStockRepository.save(car);

        outboxRepository.save(new Outbox(
                order.getOrderType(),
                order.getOrderSourceId(),
                topic.getApprovedEvent(),
                null
        ));

        order.setStatus(OrderStatus.ASSEMBLED);
        return orderStockRepository.save(order);
    }

    @Scheduled(cron = "${outbox.cron}")
    public void publishOutbox() {
        List<Outbox> outboxes = outboxRepository.findTop100ByOrderByCreatedAtAsc();

        for (Outbox outbox : outboxes) {
            try {
                orderStockKafkaTemplate.send(
                        topic.getStorageEvent(),
                        outbox.getSourceOrderId(),
                        outbox
                ).get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }

            outboxRepository.delete(outbox);
        }
    }
}
