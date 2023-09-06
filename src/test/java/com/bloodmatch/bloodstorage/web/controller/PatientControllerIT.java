package com.bloodmatch.bloodstorage.web.controller;

import com.bloodmatch.bloodstorage.business.model.Patient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIT {

    @Autowired
    public MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void test() {
        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/patient")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("patient", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Patient> responseObjectActual = mapper.readValue(response, new TypeReference<>() {
        });
        List<Patient> responseObjectExpected = mapper.readValue(Paths.get("").toFile(), // pass path before start test
                new TypeReference<>() {
                });
        assertEquals(responseObjectActual, responseObjectExpected);
    }
}
