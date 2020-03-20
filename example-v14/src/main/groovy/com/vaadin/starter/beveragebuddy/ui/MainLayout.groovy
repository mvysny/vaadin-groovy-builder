package com.vaadin.starter.beveragebuddy.ui

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasElement
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.router.HighlightConditions
import com.vaadin.flow.router.RouterLayout
import com.vaadin.flow.server.InitialPageSettings
import com.vaadin.flow.server.PageConfigurator
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import com.vaadin.starter.beveragebuddy.ui.categories.CategoriesList
import com.vaadin.starter.beveragebuddy.ui.reviews.ReviewsList
import groovy.transform.CompileStatic

/**
 * The main layout contains the header with the navigation buttons, and the
 * child views below that.
 */
@BodySize(width = "100vw", height = "100vh")
@CssImport("frontend://styles/shared-styles.css")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(Lumo)
@CompileStatic
class MainLayout extends VerticalLayout implements RouterLayout, PageConfigurator {

    MainLayout() {
        addClassName("main-layout"); setSizeFull(); setPadding(false)
        setJustifyContentMode(JustifyContentMode.START)
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH)

        div {
            // header
            addClassName("main-layout__header")
            h2("Beverage Buddy") {
                addClassName("main-layout__title")
            }
            div {
                // navigation
                addClassName("main-layout__nav")
                routerLink(VaadinIcon.LIST, "Reviews", ReviewsList) {
                    addClassName("main-layout__nav-item")
                    highlightCondition = HighlightConditions.sameLocation()
                }
                routerLink(VaadinIcon.ARCHIVES, "Categories", CategoriesList) {
                    addClassName("main-layout__nav-item")
                    highlightCondition = HighlightConditions.sameLocation()
                }
            }
        }
    }

    @Override
    void showRouterLayoutContent(HasElement content) {
        add(content as Component)
        (content as Component).setExpand(true)
    }

    @Override
    void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes")
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black")
    }
}
