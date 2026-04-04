package ru.penlk.presentation.orders.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.CommonOrderService;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.orders.common.models.CommonOrderDto;
import ru.penlk.presentation.orders.common.models.CreateCommonOrderDto;

@AllArgsConstructor
@RestController
@RequestMapping("/common-order")
public class CommonOrderController {
    private final CommonOrderService service;

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

    @PatchMapping
    public ResponseEntity<CommonOrderDto> update(@NotNull @RequestBody @Valid CommonOrderDto request) {
        ModelMapper mapper = new ModelMapper();

        try {
            return ResponseEntity.ok(
                    mapper.map(service.update(mapper.map(request, CommonOrder.class)), CommonOrderDto.class)
            );
        } catch (ServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CommonOrderDto> create(@NotNull @RequestBody @Valid CreateCommonOrderDto request) {
        ModelMapper mapper = new ModelMapper();

        CommonOrder order = mapper.map(request,CommonOrder.class);

        return ResponseEntity.ok(
                mapper.map(service.create(order), CommonOrderDto.class)
        );
    }

    @PostMapping("/issue")
    public ResponseEntity<?> issue(@NotNull @RequestBody @Valid CreateCommonOrderDto request) {
        ModelMapper mapper = new ModelMapper();

        try {
            return ResponseEntity.ok(mapper.map(service.issue(request.clientId(), request.carId()), CommonOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.confirm(id), CommonOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/wait-purchase/{id}")
    public ResponseEntity<?> waitPurchase(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.waitPurchase(id), CommonOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<?> purchase(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.purchase(id), CommonOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/car-ready/{id}")
    public ResponseEntity<?> ready(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.carReadyToTake(id), CommonOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<?> complete(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();

        try {
            return ResponseEntity.ok(mapper.map(service.complete(id), CommonOrderDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();
        try {
            return ResponseEntity.ok(mapper.map(service.cancel(id), CommonOrderDto.class));
        }  catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
