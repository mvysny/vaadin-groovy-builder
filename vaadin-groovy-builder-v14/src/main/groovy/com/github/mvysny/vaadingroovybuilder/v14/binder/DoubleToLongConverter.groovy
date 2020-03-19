package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic

@CompileStatic
class DoubleToLongConverter implements Converter<Double, Long> {
    @Override
    Result<Long> convertToModel(Double value, ValueContext context) {
        return Result.ok(value?.toLong())
    }

    @Override
    Double convertToPresentation(Long value, ValueContext context) {
        return value?.toDouble()
    }
}
