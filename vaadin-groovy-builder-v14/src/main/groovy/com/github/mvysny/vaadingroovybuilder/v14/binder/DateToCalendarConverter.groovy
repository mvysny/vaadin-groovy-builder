package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic

/**
 * Converts {@link Date} to {@link Calendar}-typed bean field. Append to {@link com.vaadin.flow.data.converter.LocalDateTimeToDateConverter} or
 * {@link com.vaadin.flow.data.converter.LocalDateToDateConverter} to allow for LocalDate-to-Calendar conversions.
 */
@CompileStatic
class DateToCalendarConverter implements Converter<Date, Calendar> {
    @Override
    Result<Calendar> convertToModel(Date value, ValueContext context) {
        if (value == null) {
            return Result.<Calendar>ok(null)
        }
        def cal = Calendar.instance
        cal.setTime(value)
        return Result.ok(cal)
    }

    @Override
    Date convertToPresentation(Calendar value, ValueContext context) {
        return value?.time
    }
}
