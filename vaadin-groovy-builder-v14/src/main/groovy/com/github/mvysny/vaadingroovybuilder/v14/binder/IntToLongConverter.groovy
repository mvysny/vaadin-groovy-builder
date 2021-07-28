package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Converts {@link Integer} from {@link com.vaadin.flow.component.textfield.IntegerField} to Long-typed bean field.
 */
@CompileStatic
class IntToLongConverter implements Converter<Integer, Long> {
    @Override
    Result<Long> convertToModel(@Nullable Integer value, @NotNull ValueContext context) {
        return Result.ok(value?.toLong())
    }

    @Override
    Integer convertToPresentation(@Nullable Long value, @NotNull ValueContext context) {
        return value?.toInteger()
    }
}
