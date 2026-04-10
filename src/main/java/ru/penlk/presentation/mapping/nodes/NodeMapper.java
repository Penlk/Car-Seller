package ru.penlk.presentation.mapping.nodes;

import org.mapstruct.Mapper;
import ru.penlk.dao.entities.nodes.Node;
import ru.penlk.presentation.nodes.models.NodeDto;

@Mapper(componentModel = "spring")
public interface NodeMapper {
    Node NodeDtoToNode(NodeDto nodeDto);

    NodeDto NodeToNodeDto(Node node);
}
