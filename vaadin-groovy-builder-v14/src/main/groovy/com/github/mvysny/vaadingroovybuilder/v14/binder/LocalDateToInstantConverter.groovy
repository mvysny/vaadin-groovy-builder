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
import java.time.LocalDate
import java.time.ZoneId

/**
 * A converter that converts between [LocalDate] and [Instant].
 * @property zoneId the time zone id to use.
 */
@TupleConstructor
@CompileStatic
class LocalDateToInstantConverter implements Converter<LocalDate, Instant> {
    ZoneId zoneId = TimeZoneUtils.browserTimeZone

    @Override
    Result<Instant> convertToModel(@Nullable LocalDate value, @NotNull ValueContext context) {
        return Result.ok(value?.atStartOfDay(zoneId)?.toInstant())
    }

    @Override
    LocalDate convertToPresentation(@Nullable Instant value, @NotNull ValueContext context) {
        return value?.atZone(zoneId)?.toLocalDate()
    }
}
