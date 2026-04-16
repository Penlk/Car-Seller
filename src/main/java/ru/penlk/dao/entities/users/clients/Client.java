package ru.penlk.dao.entities.users.clients;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import ru.penlk.dao.entities.BaseEntity;

@Getter
@Entity
@Table(name = "clients")
public class Client extends BaseEntity {
}