package com.vaadin.starter.beveragebuddy.ui

import com.vaadin.flow.component.UI
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

@CompileStatic
class ComponentsShowcaseTest extends AbstractAppTest {
    @Test void smoke() {
        UI.current.navigate(ComponentsShowcase)
    }
}
