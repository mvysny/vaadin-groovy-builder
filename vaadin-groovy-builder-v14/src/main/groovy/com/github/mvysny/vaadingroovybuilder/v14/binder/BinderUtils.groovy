package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.flow.data.converter.Converter
import com.vaadin.flow.data.converter.StringToBigDecimalConverter
import com.vaadin.flow.data.converter.StringToBigIntegerConverter
import com.vaadin.flow.data.converter.StringToDoubleConverter
import com.vaadin.flow.data.converter.StringToIntegerConverter
import com.vaadin.flow.data.converter.StringToLongConverter
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

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
    static <BEAN> Binder.BindingBuilder<BEAN, String> trimmingConverter(Binder.BindingBuilder<BEAN, String> self) {
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
    static <BEAN> Binder.BindingBuilder<BEAN, Integer> toInt(Binder.BindingBuilder<BEAN, String> self) {
        self.withConverter(new StringToIntegerConverter(karibuDslI18n.apply("cantConvertToInteger")))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Integer> toInt2(Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToIntegerConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Double> toDouble(
            Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToDecimal")
    ) {
        self.withConverter(new StringToDoubleConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Long> toLong(
            Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToInteger")
    ) {
        self.withConverter(new StringToLongConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, Long> toLong2(Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToLongConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigDecimal> toBigDecimal(
            Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToDecimal")
    ) {
        self.withConverter(new StringToBigDecimalConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigDecimal> toBigDecimal2(Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToBigDecimalConverter())
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigInteger> toBigInteger(
            Binder.BindingBuilder<BEAN, String> self,
            @NotNull String errorMessage = karibuDslI18n.apply("cantConvertToInteger")
    ) {
        self.withConverter(new StringToBigIntegerConverter(errorMessage))
    }

    @NotNull
    static <BEAN> Binder.BindingBuilder<BEAN, BigInteger> toBigInteger2(Binder.BindingBuilder<BEAN, Double> self) {
        self.withConverter(new DoubleToBigIntegerConverter())
    }

    // @todo mavi more, plus tests
}
