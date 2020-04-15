package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@CompileStatic
class DoubleToLongConverter implements Converter<Double, Long> {
    @Override
    Result<Long> convertToModel(@Nullable Double value, @NotNull ValueContext context) {
        return Result.ok(value?.toLong())
    }

    @Override
    Double convertToPresentation(@Nullable Long value, @NotNull ValueContext context) {
        return value?.toDouble()
    }
}
