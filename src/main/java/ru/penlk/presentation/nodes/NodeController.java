package ru.penlk.presentation.nodes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.nodes.NodeService;
import ru.penlk.dao.entities.nodes.Node;
import ru.penlk.presentation.mapping.nodes.NodeMapper;
import ru.penlk.presentation.nodes.models.NodeDto;

@AllArgsConstructor
@RestController
@RequestMapping("/nodes")
public class NodeController {
    private final NodeService nodeService;
    private final NodeMapper nodeMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@Valid @NotNull @PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(nodeMapper.NodeToNodeDto(nodeService.find(id)));
        } catch (ServiceException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody @NotBlank String name) {
        return ResponseEntity.ok().body(nodeMapper.NodeToNodeDto(nodeService.create(new Node(name))));
    }

    @PatchMapping
    public ResponseEntity<?> update(@Valid @RequestBody NodeDto nodeDto) {
        try {
            Node node = nodeMapper.NodeDtoToNode(nodeDto);
            NodeDto result = nodeMapper.NodeToNodeDto(nodeService.update(node));

            return ResponseEntity.ok().body(result);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        try {
            nodeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
