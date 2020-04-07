package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.github.mvysny.vaadingroovybuilder.v14.TimeZoneUtils
import com.vaadin.flow.component.HasValue
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter
import com.vaadin.flow.data.converter.LocalDateToDateConverter
import com.vaadin.flow.data.converter.StringToBigDecimalConverter
import com.vaadin.flow.data.converter.StringToBigIntegerConverter
import com.vaadin.flow.data.converter.StringToDoubleConverter
import com.vaadin.flow.data.converter.StringToIntegerConverter
import com.vaadin.flow.data.converter.StringToLongConverter
import com.vaadin.flow.data.validator.EmailValidator
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

import java.lang.reflect.Method
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.karibuDslI18n

/**
 * A set of utility functions for {@link Binder}.
 * @author mavi
 */
@CompileStatic
class BinderUtils {
    /**
     * Trims the user input string before storing it into the underlying property data source. Vital for mobile-oriented apps:
     * Android keyboard often adds whitespace to the end of the text when auto-completion occurs. Imagine storing a username ending with a space upon registration:
     * such person can no longer log in from his PC unless he explicitly types in the space.
     */
    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, String> trimmingConverter(@NotNull Binder.BindingBuilder<BEAN, String> self) {
        self.withConverter(new Converter<String, String>() {
            @Override
            Result<String> convertToModel(String value, ValueContext context) {
                Result.ok(value?.trim())
            }

            @Override
            String convertToPresentation(String value, ValueContext context) {
                // must not return null here otherwise TextField will fail with NPE:
                // workaround for https://github.com/vaadin/framework/issues/8664
                return value == null ? "" : value
            }
        })
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Integer> toInt(@NotNull Binder.BindingBuilder<BEAN, String> self) {
        self.withConverter(new StringToIntegerConverter(karibuDslI18n.apply("cantConvertToInteger")))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Integer> toInt2(@NotNull Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToIntegerConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Double> toDouble(
            @NotNull Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToDecimal")
    ) {
        self.withConverter(new StringToDoubleConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Long> toLong(
            @NotNull Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToInteger")
    ) {
        self.withConverter(new StringToLongConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Long> toLong2(@NotNull Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToLongConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigDecimal> toBigDecimal(
            @NotNull Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToDecimal")
    ) {
        self.withConverter(new StringToBigDecimalConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigDecimal> toBigDecimal2(@NotNull Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToBigDecimalConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigInteger> toBigInteger(
            @NotNull Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToInteger")
    ) {
        self.withConverter(new StringToBigIntegerConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigInteger> toBigInteger2(@NotNull Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToBigIntegerConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Date> toDate(@NotNull Binder.BindingBuilder<BEAN, LocalDate> self) {
        self.withConverter(new LocalDateToDateConverter(TimeZoneUtils.getBrowserTimeZone()))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Date> toDate2(@NotNull Binder.BindingBuilder<BEAN, LocalDateTime> self) {
        self.withConverter(new LocalDateTimeToDateConverter(TimeZoneUtils.getBrowserTimeZone()))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Instant> toInstant(@NotNull Binder.BindingBuilder<BEAN, LocalDate> self) {
        self.withConverter(new LocalDateToInstantConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Instant> toInstant2(@NotNull Binder.BindingBuilder<BEAN, LocalDateTime> self) {
        self.withConverter(new LocalDateTimeToInstantConverter())
    }

    /**
     * Allows you to bind the component directly in the component's definition. E.g.
     * ```
     * textField("Name:") {
     *   bind(binder).trimmingConverter().bind("name")
     * }
     * ```
     */
    static <BEAN, FIELDVALUE> Binder.BindingBuilder<BEAN, FIELDVALUE> bind(
            @NotNull final HasValue<? extends HasValue.ValueChangeEvent<FIELDVALUE>, FIELDVALUE> self,
            @NotNull Binder<BEAN> binder
    ) {
        def builder = binder.forField(self)
        if (self instanceof TextField || self instanceof TextArea || self instanceof EmailField) {
            builder = builder.withNullRepresentation("" as FIELDVALUE)
        }
        builder
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, String> validateNotBlank(
            @NotNull Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = "must not be blank"
    ) {
        self.withValidator(new StringNotBlankValidator(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, String> validEmail(
            @NotNull Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = "must be a valid email address"
    ) {
        self.withValidator(new EmailValidator(errorMessage))
    }

    @NotNull
    static <T extends Comparable, BEAN> Binder.BindingBuilder<BEAN, T> inRange(
            @NotNull Binder.BindingBuilder<BEAN, T> self,
            @NotNull Range<T> range,
            @NotNull String errorMessage = "must be in $range"
    ) {
        self.withValidator(new GroovyRangeValidator(range, errorMessage))
    }

    /**
     * Guesses whether the binder has been configured with read-only.
     *
     * Since Binder doesn't remember whether it is read-only, we have to guess.
     */
    static boolean guessIsReadOnly(@NotNull Binder<?> self) {
        Method bindingsGetter = Binder.class.getDeclaredMethod("getBindings")
        bindingsGetter.setAccessible(true)
        Collection<Binder.Binding<?, ?>> bindings = bindingsGetter.invoke(self) as Collection<Binder.Binding<?, ?>>;
        return bindings.any { it.setter != null && it.isReadOnly() }
    }
}
