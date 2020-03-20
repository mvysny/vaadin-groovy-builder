package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author mavi
 */
@CompileStatic
class ContextMenuTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test
    void "smoke"() {
        UI.getCurrent().button {
            contextMenu {
                item("save", { _ -> println("saved") }) {}
                item("style", null) {
                    item("bold", { _ -> println("bold") }) {}
                    item("italic", { _ -> println("italic") }) {}
                }
                item("clear", { _ -> println("clear") }) {}
            }
        }
    }

    @Test
    void "grid - smoke"() {
        UI.getCurrent().grid(String) {
            gridContextMenu {
                item("save", { _ -> println("saved") }) {}
                item("style", null) {
                    item("bold", { _ -> println("bold") }) {}
                    item("italic", { _ -> println("italic") }) {}
                }
                item("clear", { _ -> println("clear") }) {}
            }
        }
    }
}
