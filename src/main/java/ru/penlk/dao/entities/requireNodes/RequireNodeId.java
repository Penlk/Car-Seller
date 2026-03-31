package ru.penlk.dao.entities.requireNodes;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class RequireNodeId implements Serializable {
    private Long nodeId;
    private Long carId;
}
