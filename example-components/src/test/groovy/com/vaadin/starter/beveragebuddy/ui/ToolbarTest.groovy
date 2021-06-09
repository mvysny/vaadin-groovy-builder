package com.vaadin.starter.beveragebuddy.ui

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@CompileStatic
class ToolbarTest {
    @BeforeEach
    void mockVaadin() {
        MockVaadin.setup()
    }

    @AfterEach
    void tearDownVaadin() {
        MockVaadin.tearDown()
    }

    @Test
    void smoke() {
        UI.getCurrent().add(new Toolbar("foo"))
        UI.getCurrent().toolbar("foo") {}
    }
}
