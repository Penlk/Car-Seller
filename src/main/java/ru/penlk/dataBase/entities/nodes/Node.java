package ru.penlk.dataBase.entities.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Node {
    private NodeId id;
    private String name;
}
