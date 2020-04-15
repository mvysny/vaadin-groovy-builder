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
@ToString
class GroovyRangeValidator<T extends Comparable> implements Validator<T> {

    @NotNull final String errorMessage
    @NotNull final Range<T> range

    GroovyRangeValidator(@NotNull Range<T> range, @NotNull String errorMessage = "must be in $range") {
        this.errorMessage = errorMessage
        this.range = range
    }

    @Override
    ValidationResult apply(@Nullable T value, @NotNull ValueContext context) {
        value != null && !range.contains(value) ? ValidationResult.error(errorMessage) : ValidationResult.ok()
    }
}
