package com.javathinked.application.spring.controller;

import com.javathinked.application.spring.controller.exception.BadRequestException;
import com.javathinked.application.spring.controller.exception.Message;
import com.javathinked.application.spring.generic.GenericService;
import com.javathinked.application.spring.service.exception.ServiceException;
import com.javathinked.application.spring.controller.exception.NotFoundException;
import com.javathinked.application.spring.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class PersonController {

    private final GenericService<Person> service;

    public PersonController(GenericService<Person> service) {
        this.service = service;
    }

    @GetMapping("/persons/{id}")
    public Person findPersonById(@PathVariable("id") Long id) {
        try {
            return service.findById(id);
        } catch(ServiceException exception) {
            throw new NotFoundException("An error occurs while finding a person", exception);
        }
    }

    @GetMapping("/persons")
    public List<Person> findPersons() {
        return service.findAll();
    }

    @PostMapping("/persons")
    public Person createPerson(@RequestBody Person person) {
        try {
            return service.create(person);
        } catch(ServiceException exception) {
            throw new BadRequestException("An error occurs while creating a person", exception);
        }
    }

    @PutMapping("/persons")
    public Person updatePerson(@RequestBody Person person) {
        try {
            return service.update(person);
        } catch(ServiceException exception) {
            throw new BadRequestException("An error occurs while updating a person", exception);
        }
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Message> deletePerson(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            var message = new Message(HttpStatus.OK,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    String.format("Person with id %s as been deleted", id));
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch(ServiceException exception) {
            throw new NotFoundException("An error occurs while deleting a person", exception);
        }
    }
}
