package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.PrettyPrintTreeKt
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
class FormLayoutsTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test
    void formItems() {
        def layout = UI.current.formLayout {
            formItem("Name:") { textField{} }
        }
        expect("""└── FormLayout[]
    └── GFormItem[]
        ├── Label[text='Name:']
        └── TextField[value='']""") { PrettyPrintTreeKt.toPrettyTree(layout).trim() }
    }
}
