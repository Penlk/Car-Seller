package ru.penlk.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@SoftDelete(strategy = SoftDeleteType.DELETED, columnName = "removed")
public class BaseEntity {
    @Id
    @Column(name = "id",  nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @Column
    @UpdateTimestamp
    Instant updatedAt;
}
