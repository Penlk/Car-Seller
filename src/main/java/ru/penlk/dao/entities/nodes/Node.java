package ru.penlk.dao.entities.nodes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.penlk.dao.entities.BaseEntity;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "nodes")
@NoArgsConstructor
public class Node extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
