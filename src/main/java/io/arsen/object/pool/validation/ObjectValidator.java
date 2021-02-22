package io.arsen.object.pool.validation;

import java.util.Collection;

public interface ObjectValidator<T> {

    boolean isValid(T object, Collection<T> objects);

    default boolean isNotValid(T object, Collection<T> objects) {
        return !isValid(object, objects);
    }
}