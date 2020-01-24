package com.cs.etrading.csbooking.orders.controller;

import com.cs.etrading.csbooking.books.model.Book;
import com.cs.etrading.csbooking.orders.model.Order;
import com.cs.etrading.csbooking.orders.model.OrderType;
import com.cs.etrading.csbooking.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/tear_down.sql","/setup_data_create_order.sql"})
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturnIdOfNewlyPlacedOrderOfTypeLimit() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/order")
                .content(Utils.asJsonString( Order.builder().price(new BigDecimal(14.0)).quantity(50).type(OrderType.limit).book(Book.builder().bookId(new Long(1)).build()).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andDo(print())
        ;
    }

    @Test
    public void shouldReturnIdOfNewlyPlacedOrderOfTypeMarket() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/order")
                .content(Utils.asJsonString( Order.builder().quantity(50).type(OrderType.market).book(Book.builder().bookId(new Long(1)).build()).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andDo(print())
        ;
    }


}