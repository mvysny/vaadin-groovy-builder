package com.github.mvysny.vaadingroovybuilder.v14.layout

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.FlexComponent
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
        new Button().flexGrow = 1d
    }

    @Test
    void "setting flexGrow works"() {
        def button = new Button()
        button.flexGrow = 1d
        expect("1.0") { button.element.style.get("flexGrow") }
        button.flexGrow = 0d
        expect("0.0") { button.element.style.get("flexGrow") }
    }

    @Test
    void "getting flexGrow works"() {
        def button = new Button()
        button.element.style.set("flexGrow", "25")
        expect(25d) { button.flexGrow }
    }

    @Test
    void "setting flexGrow to null removes the style"() {
        def button = new Button()
        button.element.style.set("flexGrow", "25")
        button.flexGrow = null
        expect(null) { button.element.style.get("flexGrow") }
    }

    @Test
    void testExpand() {
        def button = new Button()
        expect(false) { button.isExpand() }
        button.setExpand(true)
        expect(true) { button.isExpand() }
        button.setExpand(false)
        expect(false) { button.isExpand() }
    }

    @Test
    void "by default component has flexGrow of null"() {
        expect(null) { new Button().flexGrow }
    }

    @Test void "flexShrink"() {
        def button = new Button()
        expect(null) { button.flexShrink }
        button.flexShrink = 0d
        expect("0.0") { button.element.style.get("flexShrink") }
        expect(0d) { button.flexShrink }
        button.flexShrink = 1d
        expect(1.0d) { button.flexShrink }
        expect("1.0") { button.element.style.get("flexShrink") }
        button.flexShrink = null
        expect(null) { button.flexShrink }
        expect(null) { button.element.style.get("flexShrink") }
    }

    @Test void "flexBasis"() {
        def button = new Button()
        expect(null) { button.flexBasis }
        button.flexBasis = "auto"
        expect("auto") { button.flexBasis }
        button.flexBasis = null
        expect(null) { button.flexBasis }
        expect(null) { button.element.style.get("flexBasis") }
    }

    @Test void "alignSelf"() {
        Button btn
        UI.getCurrent().verticalLayout {
            btn = button {}
        }
        expect(FlexComponent.Alignment.AUTO) { btn.alignSelf }
        btn.alignSelf = FlexComponent.Alignment.END
        expect(FlexComponent.Alignment.END) { btn.alignSelf }
        btn.alignSelf = null
        expect(FlexComponent.Alignment.AUTO) { btn.alignSelf }
        expect(null) { btn.element.style.get("alignSelf") }
    }
}
