package com.github.mvysny.vaadingroovybuilder.v14

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import static kotlin.test.AssertionsKt.expect

@CompileStatic
class GetterTest {
    @Test
    void testToString() {
        expect("Getter{public boolean java.lang.String.isEmpty()}") { new Getter(String, "empty").toString() }
    }

    @Test
    void testEquals() {
        expect(true) { new Getter(String, "empty").equals(new Getter(String, "empty")) }
        expect(false) { new Getter(String, "empty").equals(new Getter(String, "class")) }
    }
}
