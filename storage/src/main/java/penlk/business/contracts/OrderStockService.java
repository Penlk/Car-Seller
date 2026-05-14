package penlk.business.contracts;

import penlk.business.internal.OrderStockFactory;
import penlk.dao.entities.orders.OrderStock;

public interface OrderStockService {
    OrderStock create(OrderStockCreate orderStockCreate) throws RuntimeException;
}
