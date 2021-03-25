package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.Routes
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationListener
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.Location
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouterLayout
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static com.github.mvysny.kaributesting.v10.LocatorJ._assertNone
import static com.github.mvysny.vaadingroovybuilder.v14.TestUtils.expectThrows
import static kotlin.test.AssertionsKt.expect

@CompileStatic
class AppLayout extends VerticalLayout implements RouterLayout {}

@Route("")
@CompileStatic
class RootView extends VerticalLayout {}

@Route(value = "testing", layout = AppLayout)
@CompileStatic
class TestingView extends VerticalLayout {}

@Route("testing/foo/bar")
@CompileStatic
class TestingView2 extends VerticalLayout {}

@Route("testingp")
@CompileStatic
class TestingParametrizedView extends VerticalLayout implements HasUrlParameter<Long> {
    @Override
    void setParameter(BeforeEvent event, Long parameter) {
        Objects.requireNonNull(parameter)
    }
}

@Route("testingp/with/subpath")
@CompileStatic
class TestingParametrizedView2 extends VerticalLayout implements HasUrlParameter<Long> {
    @Override
    void setParameter(BeforeEvent event, Long parameter) {
        Objects.requireNonNull(parameter)
    }
}

@CompileStatic
class RouterTest {
    @BeforeEach
    void setup() {
        MockVaadin.setup(new Routes([
                TestingView,
                TestingParametrizedView,
                RootView,
                TestingParametrizedView2,
                TestingView2
        ].toSet(), [].toSet(), true))
        _assertNone(TestingView)
        _assertNone(TestingParametrizedView)
    }

    @AfterEach
    void tearDown() { MockVaadin.tearDown() }

    @Test
    void "routerLinks - no target"() {
        expect("") { UI.getCurrent().routerLink {}.href }
    }

    @Test
    void "routerLinks - simple target"() {
        expect("testing") { UI.getCurrent().routerLink(null, "foo", TestingView){}.href }
    }

    @Test
    void "routerLinks - parametrized target with missing param"() {
        expectThrows(IllegalArgumentException, "requires a parameter") {
            UI.getCurrent().routerLink(null, "foo", TestingParametrizedView){}
        }
    }

    @Test
    void "routerLinks - parametrized target"() {
        expect("testingp/1") { UI.getCurrent().routerLink(null, "foo", TestingParametrizedView, 1L) {}.href }
    }

    @Test
    void "viewClass - navigating to root view"() {
        UI.getCurrent().navigate(TestingView)
        def called = false
        UI.getCurrent().addAfterNavigationListener(new AfterNavigationListener() {
            @Override
            void afterNavigation(AfterNavigationEvent event) {
                called = true
                expect(RootView) { event.getViewClass() }
            }
        })
        UI.getCurrent().navigate(RootView)
        expect(true) { called }
    }

    @Test
    void "viewClass - navigating to TestingView"() {
        def called = false
        UI.getCurrent().addAfterNavigationListener(new AfterNavigationListener() {
            @Override
            void afterNavigation(AfterNavigationEvent event) {
                called = true
                expect(TestingView) { event.viewClass }
            }
        })
        UI.getCurrent().navigate(TestingView)
        expect(true) { called }
    }

    @Test
    void "viewClass - navigating to TestingView2"() {
        def called = false
        UI.getCurrent().addAfterNavigationListener(new AfterNavigationListener() {
            @Override
            void afterNavigation(AfterNavigationEvent event) {
                called = true
                expect(TestingView) { event.getViewClass() }
            }
        })
        UI.getCurrent().navigate(TestingView)
        expect(true) { called }
    }

    @Test
    void "viewClass - navigating to TestingParametrizedView"() {
        def called = false
        UI.getCurrent().addAfterNavigationListener(new AfterNavigationListener() {
            @Override
            void afterNavigation(AfterNavigationEvent event) {
                called = true
                expect(TestingParametrizedView) { event.viewClass }
            }
        })
        UI.getCurrent().navigate(TestingParametrizedView, 25L)
        expect(true) { called }
    }

    @Test
    void "viewClass - navigating to TestingParametrizedView2"() {
        def called = false
        UI.getCurrent().addAfterNavigationListener(new AfterNavigationListener() {
            @Override
            void afterNavigation(AfterNavigationEvent event) {
                called = true
                expect(TestingParametrizedView2) { event.viewClass }
            }
        })
        UI.getCurrent().navigate(TestingParametrizedView2, 25L)
        expect(true) { called }
    }

    @Test
    void "viewClass - location"() {
        expect(RootView) { new Location("").getViewClass() }
        expect(TestingView) { new Location("testing").getViewClass() }
        expect(TestingView2) { new Location("testing/foo/bar").getViewClass() }
        expect(TestingParametrizedView) { new Location("testingp/20").getViewClass() }
        expect(TestingParametrizedView2) { new Location("testingp/with/subpath/20").getViewClass() }
        expect(null) { new Location("nonexisting").getViewClass() }
    }
}
