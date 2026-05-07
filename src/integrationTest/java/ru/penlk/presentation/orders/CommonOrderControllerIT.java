package ru.penlk.presentation.orders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.penlk.business.contracts.ServiceException;
import ru.penlk.business.contracts.orders.CommonOrderService;
import ru.penlk.config.AbstractIntegrationTest;
import ru.penlk.dao.entities.orders.common.CommonOrder;
import ru.penlk.presentation.mapping.orders.common.CommonOrderMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CommonOrderController tests")
@AutoConfigureMockMvc
class CommonOrderControllerIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommonOrderService service;

    @MockitoBean
    private CommonOrderMapper commonOrderMapper;

    @Test
    void shouldGetOrderByIdSuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.find(1L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(get("/common-order/{id}", 1L)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).find(1L);
    }

    @Test
    void shouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
        when(service.find(9999L)).thenThrow(new ServiceException("not found"));

        mockMvc.perform(get("/common-order/{id}", 9999L)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service).find(9999L);
    }

    @Test
    void shouldGetAllOrdersSuccessfully() throws Exception {
        when(service.findAll()).thenReturn(List.of(mock(CommonOrder.class), mock(CommonOrder.class)));

        mockMvc.perform(get("/common-order")
                        .with(managerJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    void shouldDeleteOrderSuccessfully() throws Exception {
        doNothing().when(service).delete(10L);

        mockMvc.perform(delete("/common-order/{id}", 10L)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).delete(10L);
    }

    @Test
    void shouldPlaceOrderSuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.placement(5L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(post("/common-order/placement/{carId}", 5L)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).placement(5L);
    }

    @Test
    void shouldReturnBadRequestWhenPlacementFails() throws Exception {
        when(service.placement(5L)).thenThrow(new ServiceException("Cannot find any manager"));

        mockMvc.perform(post("/common-order/placement/{carId}", 5L)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(service).placement(5L);
    }

    @Test
    void shouldConfirmOrderSuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.confirm(7L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(post("/common-order/confirm/{id}", 7L)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).confirm(7L);
    }

    @Test
    void shouldReturnBadRequestWhenConfirmFails() throws Exception {
        when(service.confirm(7L)).thenThrow(new ServiceException("bad state"));

        mockMvc.perform(post("/common-order/confirm/{id}", 7L)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(service).confirm(7L);
    }

    @Test
    void shouldWaitPurchaseSuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.waitPurchase(7L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(post("/common-order/wait-purchase/{id}", 7L)
                        .with(managerJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).waitPurchase(7L);
    }

    @Test
    void shouldPurchaseSuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.purchase(7L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(post("/common-order/purchase/{id}", 7L)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).purchase(7L);
    }

    @Test
    void shouldCarReadySuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.carReadyToTake(7L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(post("/common-order/car-ready/{id}", 7L)
                        .with(managerJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).carReadyToTake(7L);
    }

    @Test
    void shouldCompleteSuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.complete(7L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(post("/common-order/complete/{id}", 7L)
                        .with(managerJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).complete(7L);
    }

    @Test
    void shouldCancelSuccessfully() throws Exception {
        CommonOrder order = mock(CommonOrder.class);

        when(service.cancel(7L)).thenReturn(order);
        when(commonOrderMapper.commonOrderToCommonOrderDto(any(CommonOrder.class))).thenReturn(null);

        mockMvc.perform(post("/common-order/cancel/{id}", 7L)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).cancel(7L);
    }

    private RequestPostProcessor adminJwt() {
        return SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.subject("admin-1"))
                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private RequestPostProcessor managerJwt() {
        return SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.subject("manager-1"))
                .authorities(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }

    private RequestPostProcessor userJwt() {
        return SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.subject("user-1"))
                .authorities(new SimpleGrantedAuthority("ROLE_USER"));
    }
}