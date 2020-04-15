package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.ValidationResult
import com.vaadin.flow.data.binder.Validator
import com.vaadin.flow.data.binder.ValueContext
import groovy.transform.CompileStatic
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@CompileStatic
@TupleConstructor
@ToString
class StringNotBlankValidator implements Validator<String> {
    @NotNull
    String errorMessage = "must not be blank"

    @Override
    ValidationResult apply(@Nullable String value, @NotNull ValueContext context) {
        value == null || value.isAllWhitespace() ? ValidationResult.error(errorMessage) : ValidationResult.ok()
    }
}
