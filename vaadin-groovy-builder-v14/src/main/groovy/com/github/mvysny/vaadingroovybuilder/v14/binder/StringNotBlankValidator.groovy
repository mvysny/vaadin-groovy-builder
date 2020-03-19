package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.ValidationResult
import com.vaadin.flow.data.binder.Validator
import com.vaadin.flow.data.binder.ValueContext
import groovy.transform.CompileStatic
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@CompileStatic
@TupleConstructor
@ToString
class StringNotBlankValidator implements Validator<String> {
    String errorMessage = "must not be blank"

    @Override
    ValidationResult apply(String value, ValueContext context) {
        value == null || value.isAllWhitespace() ? ValidationResult.error(errorMessage) : ValidationResult.ok()
    }
}
