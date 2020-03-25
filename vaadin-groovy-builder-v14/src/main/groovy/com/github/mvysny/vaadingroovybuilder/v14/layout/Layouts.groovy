package com.github.mvysny.vaadingroovybuilder.v14.layout

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
     * The layout has 100% width by default.
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
     * @param isPadding whether to have padding around the children of the layout, defaults to false
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

    /**
     * Configures the general rules for positioning of child components inside of this [HorizontalLayout].
     *
     * Example of usage:
     * <pre>
     * horizontalLayout {
     *   content { align(right, middle) }
     * }
     * </pre>
     * Important notes:
     * <ul><li>[HorizontalLayout] only supports one row of components; if you have multiple rows you need to use [FlexLayout].</li>
     * <li>Never use [com.vaadin.flow.component.HasSize.setSizeFull] nor set the [com.vaadin.flow.component.HasSize.setWidth] to `100%` - it will
     * not work as you expect. With Vaadin 8 the child would fill the slot allocated by HorizontalLayout. However with Vaadin 10 and flex layout
     * there are no slots; setting the width to `100%` would make the component match the width of parent - it would set it to be as wide as
     * the HorizontalLayout is.
     * </li></ul>
     * To alter the layout further, call the following properties on children:
     * <ul>
     * <li>Most important: [flexGrow] (and its brother [isExpand]) expands that particular child to take up all of the remaining space. The child
     * is automatically enlarged.</li>
     * <li>[verticalAlignSelf] to align child vertically; it is not possible to align particular child horizontally</li>
     * <li>[flexShrink] - when there is not enough room for all children then they are shrank</li>
     * <li>[flexBasis]</li>
     * </ul>
     */
    static void content(@NotNull HorizontalLayout self,
                        @DelegatesTo(value = HorizontalLayoutContent, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        block.delegate = new HorizontalLayoutContent(self)
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
    }

    /**
     * Configures the general rules for positioning of child components inside of this [VerticalLayout].
     *
     * Example of usage:
     * <pre>
     * verticalLayout {
     *   content { align(right, middle) }
     * }
     * </pre>
     * Important notes:
     * <ul>
     * <li>[VerticalLayout] only supports one row of components; if you have multiple columns you need to use [FlexLayout].</li>
     * <li>Never use [com.vaadin.flow.component.HasSize.setSizeFull] nor set the [com.vaadin.flow.component.HasSize.setHeight] to `100%` - it will
     * not work as you expect. With Vaadin 8 the child would fill the slot allocated by VerticalLayout. However with Vaadin 10 and flex layout
     * there are no slots; setting the height to `100%` would make the component match the height of parent - it would set it to be as tall as
     * the VerticalLayout is.</li>
     * </ul>
     * To alter the layout further, call the following properties on children:
     * <ul>
     * <li>Most important: [flexGrow] (and its brother [isExpand]) expands that particular child to take up all of the remaining space. The child
     * is automatically enlarged.</li>
     * <li>[verticalAlignSelf] to align child vertically; it is not possible to align particular child horizontally</li>
     * <li>[flexShrink] - when there is not enough room for all children then they are shrank</li>
     * <li>[flexBasis]</li>
     * </ul>
     */
    static void content(@NotNull VerticalLayout self,
                        @DelegatesTo(value = VerticalLayoutContent, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        // the only reason why this is done as a builder, is that the VerticalLayoutContent.* constants are constrained to the block
        // and not defined as global variables.
        block.delegate = new VerticalLayoutContent(self)
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
    }
}
