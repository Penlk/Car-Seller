package ru.penlk.businessLayer.contracts.commonOrders;

public interface CommonOrderService {
    CreateCommonOrder.Response create(CreateCommonOrder.Request request);

    ReadCommonOrder.Response read(ReadCommonOrder.Request request);

    UpdateCommonOrder.Response update(UpdateCommonOrder.Request request);

    DeleteCommonOrder.Response delete(DeleteCommonOrder.Request request);
}
