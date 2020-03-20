package com.vaadin.starter.beveragebuddy

import com.github.mvysny.kaributesting.v10.SearchSpecJ
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.starter.beveragebuddy.ui.EditorDialogFrame
import org.junit.jupiter.api.Test

import java.util.function.Consumer

import static com.github.mvysny.kaributesting.v10.LocatorJ._assertNone
import static com.github.mvysny.kaributesting.v10.LocatorJ._click
import static com.github.mvysny.kaributesting.v10.LocatorJ._get

class ReviewsListTest extends AbstractAppTest {

    @Test void "'new review' smoke test"() {
        UI.getCurrent().navigate("")
        _click(_get(Button, new Consumer<SearchSpecJ<Button>>() {
            @Override
            void accept(SearchSpecJ<Button> spec) {
                spec.withCaption("New review")
            }
        }))

        // the dialog should have been opened
        _get(EditorDialogFrame)

        // this is just a smoke test, so let's close the dialog
        _click(_get(Button, new Consumer<SearchSpecJ<Button>>() {
            @Override
            void accept(SearchSpecJ<Button> spec) {
                spec.withCaption("Cancel")
            }
        }))

        _assertNone(EditorDialogFrame)
    }
}
