package ru.penlk.dao.entities.configurations.specials;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialConfigurationId implements Serializable {
    private Long configurator;
    private Long specialAllowedPart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialConfigurationId that)) return false;
        return configurator.equals(that.configurator) &&
                specialAllowedPart.equals(that.specialAllowedPart);
    }

    @Override
    public int hashCode() {
        return 31 * configurator.hashCode() + specialAllowedPart.hashCode();
    }
}
