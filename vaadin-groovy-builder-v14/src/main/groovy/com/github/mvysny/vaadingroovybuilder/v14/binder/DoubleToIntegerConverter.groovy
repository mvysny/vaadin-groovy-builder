package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Converts {@link Double} from {@link com.vaadin.flow.component.textfield.NumberField} to {@link Integer}-typed bean field.
 * <p></p>
 * It's probably better to use {@link com.vaadin.flow.component.textfield.IntegerField} directly instead.
 */
@CompileStatic
class DoubleToIntegerConverter implements Converter<Double, Integer> {
    @Override
    Result<Integer> convertToModel(@Nullable Double value, @NotNull ValueContext context) {
        return Result.ok(value?.toInteger())
    }

    @Override
    Double convertToPresentation(@Nullable Integer value, @NotNull ValueContext context) {
        return value?.toDouble()
    }
}
