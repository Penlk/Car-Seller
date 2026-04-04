package ru.penlk.presentation.cars.parts;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
import ru.penlk.business.contracts.cars.CarPartService;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.presentation.cars.parts.models.CarPartDto;
import ru.penlk.presentation.cars.parts.models.CreateCarPartDto;

import java.util.List;

@RestController
@RequestMapping("/car_part")
@AllArgsConstructor
public class CarPartController {
    private final CarPartService carPartService;

    @GetMapping("/{id}")
    public ResponseEntity<CarPartDto> get(@NotNull @PathVariable Long id) {
        ModelMapper mapper = new ModelMapper();

        return ResponseEntity.ok(mapper.map(carPartService.read(id), CarPartDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        carPartService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<?> update(@NotNull @RequestBody @Valid CarPartDto request) {
        ModelMapper mapper = new ModelMapper();

        try {
            return ResponseEntity.ok(mapper.map(carPartService.update(mapper.map(request, CarPart.class)), CarPartDto.class));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CarPartDto> create(@NotNull @RequestBody @Valid CreateCarPartDto request) {
        ModelMapper mapper = new ModelMapper();

        return ResponseEntity.ok(mapper.map(carPartService.create(mapper.map(request, CarPart.class)), CarPartDto.class));
    }
}
