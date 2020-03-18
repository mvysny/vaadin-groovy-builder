package com.github.mvysny.vaadingroovybuilder.v14

import groovy.transform.CompileStatic

@CompileStatic
class TestUtils {
    static void expectThrows(Class<? extends Throwable> expected, String msg = null, Closure block) {
        boolean succeeded = false
        try {
            block()
            succeeded = true
        } catch (Throwable e) {
            if (!expected.isInstance(e)) {
                throw new AssertionError("Expected $expected but got $e", e)
            }
            if (msg != null && !e.message.contains(msg)) {
                throw new AssertionError("Expected message $msg but got $e", e)
            }
        }
        if (succeeded) {
            throw new AssertionError("Expected to fail with $expected $msg but completed successfully" as Object)
        }
    }
}
