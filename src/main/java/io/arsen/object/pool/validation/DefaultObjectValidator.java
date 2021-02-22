package io.arsen.object.pool.validation;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

public class DefaultObjectValidator<T> implements ObjectValidator<T> {

    @Override
    public boolean isValid(final T object, final Collection<T> objects) {
        // TODO: 22.02.21

        if (object == null){
            return false;
        }
        if (CollectionUtils.isEmpty(objects)){
            return false;
        }
        if (!objects.contains(object)){
            return false;
        }
        return true;
    }
}