package penlk.business.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import penlk.business.contracts.OrderStockCreate;
import penlk.business.contracts.OrderStockService;
import penlk.business.internal.OrderStockFactory;
import penlk.business.internal.OrderStockFactoryImpl;
import penlk.business.kafka.KafkaTopicProperties;
import penlk.dao.entities.orders.OrderStatus;
import penlk.dao.entities.orders.OrderStock;
import penlk.dao.repositories.CarPartStockRepository;
import penlk.dao.repositories.CarStockRepository;
import penlk.dao.repositories.OrderStockRepository;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class OrderStockServiceImpl implements OrderStockService {
    private final CarStockRepository carStockRepository;
    private final CarPartStockRepository carPartStockRepository;
    private final OrderStockRepository orderStockRepository;

    private final KafkaTemplate<Long, OrderStock> orderStockKafkaTemplate;
    private final KafkaTopicProperties topic;

    @Override
    public OrderStock create(OrderStockCreate orderStockCreate) throws RuntimeException {
        OrderStock order = new OrderStockFactoryImpl(orderStockCreate).create(carPartStockRepository, carStockRepository);

        order.setStatus(OrderStatus.ASSEMBLED);

        OrderStock orderStock = orderStockRepository.save(order);

        orderStockKafkaTemplate.send(
                topic.getStorageEvents(),
                orderStock.getOrderSourceId(),
                orderStock
        );

        return orderStock;
    }
}
