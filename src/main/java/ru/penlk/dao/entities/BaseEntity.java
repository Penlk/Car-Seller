package ru.penlk.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Generated;

import java.time.Instant;

@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(name = "id",  nullable = false)
    @Generated(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Instant createdAt;

    @Column
    Instant updatedAt;

    @Column
    boolean removed;
}
