package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.LocatorJ
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.SearchSpecJ
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.details.Details
import com.vaadin.flow.component.html.Span
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.util.function.Consumer

import static com.github.mvysny.kaributesting.v10.LocatorJ._assertOne
import static com.github.mvysny.kaributesting.v10.LocatorJ._get
import static com.github.mvysny.kaributesting.v10.UtilsKt.expectList
import static kotlin.test.AssertionsKt.expect

/**
 * @author mavi
 */
@CompileStatic
class DetailsTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test void "smoke"() {
        UI.getCurrent().details("Hello") {
            content {
                button("hi!") {}
            }
        }
        _assertOne(Span, new Consumer<SearchSpecJ<Span>>() {
            @Override
            void accept(SearchSpecJ<Span> spec) {
                spec.withText("Hello")
            }
        })
        _assertOne(Button, new Consumer<SearchSpecJ<Button>>() {
            @Override
            void accept(SearchSpecJ<Button> spec) {
                spec.withCaption("hi!")
            }
        })
        expect("hi!") { _get(Details).content.toList().collect { Component c -> (c as Button).getText() } .join(", ") }
    }

    @Test void "summary as component"() {
        UI.getCurrent().details {
            summary {
                button("hi!"){}
            }
        }
        _assertOne(Button, new Consumer<SearchSpecJ<Button>>() {
            @Override
            void accept(SearchSpecJ<Button> spec) {
                spec.withCaption("hi!")
            }
        })
        expect("hi!") { (_get(Details).summary as Button).text }
    }

    @Test void "clear content"() {
        def d = UI.getCurrent().details() {}
        expectList(new Component[0]) { d.content.toList() }
        d.content {
            button("hi!"){}
        }
        expect(1L) { d.content.count() }
        d.clearContent()
        expectList(new Component[0]) { d.content.toList() }
    }
}
