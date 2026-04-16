package ru.penlk.dao.entities.users.managers;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import ru.penlk.dao.entities.BaseEntity;

@Getter
@Entity
@Table(name = "managers")
public class Manager extends BaseEntity {
}
