package com.pernal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGet() throws Exception {
        mockMvc.perform(get("/users/mojombo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"login\":\"mojombo\",\"name\":\"Tom Preston-Werner\",\"type\":\"User\",\"avatarUrl\":\"https://avatars.githubusercontent.com/u/1?v=4\",\"createdAt\":\"2007-10-20T05:24:19Z\",\"calculations\":6.4}")));
    }

    @Test
    public void shouldNotFoundForGet() throws Exception {
        mockMvc.perform(get("/users/mojombo123"))
                .andExpect(status().isNotFound());
    }

}
