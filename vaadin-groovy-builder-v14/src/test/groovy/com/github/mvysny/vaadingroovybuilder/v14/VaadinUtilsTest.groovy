package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.orderedlayout.FlexLayout
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static kotlin.test.AssertionsKt.expect
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull

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

    @Test
    void tooltip() {
        def b = new Button()
        assertEquals(null, b.tooltip)
        b.tooltip = ""
        assertEquals("", b.tooltip)
        b.tooltip = "foo"
        assertEquals("foo", b.tooltip)
        b.tooltip = null
        assertEquals(null, b.tooltip)
    }

    @Test
    void "addContextMenuListener smoke"() {
        new Button().addContextMenuListener {}
    }

    @Test
    void "removeFromParent - component with no parent"() {
        def t = new Text("foo")
        t.removeFromParent()
        assertEquals(null, t.parent.orElse(null))
    }

    @Test
    void "removeFromParent - nested component"() {
        def fl = new FlexLayout()
        fl.add(new Label("foo"))
        def label = fl.getComponentAt(0)
        assertEquals(fl, label.parent.get())
        label.removeFromParent()
        assertNull(label.parent.orElse(null))
        expect(0) { fl.componentCount }
    }

    @Test
    void "toggle class name - add"() {
        def t = new Div()
        t.classNames.toggle("test")
        expect(["test"].toSet()) { t.classNames }
    }

    @Test
    void "toggle class name - remove"() {
        def t = new Div()
        t.classNames.add("test")
        t.classNames.toggle("test")
        expect(new HashSet<String>()) { t.classNames }
    }

    @Test
    void "findAncestor - null on no parent"() {
        expect(null) { new Button().findAncestor { false } }
    }

    @Test
    void "findAncestor - null on no acceptance"() {
        expect(null) { UI.getCurrent().button().findAncestor { false } }
    }

    @Test
    void "findAncestor - finds UI"() {
        expect(UI.getCurrent()) { UI.getCurrent().button().findAncestor { it instanceof UI } }
    }

    @Test
    void "findAncestor - doesn't find self"() {
        expect(UI.getCurrent()) { UI.getCurrent().button().findAncestor { true } }
    }

    @Test
    void "findAncestorOrSelf - null on no parent"() {
        expect(null) { new Button().findAncestorOrSelf { false } }
    }

    @Test
    void "findAncestorOrSelf - null on no acceptance"() {
        expect(null) { UI.getCurrent().button().findAncestorOrSelf { false } }
    }

    @Test
    void "findAncestorOrSelf - finds self"() {
        def button = UI.getCurrent().button()
        expect(button) { button.findAncestorOrSelf { true } }
    }

    @Test
    void isNestedIn() {
        expect(false) { new Button().isNestedIn(UI.getCurrent()) }
        expect(true) { UI.getCurrent().button().isNestedIn(UI.getCurrent()) }
    }
}
