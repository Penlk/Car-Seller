package ru.penlk.businessLayer.contracts.commonOrders;

import ru.penlk.businessLayer.contracts.commonOrders.updates.*;

public interface CommonOrderService {
    CreateCommonOrder.Response create(CreateCommonOrder.Request request);

    ReadCommonOrder.Response read(ReadCommonOrder.Request request);

    UpdateCommonOrder.Response update(UpdateCommonOrder.Request request);

    DeleteCommonOrder.Response delete(DeleteCommonOrder.Request request);

    IssueCommonOrder.Response issue(IssueCommonOrder.Request request);

    ConfirmCommonOrder.Response confirm(ConfirmCommonOrder.Request request);

    WaitPurchaseCommonOrder.Response waitPurchase(WaitPurchaseCommonOrder.Request request);

    PurchaseCommonOrder.Response purchase(PurchaseCommonOrder.Request request);

    CarReadyToTakeCommonOrder.Response carReadyToTake(CarReadyToTakeCommonOrder.Request request);

    CompletedCommonOrder.Response completed(CompletedCommonOrder.Request request);

    CancelCommonOrder.Response cancel(CancelCommonOrder.Request request);
}
