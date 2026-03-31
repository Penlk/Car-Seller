package ru.penlk.dao.entities.nodes;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class RequireNodeId implements Serializable {
    private Long node;
    private Long car;
}
