package com.javathinked.application.spring.it;

import com.javathinked.application.spring.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static com.javathinked.application.spring.utils.TestData.createPerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PersonControllerITest {

    private static String URI = "http://localhost:%s";

    @LocalServerPort
    private String localServerPort;

    @Autowired
    private WebTestClient webTestClient;

    private static final int PERSON_ID = 1;

    @BeforeEach
    void setUp() {
        URI = String.format(URI, localServerPort);
    }

    @Test
    void integrationTest_For_FindingPersons() {
        webTestClient.get()
                .uri(URI + "/persons")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void integrationTest_For_FindingPersonById() {
        webTestClient.get()
                .uri(URI + "/persons/{id}", PERSON_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .consumeWith(result -> {
                    var person = result.getResponseBody();
                    assertAll(
                            () -> assumingThat(Objects.nonNull(person), () -> {
                                assertThat(person.getFirstName()).isNotEmpty();
                                assertThat(person.getLastName()).isNotEmpty();
                            })
                    );
                });
    }

    @Test
    void integrationTest_For_CreatingPerson() {
        var newPerson = createPerson();

        webTestClient.post()
                .uri(URI + "/persons")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newPerson)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void integrationTest_For_UpdatingPerson() {
        var updatePerson = createPerson();

        webTestClient.put()
                .uri(URI + "/persons")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updatePerson)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void integrationTest_For_DeletingPerson() {
        webTestClient.delete()
                .uri(URI + "/persons/{ID}", PERSON_ID)
                .exchange()
                .expectStatus().isOk();
    }
}
