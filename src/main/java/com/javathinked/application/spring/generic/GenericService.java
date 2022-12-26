package com.javathinked.application.spring.generic;


import java.util.List;

public interface GenericService<T> {

    T findById(Long id);
    List<T> findAll();
    T create(T object);
    T update(T object);
    void delete(Long id);
}
