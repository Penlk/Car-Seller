package ru.penlk.dataBase.entities.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Node {
    private NodeId nodeId;
    private String name;
}
