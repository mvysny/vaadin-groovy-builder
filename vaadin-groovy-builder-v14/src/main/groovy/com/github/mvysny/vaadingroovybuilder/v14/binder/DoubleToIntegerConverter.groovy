package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic

@CompileStatic
class DoubleToIntegerConverter implements Converter<Double, Integer> {
    @Override
    Result<Integer> convertToModel(Double value, ValueContext context) {
        return Result.ok(value?.toInteger())
    }

    @Override
    Double convertToPresentation(Integer value, ValueContext context) {
        return value?.toDouble()
    }
}
