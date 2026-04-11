package ru.penlk.presentation.orders.special;

import jakarta.validation.Valid;
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
import ru.penlk.business.contracts.orders.SpecialOrderService;
import ru.penlk.presentation.mapping.orders.special.CreateSpecialOrderMapper;
import ru.penlk.presentation.mapping.orders.special.SpecialOrderMapper;
import ru.penlk.presentation.orders.special.models.CreateSpecialOrderDto;

@AllArgsConstructor
@RestController
@RequestMapping("/special-order")
public class SpecialOrderController {
    private final SpecialOrderService service;
    private final SpecialOrderMapper specialOrderMapper;
    private final CreateSpecialOrderMapper createSpecialOrderMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.find(id)));
        } catch (ServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/issue")
    public ResponseEntity<?> placement(@NotNull @RequestBody @Valid CreateSpecialOrderDto request) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.placement(request.clientId(), request.configurationId())));
        } catch (ServiceException | IncompatibleComponentException | DomainValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.confirm(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/wait-purchase/{id}")
    public ResponseEntity<?> waitPurchase(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.waitPurchase(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<?> purchase(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.purchase(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/car-ready/{id}")
    public ResponseEntity<?> ready(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.carReadyToTake(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<?> complete(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.complete(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(specialOrderMapper.specialOrderToSpecialOrderDto(service.cancel(id)));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
