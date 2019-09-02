package com.example.addressModification.domain;


public interface SomeEntity<T> {
    T getPrimaryKey();
    String getBusinessKey();

}