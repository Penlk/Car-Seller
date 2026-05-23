package penlk.business.internal;

import penlk.dao.entities.orders.OrderStock;
import penlk.dao.repositories.CarPartStockRepository;
import penlk.dao.repositories.CarStockRepository;

public interface OrderStockFactory {
    OrderStock create(CarPartStockRepository carPartStockRepository, CarStockRepository carStockRepository) throws RuntimeException;
}