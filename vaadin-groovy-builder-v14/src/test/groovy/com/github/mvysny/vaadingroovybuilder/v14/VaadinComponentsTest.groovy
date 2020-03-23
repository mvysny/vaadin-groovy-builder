package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.Scroller
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static kotlin.test.AssertionsKt.expect

/**
 * @author mavi
 */
@CompileStatic
class VaadinComponentsTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test
    void smoke() {
        UI.getCurrent().flexLayout {
            button("Foo", null) {
                setPrimary()
            }
            iconButton(VaadinIcon.LEVEL_LEFT.create()) {
                addClickListener {  }
            }
            checkbox{}
            comboBox(String, null) {
                setItems(["foo", "bar"])
            }
            select(String) {
                setItems(["foo", "bar"])
            }
            datePicker{}
            dialog{}
            icon(VaadinIcon.TRASH)
            passwordField{}
            splitLayout{}
            textField{}
            emailField{}
            numberField{}
            textArea{}
            tabs {
                tab{}
            }
            checkboxGroup{}
            timePicker{}
            integerField{}
            bigDecimalField{}
            dateTimePicker {}
        }
    }

    @Test void scroller() {
        Scroller scroller = UI.getCurrent().scroller {  }
        expect(null) { scroller.content }
        Span s = scroller.content { span("Foo"){} }
        expect(s) { scroller.content }
        scroller._expectOne(Span)

        // test the API - compiling the following shouldn't fail
        scroller.content { span("Bar"){} }
    }

    @Test menuBar() {
        UI.getCurrent().menuBar {
            item("save", { _ -> println("saved") }) {}
            item("style", null) {
                item("bold", { _ -> println("bold") })  {}
                item("italic", { _ -> println("italic") })  {}
            }
            item("clear", { _ -> println("clear") }) {}
        }
    }
}
