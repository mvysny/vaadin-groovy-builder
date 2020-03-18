package com.github.mvysny.vaadingroovybuilder.v14

import org.jetbrains.annotations.NotNull

import java.util.function.Supplier

class Utils {

    /**
     * Throws an IllegalArgumentException with the result of calling lazyMessage if the value is false.
     */
    static def require(boolean value, @NotNull Supplier<Object> lazyMessage) {
        if (!value) {
            def message = lazyMessage()
            throw new IllegalArgumentException(message.toString())
        }
    }

}
