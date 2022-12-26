package com.javathinked.application.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javathinked.application.spring.generic.GenericService;
import com.javathinked.application.spring.service.exception.ServiceException;
import com.javathinked.application.spring.utils.TestData;
import com.javathinked.application.spring.model.Person;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest implements WithAssertions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private GenericService<Person> service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void givenController_whenFindingPersonById_thenReturns_200_OK() throws Exception {
        when(service.findById(anyLong())).thenReturn(TestData.createPerson());
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/{id}", TestData.ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenPersonByIdNotFound_thenReturns_404_Not_Found() throws Exception {
        when(service.findById(anyLong())).thenThrow(ServiceException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/{id}", TestData.ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenController_whenFindingPersons_thenReturns_200_OK() throws Exception {
        when(service.findAll()).thenReturn(TestData.createPersons());
        mockMvc.perform(get("/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenCreatingPerson_thenReturns_200_OK() throws Exception {
        var person = TestData.createPerson();
        when(service.create(person)).thenReturn(person);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenCreatingPerson_thenReturns_400_OK() throws Exception {
        var person = TestData.createPerson();
        when(service.create(person)).thenThrow(ServiceException.class);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenController_whenUpdatingPerson_thenReturns_200_OK() throws Exception {
        var person = TestData.createPerson();
        when(service.create(person)).thenReturn(person);

        mockMvc.perform(put("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenUpdatingPerson_thenReturns_400_OK() throws Exception {
        var person = TestData.createPerson();
        when(service.create(person)).thenThrow(ServiceException.class);

        mockMvc.perform(put("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenDeletingPerson_thenReturns_200_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons/{id}", TestData.ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).delete(TestData.ID);
    }

    @Test
    void givenController_whenDeletingPerson_thenReturns_404_OK() throws Exception {
        when(service.findById(anyLong())).thenThrow(ServiceException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons/{id}", TestData.ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).delete(TestData.ID);
    }
}