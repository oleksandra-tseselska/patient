package com.bloodmatch.bloodstorage.web.controller;

import com.bloodmatch.bloodstorage.business.model.BloodGroup;
import com.bloodmatch.bloodstorage.business.repository.BloodGroupRepository;
import com.bloodmatch.bloodstorage.business.repository.model.BloodGroupEntity;
import com.bloodmatch.bloodstorage.business.repository.model.PatientEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BloodGroupControllerIT {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    BloodGroupRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    private BloodGroupEntity bloodGroupOne;
    private BloodGroupEntity bloodGroupTwo;

    @BeforeEach
    void init() {
        PatientEntity patientOne = createPatientEntity(1L, "name1", "lastName2", true,
                "12345678", "30175D49");
        PatientEntity patientTwo = createPatientEntity(2L, "name2", "lastName2", false,
                "23456781", "31175D49");
        PatientEntity patientThree = createPatientEntity(3L, "name3", "lastName3", true,
                "34567812", "32175D49");
        bloodGroupOne = createBloodGroupEntity(1L, "O+", List.of(patientOne, patientThree));
        bloodGroupTwo = createBloodGroupEntity(2L, "O-", List.of(patientTwo));
    }

    @Test
    @SneakyThrows
    void test() {
        when(repository.findAll()).thenReturn(List.of(bloodGroupOne, bloodGroupTwo));
        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/blood/group")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("bloodGroup", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BloodGroup> responseObjectActual = mapper.readValue(response, new TypeReference<>() {
        });
        List<BloodGroup> responseObjectExpected = mapper.readValue(Paths.get("src/test/resources/bloodGroupList.json").toFile(),
                new TypeReference<>() {
                });
        assertEquals(responseObjectActual, responseObjectExpected);
    }

    private BloodGroupEntity createBloodGroupEntity(Long bloodGroupID, String bloodGroup, List<PatientEntity> patients) {
        BloodGroupEntity bloodGroupEntity = new BloodGroupEntity();
        bloodGroupEntity.setId(bloodGroupID);
        bloodGroupEntity.setGroup(bloodGroup);
        bloodGroupEntity.setPatientIds(patients);
        return bloodGroupEntity;
    }

    private PatientEntity createPatientEntity(Long patientId, String name, String lastName, Boolean urgency,
                                              String phoneNumber, String uniqueNumber) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        patientEntity.setFirstName(name);
        patientEntity.setLastName(lastName);
        patientEntity.setUrgency(urgency);
        patientEntity.setPhoneNumber(phoneNumber);
        patientEntity.setUniqueNumber(uniqueNumber);
        return patientEntity;
    }
}
