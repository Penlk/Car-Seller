package ru.penlk.dao.entities.users.managers;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;

@Getter
@Entity
@Table(name = "managers")
public class Manager extends BaseEntity { }
