package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Converts {@link Double} from {@link com.vaadin.flow.component.textfield.NumberField} to {@link BigDecimal}-typed bean field.
 * <p></p>
 * It's probably better to use {@link com.vaadin.flow.component.textfield.BigDecimalField} directly instead.
 */
@CompileStatic
class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {
    @Override
    Result<BigDecimal> convertToModel(@Nullable Double value, @NotNull ValueContext context) {
        return Result.ok(value?.toBigDecimal())
    }

    @Override
    Double convertToPresentation(@Nullable BigDecimal value, @NotNull ValueContext context) {
        return value?.toDouble()
    }
}
