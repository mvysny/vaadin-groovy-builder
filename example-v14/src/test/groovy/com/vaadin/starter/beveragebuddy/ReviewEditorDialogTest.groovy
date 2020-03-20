package com.vaadin.starter.beveragebuddy

import com.github.mvysny.kaributesting.v10.SearchSpecJ
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.starter.beveragebuddy.ui.EditorDialogFrame
import com.vaadin.starter.beveragebuddy.ui.reviews.ReviewEditorDialog
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import java.util.function.Consumer

import static com.github.mvysny.kaributesting.v10.LocatorJ.*
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
        _assertOne(EditorDialogFrame)
        _click(_get(Button, new Consumer<SearchSpecJ<Button>>() {
            @Override
            void accept(SearchSpecJ<Button> spec) {
                spec.withCaption("Save")
            }
        }))
        _assertOne(EditorDialogFrame)
        expect("must not be blank") {
            _get(TextField, new Consumer<SearchSpecJ<TextField>>() {
                @Override
                void accept(SearchSpecJ<TextField> spec) {
                    spec.withCaption("Beverage name")
                }
            }).errorMessage
        }
    }
}
