package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.github.mvysny.vaadingroovybuilder.v14.TimeZoneUtils
import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * A converter that converts from {@link LocalDateTime} {@link com.vaadin.flow.component.datetimepicker.DateTimePicker}
 * to {@link Instant} bean field.
 */
@TupleConstructor
@CompileStatic
class LocalDateTimeToInstantConverter implements Converter<LocalDateTime, Instant> {
    /**
     * the time zone id to use, defaults to {@link TimeZoneUtils#getBrowserTimeZone()}
     */
    ZoneId zoneId = TimeZoneUtils.browserTimeZone

    @Override
    Result<Instant> convertToModel(@Nullable LocalDateTime value, @NotNull ValueContext context) {
        return Result.ok(value?.atZone(zoneId)?.toInstant())
    }

    @Override
    LocalDateTime convertToPresentation(@Nullable Instant value, @NotNull ValueContext context) {
        return value?.atZone(zoneId)?.toLocalDateTime()
    }
}
