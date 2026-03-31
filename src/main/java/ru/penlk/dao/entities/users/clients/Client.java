package ru.penlk.dao.entities.users.clients;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.penlk.dao.entities.BaseEntity;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Table(name = "clients")
public class Client extends BaseEntity { }