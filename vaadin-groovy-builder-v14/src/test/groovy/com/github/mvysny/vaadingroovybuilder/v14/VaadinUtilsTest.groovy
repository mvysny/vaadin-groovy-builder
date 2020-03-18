package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * @author mavi
 */
@CompileStatic
class VaadinUtilsTest {
    @BeforeEach
    void setup() { MockVaadin.setup() }

    @AfterEach
    void tearDown() { MockVaadin.tearDown() }

    @Test
    void serverClick() {
        def b = new Button()
        int clicked = 0
        b.addClickListener { clicked++ }
        b.serverClick()
        assertEquals(1, clicked as int)
    }

    @Test
    void text() {
        Text t = UI.getCurrent().text("foo")
        assertEquals("foo", t.text)
        assertEquals(UI.getCurrent(), t.parent.get())
    }

    @Test
    void setOrRemoveAttribute() {
        def t = new Div().element
        assertEquals(null, t.getAttribute("foo"))
        t.setOrRemoveAttribute("foo", "bar")
        assertEquals("bar", t.getAttribute("foo"))
        t.setOrRemoveAttribute("foo", null)
        assertEquals(null, t.getAttribute("foo"))
    }
}
