package ru.penlk.business.implementations.fk;

import lombok.AllArgsConstructor;
import ru.penlk.business.contracts.cars.fk.SpecialAllowedPartProvider;
import ru.penlk.dao.entities.BaseEntity;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.entities.orders.special.SpecialAllowedPart;
import ru.penlk.dao.entities.vo.Price;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;
import ru.penlk.presentation.cars.models.SpecialAllowedPartDto;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class SpecialAllowedPartProviderImpl implements SpecialAllowedPartProvider {
    private final Set<SpecialAllowedPartDto> specialAllowedPartDtos;

    @Override
    public Set<SpecialAllowedPart> getSpecialAllowedParts(Car car, CarPartRepository repository) {
        Set<Long> specialAllowedPartIds = new HashSet<>(specialAllowedPartDtos
                .stream().map(SpecialAllowedPartDto::carPartId).toList()
        );

        Set<CarPart> actuallyCarParts = new HashSet<>(repository.findAllById(specialAllowedPartIds));

        Set<Long> actuallyCarPartIds = new HashSet<>(actuallyCarParts.stream().map(BaseEntity::getId).toList());

        specialAllowedPartIds.forEach(id -> {
            if (!actuallyCarPartIds.contains(id)) {
                throw new IllegalArgumentException("Car part id " + id + " not found");
            }
        });

        Set<SpecialAllowedPart> specialAllowedParts = new HashSet<>(
                actuallyCarParts.stream().map(x -> new SpecialAllowedPart(null, car, x, null)).toList()
        );

        specialAllowedPartDtos.forEach(dto -> {
            SpecialAllowedPart part = specialAllowedParts
                    .stream().filter(x -> x.getCarPart().getId().equals(dto.carPartId()))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Car part id " + dto.carPartId() + " not found"));
            part.setPrice(new Price(dto.price()));
        });

        return specialAllowedParts;
    }
}
