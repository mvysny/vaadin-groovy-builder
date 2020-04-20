package com.vaadin.starter.beveragebuddy.ui

import com.github.mvysny.vaadingroovybuilder.v14.GComposite
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.router.Route
import groovy.transform.CompileStatic

@Route("components-showcase")
@CompileStatic
class ComponentsShowcase extends GComposite {
    private final def root = ui {
        tabSheet {
            setSizeFull()

            tab("Scrollable Tab") {
                div {
                    setSizeFull()
                    element.style.set("overflow-y", "scroll")
                    for (int i = 0; i < 1000; i++) {
                        div {
                            text("Row $i")
                        }
                    }
                }
            }

            tab("TabSheet") {
                tabSheet {
                    tab("Simple content") {
                        div {
                            h1("Welcome") {}
                            p { text("Foo Bar Baz")}
                        }
                    }
                    addTab("Lazy Tab 1")
                    addTab("Lazy Tab 2")
                    addTab("Lazy Tab 3")

                    addSelectedChangeListener {
                        def selectedTab = getSelectedTab()
                        if (selectedTab != null && getTabContents(selectedTab) == null) {
                            def div = new Div()
                            div.text("Lazy contents of ${selectedTab.label}")
                            Notification.show("Lazily created contents for ${selectedTab.label}")
                            setTabContents(selectedTab, div)
                        }
                    }
                }
            }
        }
    }
}
