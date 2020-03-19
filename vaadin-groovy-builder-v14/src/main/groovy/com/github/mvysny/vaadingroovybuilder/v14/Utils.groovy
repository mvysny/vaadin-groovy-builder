package com.github.mvysny.vaadingroovybuilder.v14

import org.jetbrains.annotations.NotNull

import java.util.function.Function
import java.util.function.Supplier

class Utils {

    /**
     * Throws an IllegalArgumentException with the result of calling lazyMessage if the value is false.
     */
    static void require(boolean value, @NotNull Supplier<Object> lazyMessage) {
        if (!value) {
            def message = lazyMessage()
            throw new IllegalArgumentException(message.toString())
        }
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
