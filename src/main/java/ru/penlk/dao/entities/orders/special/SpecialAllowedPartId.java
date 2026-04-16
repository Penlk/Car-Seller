package ru.penlk.dao.entities.orders.special;


import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
public class SpecialAllowedPartId implements Serializable {
    private Long carPart;
    private Long car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialAllowedPartId that)) return false;
        return Objects.equals(carPart, that.carPart) &&
                Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, carPart);
    }
}
