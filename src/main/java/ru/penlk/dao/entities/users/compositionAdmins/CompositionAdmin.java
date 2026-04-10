package ru.penlk.dao.entities.users.compositionAdmins;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;

@Getter
@Entity
@Table(name = "composition_admins")
public class CompositionAdmin extends BaseEntity { }
