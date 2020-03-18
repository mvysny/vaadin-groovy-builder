package com.vaadin.starter.beveragebuddy.backend

import com.vaadin.flow.templatemodel.ModelEncoder
import groovy.transform.CompileStatic

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Converts between DateTime-objects and their String-representations
 */
@CompileStatic
class LocalDateToStringConverter implements ModelEncoder<LocalDate, String> {

    @Override
    LocalDate decode(String value) {
        value == null ? null : LocalDate.parse(value, DATE_FORMAT)
    }

    @Override
    String encode(LocalDate value) {
        value?.format(DATE_FORMAT)
    }

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy")
}
