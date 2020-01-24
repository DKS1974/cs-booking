package com.cs.etrading.csbooking.books.controller;

import com.cs.etrading.csbooking.books.model.Book;
import com.cs.etrading.csbooking.books.model.BookCloseEvent;
import com.cs.etrading.csbooking.util.Utils;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/tear_down.sql","/setup_data_close_book.sql"})
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturnBookIdOfNewlyCreateBook() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/book")
                .content(Utils.asJsonString(Book.builder().bookName("BOOK_1").build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").exists())
                .andExpect(jsonPath("$.bookId").isNumber())
                .andDo(print())
        ;
    }

    @Test
    public void shouldBeAbleToCloseBookAndDistributeToMaxExtend() throws Exception {
        makeCallToCloseBook(buildBookCloseEvent(1L,new BigDecimal(15.0), new Integer(80)));
        mvc.perform( MockMvcRequestBuilders
                .get("/distribution")
                .param("bookId","1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[?(@.order.id == 1 && @.order.book.bookId == 1 && @.quantity == 0 && @.price == 15.00 && @.status == \"invalid\")]").exists() )
                .andExpect(jsonPath("$.[?(@.order.id == 2 && @.order.book.bookId == 1 && @.quantity == 20 && @.price == 15.00 && @.status == \"executed\")]").exists() )
                .andExpect(jsonPath("$.[?(@.order.id == 3 && @.order.book.bookId == 1 && @.quantity == 60 && @.price == 15.00 && @.status == \"executed\")]").exists() )
                .andDo(print())
        ;

    }



    @Test
    public void shouldBeAbleToCloseBookToExtendOfOrder() throws Exception {
        makeCallToCloseBook(buildBookCloseEvent(2L,new BigDecimal(15.0), new Integer(80)));
        mvc.perform( MockMvcRequestBuilders
                .get("/distribution")
                .param("bookId","2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[?(@.order.id == 4 && @.order.book.bookId == 2 && @.quantity == 0 && @.price == 15.00 && @.status == \"invalid\")]").exists() )
                .andExpect(jsonPath("$.[?(@.order.id == 5 && @.order.book.bookId == 2 && @.quantity == 50 && @.price == 15.00 && @.status == \"executed\")]").exists() )
                .andExpect(jsonPath("$.[?(@.order.id == 6 && @.order.book.bookId == 2 && @.quantity == 5 && @.price == 15.00 && @.status == \"executed\")]").exists() )
                .andDo(print())
        ;

    }

    private BookCloseEvent buildBookCloseEvent(long bookId, BigDecimal price, Integer quantity) {
        return BookCloseEvent.builder()
                .bookId( Long.valueOf(bookId))
                .price(price)
                .quantity(quantity).build() ;
    }

    private void makeCallToCloseBook(BookCloseEvent bookCloseEvent) throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .put("/book")
                .content(Utils.asJsonString(bookCloseEvent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

}