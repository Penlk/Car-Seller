package ru.penlk.presentation.orders.special;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.SpecialOrderService;
import ru.penlk.presentation.orders.special.models.CreateSpecialOrderDto;
import ru.penlk.presentation.orders.special.models.SpecialOrderDto;

@AllArgsConstructor
@RestController
@RequestMapping("/special-order")
public class SpecialOrderController {
    private final SpecialOrderService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.read(id));
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
    public ResponseEntity<?> issue(@NotNull @RequestBody @Valid CreateSpecialOrderDto request) {
        ModelMapper mapper = new ModelMapper();

        try {
            return ResponseEntity.ok(mapper.map(service.issue(request.clientId(), request.configurationId()), SpecialOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.confirm(id), SpecialOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/wait-purchase/{id}")
    public ResponseEntity<?> waitPurchase(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.waitPurchase(id), SpecialOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<?> purchase(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.purchase(id), SpecialOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/car-ready/{id}")
    public ResponseEntity<?> ready(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.carReadyToTake(id), SpecialOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<?> complete(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();

        try {
            return ResponseEntity.ok(mapper.map(service.complete(id), SpecialOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.cancel(id), SpecialOrderDto.class));
        }  catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
