package com.javathinked.application.spring.repository;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static com.javathinked.application.spring.utils.TestData.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
class PersonRepositoryTest implements WithAssertions {

    @Autowired
    private PersonRepository repository;

    @Test
    void givenRepository_whenFindingPersons_thenFindAllPersons() {
        var persons = repository.findAll();
        assertThat(persons).isNotEmpty();
    }

    @Test
    void givenRepository_whenFindingPersonById_thenFindAPerson() {
        var foundPerson = repository.findById(1L);

        assertThat(foundPerson).hasValueSatisfying(person -> {
            assertThat(person.getFirstName()).isEqualTo("Feng");
            assertThat(person.getLastName()).isEqualTo("Wei");
            assertThat(person.getBirthDay()).isEqualTo(LocalDate.of(1985, 6, 10));
            assertThat(person.getPhoneNumber()).isEqualTo("418-999-4585");
            assertThat(person.getEmail()).isEqualTo("feng.wei@mail.com");
        });
    }

    @Test
    void givenRepository_whenCreatePerson_thenPersonIsCreated() {
        var newPerson = repository.save(createPerson());

        assertAll(
                () -> assertThat(newPerson.getId()).isEqualTo(1L),
                () -> assertThat(newPerson.getFirstName()).isEqualTo(FIRST_NAME),
                () -> assertThat(newPerson.getLastName()).isEqualTo(LAST_NAME),
                () -> assertThat(newPerson.getBirthDay()).isEqualTo(BIRTH_DAY),
                () -> assertThat(newPerson.getPhoneNumber()).isEqualTo(PHONE_NUMBER),
                () -> assertThat(newPerson.getEmail()).isEqualTo(EMAIL)
        );
    }

    @Test
    void givenRepository_whenUpdatePerson_thenPersonIsUpdated() {
        var personToUpdate = createPerson();
        personToUpdate.setFirstName("Nina");
        personToUpdate.setLastName("Williams");
        var person = repository.save(personToUpdate);

        assertAll(
                () -> assertThat(person.getFirstName()).isEqualTo("Nina"),
                () -> assertThat(person.getLastName()).isEqualTo("Williams")
        );
    }

    @Test
    void givenRepository_whenDeletePerson_thenPersonIsDeleted() {
        var personToDelete = createPerson();
        repository.delete(personToDelete);
        assertThat(repository.findById(personToDelete.getId())).isEmpty();
    }
}