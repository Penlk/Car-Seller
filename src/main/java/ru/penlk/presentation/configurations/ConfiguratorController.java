package ru.penlk.presentation.configurations;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
import ru.penlk.business.contracts.configurations.ConfiguratorService;
import ru.penlk.business.implementations.fk.SpecialConfigurationFactoryImpl;
import ru.penlk.dao.entities.configurations.specials.Configurator;
import ru.penlk.presentation.configurations.models.CreateConfiguratorDto;
import ru.penlk.presentation.mapping.configurations.ConfiguratorMapper;
import ru.penlk.presentation.mapping.configurations.CreateConfiguratorMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/configurator")
public class ConfiguratorController {
    private final ConfiguratorService configuratorService;
    private final CreateConfiguratorMapper createConfiguratorMapper;
    private final ConfiguratorMapper configuratorMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(configuratorMapper.ConfiguratorToDto(configuratorService.read(id)));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateConfiguratorDto dto) {
        Configurator configurator = createConfiguratorMapper.CreateDtoToConfigurator(dto);

        SpecialConfigurationFactoryImpl factory = new SpecialConfigurationFactoryImpl(dto.specialConfigurationIds());

        try {
            Configurator result = configuratorService.create(configurator, dto.carId(), factory);
            return ResponseEntity.ok().body(configuratorMapper.ConfiguratorToDto(result));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            configuratorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/add/{carPartId}")
    public ResponseEntity<?> addPart(@PathVariable Long carPartId, @RequestBody @Valid Long configuratorId) {
        try {
            Configurator result = configuratorService.addPart(carPartId, configuratorId);

            return ResponseEntity.ok().body(configuratorMapper.ConfiguratorToDto(result));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/remove/{carPartId}")
    public ResponseEntity<?> removePart(@PathVariable Long carPartId, @RequestBody @Valid Long configuratorId) {
        try {
            Configurator result = configuratorService.removePart(carPartId, configuratorId);

            return ResponseEntity.ok().body(configuratorMapper.ConfiguratorToDto(result));
        } catch (ServiceException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
