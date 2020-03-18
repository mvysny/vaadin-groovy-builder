package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * @author mavi
 */
@CompileStatic
class Layouts {
    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-ordered-layout/">[Flex Layout]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static FlexLayout flexLayout(HasComponents self,
                                 @DelegatesTo(value = FlexLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new FlexLayout(), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-ordered-layout">[Vertical Layout]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     * <p></p>
     * The layout
     * has 100% width by default.
     * @param isPadding whether to have padding around the children of the layout, defaults to true
     * @param isSpacing whether to have spacing between the children of the layout, defaults to true
     */
    @NotNull
    static VerticalLayout verticalLayout(HasComponents self,
                                         boolean isPadding = true,
                                         boolean isSpacing = true,
                                         @DelegatesTo(value = VerticalLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {

        def layout = new VerticalLayout()
        layout.setPadding(isPadding)
        layout.setSpacing(isSpacing)
        init(self, layout, block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-ordered-layout">[Horizontal Layout]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     * @param isPadding whether to have padding around the children of the layout, defaults to true
     * @param isSpacing whether to have spacing between the children of the layout, defaults to true
     */
    @NotNull
    static HorizontalLayout horizontalLayout(HasComponents self,
                                           boolean isPadding = false,
                                           boolean isSpacing = true,
                                           @DelegatesTo(value = HorizontalLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {

        def layout = new HorizontalLayout()
        layout.setPadding(isPadding)
        layout.setSpacing(isSpacing)
        init(self, layout, block)
    }
}
