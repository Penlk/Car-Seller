package ru.penlk.presentation.cars;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
import ru.penlk.business.contracts.cars.CarService;
import ru.penlk.business.contracts.cars.fk.DefaultConfigurationProvider;
import ru.penlk.business.contracts.cars.fk.RequireNodeProvider;
import ru.penlk.business.contracts.cars.fk.SpecialAllowedPartProvider;
import ru.penlk.business.implementations.fk.DefaultConfigurationProviderImpl;
import ru.penlk.business.implementations.fk.RequireNodeProviderImpl;
import ru.penlk.business.implementations.fk.SpecialAllowedPartProviderImpl;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.presentation.cars.models.CarDto;
import ru.penlk.presentation.cars.models.CreateCarDto;
import ru.penlk.presentation.mapping.cars.CarMapper;
import ru.penlk.presentation.mapping.cars.CreateCarMapper;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {
    private final CarService carService;
    private final CreateCarMapper createCarMapper;
    private final CarMapper carMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> get(@PathVariable @NonNull Long id) {
        try {
            return ResponseEntity.ok().body(carMapper.carToCarDto(carService.find(id)));
        } catch (ServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateCarDto request) {
        Car car = createCarMapper.createCarDtoToCar(request);

        DefaultConfigurationProvider defaultConfigurationFactory =
                new DefaultConfigurationProviderImpl(request.defaultConfigurationIds());

        SpecialAllowedPartProvider specialAllowedPartProvider =
                new SpecialAllowedPartProviderImpl(request.specialAllowedParts());

        RequireNodeProvider requireNodeProvider =
                new RequireNodeProviderImpl(request.requireNodeIds());

        try {
            Car response = carService.create(car, defaultConfigurationFactory, specialAllowedPartProvider, requireNodeProvider);

            return ResponseEntity.ok().body(carMapper.carToCarDto(response));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping
    public ResponseEntity<CarDto> update(@RequestBody @Valid CarDto request) {
        try {
            return ResponseEntity.ok().body(
                    carMapper.carToCarDto(
                            carService.update(
                                    carMapper.carDtoToCar(request)
                            )
                    )
            );
        } catch (ServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @NonNull Long id) {

        try {
            carService.delete(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
