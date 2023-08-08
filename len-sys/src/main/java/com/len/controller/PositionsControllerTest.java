package com.len.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.len.entity.SysPositions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PositionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPositions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/positions"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testCreatePosition() throws Exception {
        SysPositions newPosition = new SysPositions();
        newPosition.setName("Test Position");
        newPosition.setDescription("This is a test position");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/positions")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(newPosition)))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Position"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is a test position"));
    }

    @Test
    public void testUpdatePosition() throws Exception {
        SysPositions updatedPosition = new SysPositions();
        updatedPosition.setName("Updated Position");
        updatedPosition.setDescription("This position has been updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/positions/{id}", 1L)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updatedPosition)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Position"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This position has been updated"));
    }

    @Test
    public void testDeletePosition() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/positions/{id}", 1L))
               .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
