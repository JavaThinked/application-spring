package com.javathinked.application.spring.service;

import com.javathinked.application.spring.repository.PersonRepository;
import com.javathinked.application.spring.service.exception.ServiceException;
import com.javathinked.application.spring.utils.TestData;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest implements WithAssertions {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService underTest;

    @Test
    void givenService_whenFindingPersonById_thenFindPerson() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(TestData.createPerson()));
        var foundPerson = underTest.findById(anyLong());

        assertAll(
                () -> assertThat(foundPerson).isNotNull(),
                () -> assertThat(foundPerson.getId()).isEqualTo(TestData.ID),
                () -> assertThat(foundPerson.getFirstName()).isEqualTo(TestData.FIRST_NAME),
                () -> assertThat(foundPerson.getLastName()).isEqualTo(TestData.LAST_NAME),
                () -> assertThat(foundPerson.getPhoneNumber()).isEqualTo(TestData.PHONE_NUMBER),
                () -> assertThat(foundPerson.getEmail()).isEqualTo(TestData.EMAIL)
        );
    }

    @Test
    void givenService_whenFindingPersons_thenFindAllPersons() {
        when(repository.findAll()).thenReturn(TestData.createPersons());

        assertThat(underTest.findAll()).isNotEmpty();
    }

    @Test
    void givenService_whenCreatePerson_thenPersonIsCreated() {
        var person = TestData.createPerson();
        when(repository.save(person)).thenReturn(person);
        var createdPerson = underTest.create(person);

        assertThat(createdPerson).isNotNull();
    }

    @Test
    void givenService_whenCreatePersonAndFieldsInvalid_thenThrowException() {
        var person = TestData.createPerson();
        person.setFirstName("");

        var exception = assertThrows(
                ServiceException.class, () -> underTest.create(person)
        );
        assertThat(exception.getMessage()).contains("missing");
    }

    @Test
    void givenService_whenUpdatePerson_thenPersonIsUpdated() {
        var personToUpdate = TestData.createPerson();
        personToUpdate.setLastName("New Last Name");
        when(repository.save(personToUpdate)).thenReturn(personToUpdate);
        var newPerson = underTest.update(personToUpdate);

        assertThat(newPerson).isNotNull();
    }

    @Test
    void givenService_whenUpdatePersonAndFieldsInvalid_thenThrowException() {
        var person = TestData.createPerson();
        person.setLastName("");

        var exception = assertThrows(
                ServiceException.class, () -> underTest.create(person)
        );
        assertThat(exception.getMessage()).contains("missing");
    }

    @Test
    void givenService_whenDeletePerson_thenPersonIsDeleted() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(TestData.createPerson()));
        underTest.delete(TestData.ID);
        verify(repository).deleteById(TestData.ID);
    }
}