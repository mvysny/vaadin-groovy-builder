package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid
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
    static FlexLayout flexLayout(@NotNull HasComponents self,
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
    static VerticalLayout verticalLayout(@NotNull HasComponents self,
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
    static HorizontalLayout horizontalLayout(@NotNull HasComponents self,
                                           boolean isPadding = false,
                                           boolean isSpacing = true,
                                           @DelegatesTo(value = HorizontalLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def layout = new HorizontalLayout()
        layout.setPadding(isPadding)
        layout.setSpacing(isSpacing)
        init(self, layout, block)
    }

    /**
     * Sets the component's {@link FlexLayout#getFlexGrow(com.vaadin.flow.component.HasElement)}. Only works when the
     * component is nested in a DOM element using CSS flexbox.
     * <p></p>
     * This defines the ability for a flex item to grow if necessary. It accepts a
     * unitless value that serves as a proportion. It dictates what amount of the
     * available space inside the flex container the item should take up.
     * <p></p>
     * If all items have flex-grow set to 1, the remaining space in the container
     * will be distributed equally to all children. If one of the children has a value
     * of 2, the remaining space would take up twice as much space as the others
     * (or it will try to, at least).
     * <p></p>
     * Negative numbers are invalid.
     * <p></p>
     * Get more information at <a href="https://css-tricks.com/snippets/css/a-guide-to-flexbox/">[Guide to Flexbox]</a>
     * <p></p>
     * Warning: in case of {@link Grid.Column} it returns/sets the value of {@link Grid.Column#setFlexGrow(int)}.
     */
    static void setFlexGrow(@NotNull Component self, double flexGrow) {
        if (self instanceof Grid.Column) {
            (self as Grid.Column).flexGrow = flexGrow.toInteger()
        }
        if (flexGrow == 0d) {
            self.element.style.remove("flexGrow")
        } else if (flexGrow > 0) {
            self.element.style.set("flexGrow", flexGrow.toString())
        } else {
            throw new IllegalArgumentException("Flex grow property cannot be negative: $flexGrow")
        }
    }

    /**
     * See {@link #setFlexGrow}.
     */
    static double getFlexGrow(@NotNull Component self) {
        if (self instanceof Grid.Column) {
            return (self as Grid.Column).flexGrow.toDouble()
        }
        String value = self.element.style.get("flexGrow")
        return value == null || value.isAllWhitespace() ? 0d : value.toDouble()
    }

    /**
     * Checks if the component expands when nested in {@link com.vaadin.flow.component.orderedlayout.FlexComponent}. Alias for
     * setting {@link #setFlexGrow} to 1.0; see {@link #setFlexGrow} for more information.
     */
    static boolean isExpand(@NotNull Component self) {
        return getFlexGrow(self) > 0
    }

    static void setExpand(@NotNull Component self, boolean isExpand) {
        setFlexGrow(self, isExpand ? 1d : 0d)
    }
}
