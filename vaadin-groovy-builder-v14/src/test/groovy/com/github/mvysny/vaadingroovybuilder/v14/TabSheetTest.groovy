package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.tabs.Tab
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static com.github.mvysny.kaributesting.v10.groovy.LocatorG.*
import static kotlin.test.AssertionsKt.expect
import static org.junit.jupiter.api.Assertions.fail

@CompileStatic
class TabSheetTest {
    @BeforeEach
    void setup() { MockVaadin.setup() }

    @AfterEach
    void tearDown() { MockVaadin.tearDown() }

    @Test
    void "smoke"() {
        UI.getCurrent().tabSheet {}
        _expectOne(TabSheet)
    }

    @Test
    void "Initially empty"() {
        def th = new TabSheet()
        expect(-1) { th.selectedIndex }
        expect(null) { th.selectedTab }
        expect([]) { th.tabs }
        expect(0) { th.tabCount }
    }

    @Test
    void "Adding a tab to an empty tabhost selects it immediately"() {
        Tab tab2
        def th = UI.getCurrent().tabSheet {
            tab2 = tab("foo") {
                span("it works!") {}
            }
        }
        expect(0) { th.selectedIndex }
        expect(tab2) { th.selectedTab }
        expect([tab2]) { th.tabs }
        expect(1) { th.tabCount }
        _expectOne(Span) { text = "it works!" }
    }

    @Test
    void "Adding a tab to a non-empty tabhost doesn't change the selection"() {
        Tab tab1
        Tab tab2
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                span("it works!") {}
            }
            tab2 = tab("bar") {
                span("it works 2!") {}
            }
        }
        expect(0) { th.selectedIndex }
        expect(tab1) { th.selectedTab }
        expect([tab1, tab2]) { th.tabs }
        expect(2) { th.tabCount }
        _expectOne(Span) { text = "it works!" }
    }

    @Test
    void "Adding a tab with null contents works"() {
        Tab tab
        def th = UI.getCurrent().tabSheet {
            tab = addTab("foo")
        }
        expect(0) { th.selectedIndex }
        expect(tab) { th.selectedTab }
        expect([tab]) { th.tabs }
        expect(1) { th.tabCount }
        expect(0) { tab.index }

        th.setTabContents(tab, new Span("it works!"))
        _expectOne(Span) { text = "it works!" }
    }

    @Test
    void "Removing last tab clears selection"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                span("it works!") {}
            }
        }
        th.remove(tab1)

        expect(-1) { th.selectedIndex }
        expect(null) { th.selectedTab }
        expect([]) { th.tabs }
        expect(0) { th.tabCount }
        _expectNone(Span) { text = "it works!" }
    }

    @Test
    void "Removing all tabs clears selection"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                span("it works!") {}
            }
        }
        th.removeAll()

        expect(-1) { th.selectedIndex }
        expect(null) { th.selectedTab }
        expect([]) { th.tabs }
        expect(0) { th.tabCount }
        _expectNone(Span) { text = "it works!" }
    }

    @Test
    void "owner"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                span("it works!") {}
            }
        }
        expect(th) { tab1.ownerTabSheet }
        tab1.owner // check that the tab has an owner
    }

    @Test
    void "tab contents - non-empty contents"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                span("it works!") {}
            }
        }
        expect(Span) { tab1.contents.class }
    }

    @Test
    void "tab contents - clearing contents"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                span("it works!") {}
            }
        }
        expect(Span) { tab1.contents.class }
        tab1.contents = null
        _expectNone(Span)
        expect(null) { tab1.contents }
    }

    @Test
    void "find contents - empty tab"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo")
        }
        expect(null) { tab1.contents }
        expect(null) { th.findTabWithContents(new Span("bar")) }
    }

    @Test
    void "find contents - simple test"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                span("it works!") {}
            }
        }
        expect(tab1) { th.findTabWithContents(tab1.contents) }
    }

    @Test
    void "find tab containing - empty tab"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo")
        }
        expect(null) { th.findTabContaining(new Span("bar")) }
    }

    @Test
    void "find tab containing - simple test"() {
        Tab tab1
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") { span("it works!") {} }
        }
        expect(tab1) { th.findTabContaining(tab1.contents) }
    }

    @Test
    void "find tab containing - complex test"() {
        Tab tab1
        Button nested
        def th = UI.getCurrent().tabSheet {
            tab1 = tab("foo") {
                div {
                    div {
                        nested = button {}
                    }
                }
            }
        }
        expect(tab1) { th.findTabContaining(nested) }
    }

    @Test
    void addFirstLazyTabImmediatelyInvokesClosure() {
        def th = UI.current.tabSheet {}
        def producedLabel = new Label("baz")
        th.addLazyTab("foo") { producedLabel }
        expect(producedLabel) { th.selectedTab.contents }
    }

    @Test
    void addSecondLazyTabDelaysClosure() {
        def th = UI.current.tabSheet {}
        def producedLabel = new Label("baz")
        boolean allowInvoking = false
        def tab1 = th.addTab("bar")
        def tab2 = th.addLazyTab("foo") {
            if (!allowInvoking) fail("Should not invoke")
            producedLabel
        }
        expect(tab1) { th.selectedTab }
        allowInvoking = true

        th.selectedTab = tab2
        expect(tab2) { th.selectedTab }
        expect(producedLabel) { th.selectedTab.contents }
    }
}
