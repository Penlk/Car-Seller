package ru.penlk.presentation.mapping.cars.parts;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.nodes.Node;

@Mapper(componentModel = "spring")
public class NodeMapperId {
    public Long nodeToId(Node node) {
        return node.getId();
    }
}
