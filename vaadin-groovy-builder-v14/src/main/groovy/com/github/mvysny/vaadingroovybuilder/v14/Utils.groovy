package com.github.mvysny.vaadingroovybuilder.v14

import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.util.function.Function
import java.util.function.Supplier

@CompileStatic
class Utils {

    /**
     * Throws an {@link IllegalArgumentException} with the result of calling lazyMessage if the value is false.
     */
    static void require(boolean value, @NotNull Supplier<Object> lazyMessage) {
        if (!value) {
            def message = lazyMessage.get()
            throw new IllegalArgumentException(message.toString())
        }
    }

    /**
     * Throws an {@link IllegalStateException} with the result of calling lazyMessage if the value is false.
     */
    static void check(boolean value, @NotNull Supplier<Object> lazyMessage) {
        if (!value) {
            def message = lazyMessage.get()
            throw new IllegalStateException(message.toString())
        }
    }

    /**
     * Throws an {@link IllegalStateException} with the result of calling lazyMessage if the value is null.
     */
    @NotNull
    static <T> T checkNotNull(@Nullable T value, @NotNull Supplier<Object> lazyMessage = { "value must not be null" }) {
        if (value == null) {
            def message = lazyMessage.get()
            throw new IllegalStateException(message.toString())
        }
        value
    }

    private static final Map<String, String> messages = ["cantConvertToInteger": "Can't convert to integer",
            "cantConvertToDecimal": "Can't convert to decimal number",
            "from": "From:",
            "to": "To:",
            "set": "Set",
            "clear": "Clear",
            "all": "All"
    ]

    /**
     * Change this function to provide a proper i18n for your apps. For a list of all keys used by Karibu-DSL see {@link #messages}.
     */
    static Function<String, String> karibuDslI18n = new Function<String, String>() {
        @Override
        String apply(String key) {
            return messages.getOrDefault(key, key)
        }
    }
}
