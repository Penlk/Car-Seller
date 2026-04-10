package ru.penlk.dao.entities.configurations.defaults;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultConfigurationId implements Serializable {
    private Long carPart;
    private Long car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultConfigurationId that)) return false;
        return Objects.equals(car, that.car) &&
                Objects.equals(carPart, that.carPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, carPart);
    }
}
