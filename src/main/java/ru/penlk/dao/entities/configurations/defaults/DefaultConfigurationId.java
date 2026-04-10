package ru.penlk.dao.entities.configurations.defaults;

import lombok.AllArgsConstructor;
import ru.penlk.dao.entities.configurations.specials.ConfiguratorCarPartId;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
public class DefaultConfigurationId implements Serializable {
    private Long carPart;
    private Long car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultConfigurationId)) return false;
        DefaultConfigurationId that = (DefaultConfigurationId) o;
        return Objects.equals(car, that.car) &&
                Objects.equals(carPart, that.carPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, carPart);
    }
}
