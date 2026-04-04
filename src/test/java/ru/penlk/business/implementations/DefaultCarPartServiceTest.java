package ru.penlk.business.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.presentation.cars.parts.models.CarPartDto;
import ru.penlk.presentation.cars.parts.models.CreateCarPartDto;
import ru.penlk.dao.entities.cars.CarPart;
import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCarPartServiceTest {
    @Mock
    CarPartRepository repo;

    @InjectMocks
    CarPartServiceImpl service;

    @Test
    void create_returnsDto() {
        when(repo.create(any())).thenReturn(new CarPart(new CarPartId(5), "p", new NodeId(3)));

        var dto = service.create(new CreateCarPartDto("p", 3L));

        assertEquals(5L, dto.id());
        assertEquals("p", dto.namePart());
        assertEquals(3L, dto.nodeId());
    }

    @Test
    void read_existing_returnsDto() {
        when(repo.findById(new CarPartId(1))).thenReturn(Optional.of(new CarPart(new CarPartId(1), "x", new NodeId(2))));

        var dto = service.read(1L);

        assertEquals(1L, dto.id());
    }

    @Test
    void read_missing_throws() {
        when(repo.findById(new CarPartId(2))).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> service.read(2L));
    }

    @Test
    void update_success_returnsDto() {
        var req = new CarPartDto(3L, "n", 7L);
        when(repo.update(any())).thenReturn(new CarPart(new CarPartId(3), "n", new NodeId(7)));

        var out = service.update(req);

        assertEquals(3L, out.id());
    }

    @Test
    void update_notFound_throws() {
        var req = new CarPartDto(4L, "n", 7L);
        when(repo.update(any())).thenThrow(new CarPartNotFoundException(new CarPartId(4)));

        assertThrows(ServiceException.class, () -> service.update(req));
    }

    @Test
    void delete_notFound_throws() {
        doThrow(new CarPartNotFoundException(new CarPartId(8))).when(repo).delete(new CarPartId(8));

        assertThrows(ServiceException.class, () -> service.delete(8L));
    }
}
// package ru.penlk.business.implementations;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import ru.penlk.business.contracts.ServiceException;
// import ru.penlk.business.contracts.cars.parts.models.CarPartDto;
// import ru.penlk.business.contracts.cars.parts.models.CreateCarPartDto;
// import ru.penlk.dao.entities.cars.CarPart;
// import ru.penlk.dao.entities.carParts.CarPartId;
// import ru.penlk.dao.entities.nodes.NodeId;
// import ru.penlk.dao.repositories.interfaces.cars.parts.CarPartNotFoundException;
// import ru.penlk.dao.repositories.interfaces.cars.CarPartRepository;

// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.doThrow;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// class DefaultCarPartServiceTest {
//     @Mock
//     CarPartRepository repo;

//     @InjectMocks
//     DefaultCarPartService service;

//     @Test
//     void create_returnsDto() {
//         when(repo.create(any())).thenReturn(new CarPart(new CarPartId(5), "p", new NodeId(3)));

//         var dto = service.create(new CreateCarPartDto("p", 3));

//         assertEquals(5, dto.id());
//         assertEquals("p", dto.namePart());
//         assertEquals(3, dto.nodeId());
//     }

//     @Test
//     void read_existing_returnsDto() {
//         when(repo.findById(new CarPartId(1))).thenReturn(Optional.of(new CarPart(new CarPartId(1), "x", new NodeId(2))));

//         var dto = service.read(1L);

//         assertEquals(1, dto.id());
//     }

//     @Test
//     void read_missing_throws() {
//         when(repo.findById(new CarPartId(2))).thenReturn(Optional.empty());

//         assertThrows(ServiceException.class, () -> service.read(2L));
//     }

//     @Test
//     void update_success_returnsDto() {
//         var req = new CarPartDto(3, "n", 7);
//         when(repo.update(any())).thenReturn(new CarPart(new CarPartId(3), "n", new NodeId(7)));

//         var out = service.update(req);

//         assertEquals(3, out.id());
//     }

//     @Test
//     void update_notFound_throws() {
//         var req = new CarPartDto(4, "n", 7);
//         when(repo.update(any())).thenThrow(new CarPartNotFoundException(new CarPartId(4)));

//         assertThrows(ServiceException.class, () -> service.update(req));
//     }

//     @Test
//     void delete_notFound_throws() {
//         doThrow(new CarPartNotFoundException(new CarPartId(8))).when(repo).delete(new CarPartId(8));

//         assertThrows(ServiceException.class, () -> service.delete(8L));
//     }
// }
