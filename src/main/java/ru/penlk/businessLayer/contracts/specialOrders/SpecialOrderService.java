package ru.penlk.businessLayer.contracts.specialOrders;

import ru.penlk.businessLayer.contracts.specialOrders.updates.*;

public interface SpecialOrderService {
    CreateSpecialOrder.Response create(CreateSpecialOrder.Request request);

    ReadSpecialOrder.Response read(ReadSpecialOrder.Request request);

    UpdateSpecialOrder.Response update(UpdateSpecialOrder.Request request);

    DeleteSpecialOrder.Response delete(DeleteSpecialOrder.Request request);

    IssueSpecialOrder.Response issue(IssueSpecialOrder.Request request);

    ConfirmSpecialOrder.Response confirm(ConfirmSpecialOrder.Request request);

    WaitPurchaseSpecialOrder.Response waitPurchase(WaitPurchaseSpecialOrder.Request request);

    PurchaseSpecialOrder.Response purchase(PurchaseSpecialOrder.Request request);

    CarReadyToTakeSpecialOrder.Response carReadyToTake(CarReadyToTakeSpecialOrder.Request request);

    CompletedSpecialOrder.Response completed(CompletedSpecialOrder.Request request);

    CancelSpecialOrder.Response cancel(CancelSpecialOrder.Request request);
}
