package penlk.business.internal;

import lombok.AllArgsConstructor;
import penlk.business.contracts.OrderStockCreate;
import penlk.dao.entities.CarPartStock;
import penlk.dao.entities.CarStock;
import penlk.dao.entities.orders.OrderStock;
import penlk.dao.entities.orders.OrderType;
import penlk.dao.repositories.CarPartStockRepository;
import penlk.dao.repositories.CarStockRepository;
import penlk.presentation.KafkaEvent;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderStockFactoryImpl implements OrderStockFactory {
    private final KafkaEvent event;

    @Override
    public OrderStock create(CarPartStockRepository carPartStockRepository, CarStockRepository carStockRepository) throws RuntimeException {
        OrderStock orderStock = new OrderStock();

        CarStock car = carStockRepository.findByCarSourceId(event.carSourceId()).orElse(null);

        if (event.carPartSourceIds() != null && !event.carPartSourceIds().isEmpty()) {
            Set<CarPartStock> carPartStocks;

            carPartStocks = carPartStockRepository.findByCarPartSourceIdIn(event.carPartSourceIds());

            if (carPartStocks == null || carPartStocks.size() != event.carPartSourceIds().size()) {
                orderStock.setCarPartStocks(null);
            } else {
                orderStock.setCarPartStocks(carPartStocks);
            }
        }



        orderStock.setCarStock(car);
        orderStock.setOrderSourceId(event.orderSourceId());
        orderStock.setOrderType(event.orderType());

        return orderStock;
    }
}
