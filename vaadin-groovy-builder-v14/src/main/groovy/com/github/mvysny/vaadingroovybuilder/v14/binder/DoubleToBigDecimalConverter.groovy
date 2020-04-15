package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

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
