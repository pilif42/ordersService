package com.example.ordersService.endpoint;

import com.example.ordersService.domain.Order;
import com.example.ordersService.dto.OrderDto;
import com.example.ordersService.mapper.OrderMapper;
import com.example.ordersService.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderEndpoint.class)
public class OrderEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    public void givenNOrdersAreRetrievedFromDB_expectNOrdersInResponse() throws Exception {
        Order orderOne = new Order();
        orderOne.setId(1);
        Order orderTwo = new Order();
        orderTwo.setId(2);
        List<Order> orders = List.of(orderOne, orderTwo);
        when(orderService.findAll()).thenReturn(orders);

        OrderDto orderDtoOne = new OrderDto();
        orderDtoOne.setId(1);
        OrderDto orderDtoTwo = new OrderDto();
        orderDtoTwo.setId(2);
        List<OrderDto> orderDtos = List.of(orderDtoOne, orderDtoTwo);
        when(orderMapper.map(orders)).thenReturn(orderDtos);

        this.mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].*", hasSize(4)))
                .andExpect(jsonPath("$[1].*", hasSize(4)))
                .andExpect(jsonPath("$[*].id",containsInAnyOrder(1, 2)))
        ;

        // TODO Test other JSON paths
    }

    @Test
    public void givenDBCallThrowsException_expect500WithCorrectMessage() throws Exception {
        when(orderService.findAll()).thenThrow(new RuntimeException("some issue with DB"));

        MvcResult result = this.mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andReturn();
        assertEquals("Some server side issue.", result.getResponse().getContentAsString());
    }
}
