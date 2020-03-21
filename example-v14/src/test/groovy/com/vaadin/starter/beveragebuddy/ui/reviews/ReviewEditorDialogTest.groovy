package com.vaadin.starter.beveragebuddy.ui.reviews

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.starter.beveragebuddy.ui.AbstractAppTest
import com.vaadin.starter.beveragebuddy.ui.EditorDialogFrame
import com.vaadin.starter.beveragebuddy.ui.reviews.ReviewEditorDialog
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import static com.github.mvysny.kaributesting.v10.groovy.LocatorG.*
import static kotlin.test.AssertionsKt.expect

@CompileStatic
class ReviewEditorDialogTest extends AbstractAppTest {
    @Test
    void "smoke"() {
        new ReviewEditorDialog({}, {}).createNew()
    }

    @Test
    void "simple validation failure"() {
        new ReviewEditorDialog({}, {}).createNew()
        _expectOne(EditorDialogFrame)
        _get(Button) { caption = "Save" } ._click()
        _expectOne(EditorDialogFrame)
        expect("must not be blank") {
            _get(TextField) { caption = "Beverage name" } .errorMessage
        }
    }
}
