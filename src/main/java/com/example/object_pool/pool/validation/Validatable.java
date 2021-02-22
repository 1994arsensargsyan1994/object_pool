package com.example.object_pool.pool.validation;


public interface Validatable<T> {


    boolean validate(T object);

    default boolean basValidate(T object) {

        if (object == null) {
            return false;
        }

        return true;
    }

}
