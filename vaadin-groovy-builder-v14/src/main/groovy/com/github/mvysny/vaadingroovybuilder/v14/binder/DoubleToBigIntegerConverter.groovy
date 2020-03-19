package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic

@CompileStatic
class DoubleToBigIntegerConverter implements Converter<Double, BigInteger> {
    @Override
    Result<BigInteger> convertToModel(Double value, ValueContext context) {
        return Result.ok(value?.toBigInteger())
    }

    @Override
    Double convertToPresentation(BigInteger value, ValueContext context) {
        return value?.toDouble()
    }
}
