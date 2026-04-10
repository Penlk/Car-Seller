package ru.penlk.dao.repositories.implementations.cars;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.penlk.dao.entities.cars.Car;
import ru.penlk.dao.repositories.interfaces.cars.CarQuery;

import java.util.ArrayList;
import java.util.List;

public class CarSpecification {

    public static Specification<Car> byQuery(CarQuery query) {
        return (Root<Car> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query.getMinPrice() != null) {
                predicates.add(cb.ge(root.get("price").get("engine_power"), query.getMinPrice().price()));
            }
            if (query.getMaxPrice() != null) {
                predicates.add(cb.le(root.get("price").get("engine_power"), query.getMaxPrice().price()));
            }

            if (query.getMinEnginePower() != null) {
                predicates.add(cb.ge(root.get("enginePower").get("engine_power"), query.getMinEnginePower().engine_power()));
            }
            if (query.getMaxEnginePower() != null) {
                predicates.add(cb.le(root.get("enginePower").get("engine_power"), query.getMaxEnginePower().engine_power()));
            }

            if (query.getMinEngineVolume() != null) {
                predicates.add(cb.ge(root.get("engineVolume").get("engine_power"), query.getMinEngineVolume().engine_volume()));
            }
            if (query.getMaxEngineVolume() != null) {
                predicates.add(cb.le(root.get("engineVolume").get("engine_power"), query.getMaxEngineVolume().engine_volume()));
            }

            if (query.getBrands() != null && !query.getBrands().isEmpty()) {
                predicates.add(root.get("brand").in(query.getBrands()));
            }

            if (query.getModel() != null && !query.getModel().isEmpty()) {
                predicates.add(cb.equal(root.get("model"), query.getModel()));
            }

            if (query.getBodies() != null && !query.getBodies().isEmpty()) {
                predicates.add(root.get("body").in(query.getBodies()));
            }

            if (query.getFuels() != null && !query.getFuels().isEmpty()) {
                predicates.add(root.get("fuel").in(query.getFuels()));
            }

            if (query.getGearShiftBoxes() != null && !query.getGearShiftBoxes().isEmpty()) {
                predicates.add(root.get("gearShiftBox").in(query.getGearShiftBoxes()));
            }

            if (query.getCarDrives() != null && !query.getCarDrives().isEmpty()) {
                predicates.add(root.get("carDrive").in(query.getCarDrives()));
            }

            if (query.getColours() != null && !query.getColours().isEmpty()) {
                predicates.add(root.get("colour").in(query.getColours()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
