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
class AppLayoutExtensionMethodsTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test void smoke() {
        def a = UI.getCurrent().appLayout {
            navbar {
                drawerToggle{}
                h3("My App"){}
            }
            drawer {
                routerLink(VaadinIcon.ARCHIVE, "Foo"){}
                routerLink(VaadinIcon.ACADEMY_CAP, "Bar"){}
            }
            content {
                span("Hello world!"){}
            }
        }
        a.cloneBySerialization()
    }
}
