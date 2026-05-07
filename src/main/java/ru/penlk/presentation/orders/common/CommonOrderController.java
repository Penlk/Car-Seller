package ru.penlk.presentation.orders.common;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.penlk.business.contracts.DomainValidationException;
import ru.penlk.business.contracts.IncompatibleComponentException;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.CommonOrderService;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.mapping.orders.common.CommonOrderMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/common-order")
public class CommonOrderController {
    private final CommonOrderService service;
    private final CommonOrderMapper commonOrderMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(service.find(id)));
        } catch (ServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/placement/{carId}")
    public ResponseEntity<?> placement(@PathVariable @NotNull @RequestBody Long carId) {
        try {
            CommonOrder order = service.placement(carId);
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(order));
        } catch (ServiceException | IncompatibleComponentException | DomainValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(service.confirm(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/wait-purchase/{id}")
    public ResponseEntity<?> waitPurchase(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(service.waitPurchase(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<?> purchase(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(service.purchase(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/car-ready/{id}")
    public ResponseEntity<?> ready(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(service.carReadyToTake(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<?> complete(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(service.complete(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(commonOrderMapper.commonOrderToCommonOrderDto(service.cancel(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
