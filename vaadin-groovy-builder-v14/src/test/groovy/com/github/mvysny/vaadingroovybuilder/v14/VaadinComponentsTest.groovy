package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.icon.VaadinIcon
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
            checkBox()
            comboBox(null) {
                setItems(["foo", "bar"])
            }
            select()
            datePicker()
            dialog()
            icon(VaadinIcon.TRASH)
            passwordField()
            splitLayout()
            textField()
            emailField()
            numberField()
            textArea()
            tabs {
                tab()
            }
            checkboxGroup()
            timePicker()
            integerField()
            bigDecimalField()
        }
    }
}
