package ru.penlk.dao.entities.users.clients;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.penlk.dao.entities.BaseEntity;

@Getter
@Entity
@Table(name = "clients")
public class Client extends BaseEntity { }