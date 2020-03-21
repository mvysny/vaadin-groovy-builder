package com.vaadin.starter.beveragebuddy.ui.reviews

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.starter.beveragebuddy.ui.AbstractAppTest
import com.vaadin.starter.beveragebuddy.ui.EditorDialogFrame
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import static com.github.mvysny.kaributesting.v10.groovy.LocatorG._expectNone
import static com.github.mvysny.kaributesting.v10.groovy.LocatorG._get

@CompileStatic
class ReviewsListTest extends AbstractAppTest {

    @Test void "'new review' smoke test"() {
        UI.getCurrent().navigate("")
        _get(Button) { caption = "New review" } ._click()

        // the dialog should have been opened
        _get(EditorDialogFrame)

        // this is just a smoke test, so let's close the dialog
        _get(Button) { caption = "Cancel" } ._click()

        _expectNone(EditorDialogFrame)
    }
}
