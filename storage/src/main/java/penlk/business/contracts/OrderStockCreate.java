package penlk.business.contracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import penlk.dao.entities.orders.OrderType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStockCreate {
    private Long orderSourceId;

    private UUID carStockId;

    private Set<UUID> carPartStockIds = new HashSet<>();

    private OrderType orderType;
}
