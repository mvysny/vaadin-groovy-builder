package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.UI
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static kotlin.test.AssertionsKt.expect

/**
 * @author mavi
 */
@CompileStatic
class TreeIteratorTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test void walk() {
        def expected = new HashSet()
        def root = UI.getCurrent().verticalLayout {
            expected.add(delegate)
            button("Foo") {
                expected.add(delegate)
            }
            horizontalLayout {
                expected.add(delegate)
                label {
                    expected.add(delegate)
                }
            }
            verticalLayout {
                expected.add(delegate)
            }
        }
        expect(expected) { root.walk().toSet() }
        expect(root) { root.walk().toList()[0] }
    }
}
