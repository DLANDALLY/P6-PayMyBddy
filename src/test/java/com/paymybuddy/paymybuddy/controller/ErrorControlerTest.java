package com.paymybuddy.paymybuddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ErrorControlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn404ErrorPage() throws Exception {
        mockMvc.perform(get("/nonExistentPage"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    void shouldReturn403ErrorPage() throws Exception {
        mockMvc.perform(get("/admin").with(user("user").roles("USER")))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error/403"));
    }

    @Test
    void shouldReturn500ErrorPage() throws Exception {
        mockMvc.perform(get("/errorServer500"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error/500"));
    }
}