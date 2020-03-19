package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic

@CompileStatic
class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {
    @Override
    Result<BigDecimal> convertToModel(Double value, ValueContext context) {
        return Result.ok(value?.toBigDecimal())
    }

    @Override
    Double convertToPresentation(BigDecimal value, ValueContext context) {
        return value?.toDouble()
    }
}
