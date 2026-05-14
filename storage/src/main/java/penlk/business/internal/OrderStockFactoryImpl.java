package penlk.business.internal;

import lombok.AllArgsConstructor;
import penlk.business.contracts.OrderStockCreate;
import penlk.dao.entities.CarPartStock;
import penlk.dao.entities.CarStock;
import penlk.dao.entities.orders.OrderStock;
import penlk.dao.entities.orders.OrderType;
import penlk.dao.repositories.CarPartStockRepository;
import penlk.dao.repositories.CarStockRepository;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderStockFactoryImpl implements OrderStockFactory {
    private final OrderStockCreate rawOrder;

    @Override
    public OrderStock create(CarPartStockRepository carPartStockRepository, CarStockRepository carStockRepository) throws RuntimeException {
        OrderStock orderStock = new OrderStock();

        CarStock car = carStockRepository.findById(rawOrder.getCarStockId()).orElse(null);

        if (rawOrder.getOrderType() == OrderType.Special) {
            Set<CarPartStock> carPartStocks;

            try {
                carPartStocks = rawOrder.getCarPartStockIds().stream()
                        .map(x -> carPartStockRepository.findById(x)
                                .orElseThrow(() -> new RuntimeException(String.format("CarPartStock with id %s not found", x))))
                        .collect(Collectors.toUnmodifiableSet());

                orderStock.setCarPartStocks(carPartStocks);
            } catch (RuntimeException e) {
                orderStock.setCarPartStocks(null);
            }
        }



        orderStock.setCarStock(car);
        orderStock.setOrderSourceId(rawOrder.getOrderSourceId());
        orderStock.setOrderType(rawOrder.getOrderType());

        return orderStock;
    }
}
