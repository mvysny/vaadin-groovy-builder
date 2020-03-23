package com.github.mvysny.vaadingroovybuilder.v14.layout

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static kotlin.test.AssertionsKt.expect

/**
 * @author mavi
 */
@CompileStatic
class LayoutsTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test
    void smoke() {
        UI.getCurrent().flexLayout {
            verticalLayout {}
            horizontalLayout {}
        }
    }

    @Test
    void "flexGrow works even when the component is not yet attached to parent"() {
        new Button().flexGrow = 1.0
    }

    @Test
    void "setting flexGrow works"() {
        def button = new Button()
        button.flexGrow = 1.0
        expect("1.0") { button.element.style.get("flexGrow") }
    }

    @Test
    void "getting flexGrow works"() {
        def button = new Button()
        button.element.style.set("flexGrow", "25")
        expect(25d) { button.flexGrow }
    }

    @Test
    void "setting flexGrow to 0 removes the style"() {
        def button = new Button()
        button.element.style.set("flexGrow", "25")
        button.flexGrow = 0.0
        expect(null) { button.element.style.get("flexGrow") }
    }

    @Test
    void "by default component has flexGrow of 0"() {
        expect(0d) { new Button().flexGrow }
    }
}
