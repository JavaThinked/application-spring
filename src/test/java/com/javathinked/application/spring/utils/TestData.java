package com.javathinked.application.spring.utils;

import com.javathinked.application.spring.model.Person;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "Polo";
    public static final String LAST_NAME = "Wang";
    public static final LocalDate BIRTH_DAY = LocalDate.now();
    public static final String PHONE_NUMBER = "555-444-9922";
    public static final String EMAIL = "polo.wang@mail.com";

    public static Person createPerson() {
        var person = new Person();
        person.setId(ID);
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);
        person.setBirthDay(BIRTH_DAY);
        person.setPhoneNumber(PHONE_NUMBER);
        person.setEmail(EMAIL);
        return person;
    }

    public static List<Person> createPersons() {
        return List.of(createPerson());
    }
}
