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
class AccordionTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test void smoke() {
        UI.getCurrent().accordion {
            panel("lorem ipsum") {
                content {
                    label("dolor sit amet"){}
                }
            }
            panel {
                summary { checkbox("More Lorem Ipsum?"){} }
                content {
                    label("dolor sit amet"){}
                }
            }
        }
    }
}
