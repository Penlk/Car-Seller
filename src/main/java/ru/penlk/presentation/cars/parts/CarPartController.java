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
import ru.penlk.presentation.mapping.cars.parts.CarPartMapper;
import ru.penlk.presentation.mapping.cars.parts.CreateCarPartMapper;

import java.util.List;

@RestController
@RequestMapping("/car_part")
@AllArgsConstructor
public class CarPartController {
    private final CarPartService carPartService;
    private final CreateCarPartMapper createCarPartMapper;
    private final CarPartMapper carPartMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CarPartDto> get(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(carPartMapper.carPartToCarPartDto(carPartService.read(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        carPartService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<?> update(@NotNull @RequestBody @Valid CarPartDto request) {
        try {
            CarPart carPart = carPartMapper.carPartDtoToCarPart(request);
            return ResponseEntity.ok(carPartMapper.carPartToCarPartDto(carPartService.update(carPart, request.nodeId())));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CarPartDto> create(@NotNull @RequestBody @Valid CreateCarPartDto request) {
        CarPart carPart = createCarPartMapper.createCarPartDtoToCarPart(request);
        CarPartDto result = carPartMapper.carPartToCarPartDto(carPartService.create(carPart, request.nodeId()));

        return ResponseEntity.ok().body(result);
    }
}
