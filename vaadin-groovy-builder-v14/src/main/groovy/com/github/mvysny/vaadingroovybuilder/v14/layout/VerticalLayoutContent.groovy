package com.github.mvysny.vaadingroovybuilder.v14.layout

import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

/**
 * @author mavi
 */
@CompileStatic
class VerticalLayoutContent {
    private final VerticalLayout owner

    VerticalLayoutContent(@NotNull VerticalLayout owner) {
        this.owner = owner
    }
    /**
     * The default horizontal alignment to be used by all components without
     * individual alignments inside the layout. Individual components can be
     * aligned by using the [horizontalAlignSelf] property.
     * <p></p>
     * It effectively sets the `"alignItems"` style value.
     * <p></p>
     * The default alignment is [left].
     * <p></p>
     * It's the same as the [VerticalLayout.setDefaultHorizontalComponentAlignment] method.
     */
    @NotNull FlexComponent.Alignment getHorizontalAlignment() {
        owner.defaultHorizontalComponentAlignment
    }
    void setHorizontalAlignment(@NotNull FlexComponent.Alignment alignment) {
        owner.defaultHorizontalComponentAlignment = alignment
    }

    /**
     * This aligns the container's components within when there is extra space. See [justify-content](https://css-tricks.com/snippets/css/a-guide-to-flexbox/#article-header-id-6)
     * for more details.
     * <p></p>
     * Note: contrary to [horizontalAlignment] this setting can not be overridden by individual children. This is a limitation of
     * the flex layout. Calling [verticalAlignSelf] on children will throw an exception.
     */
    @NotNull FlexComponent.JustifyContentMode getVerticalAlignment() {
        owner.justifyContentMode
    }
    void setVerticalAlignment(@NotNull FlexComponent.JustifyContentMode value) {
        owner.justifyContentMode = value
    }

    /**
     * Children are positioned to the left of the available space.
     */
    @NotNull static final FlexComponent.Alignment left = FlexComponent.Alignment.START

    /**
     * Children are positioned to the right of the available space.
     */
    @NotNull static final FlexComponent.Alignment right = FlexComponent.Alignment.END

    /**
     * Children are positioned at the horizontal center of the container.
     */
    @NotNull static final FlexComponent.Alignment center = FlexComponent.Alignment.CENTER

    /**
     * Children are stretched to fit the container's width.
     */
    @NotNull static final FlexComponent.Alignment stretch = FlexComponent.Alignment.STRETCH

    /**
     * Items are positioned to the top of the available space.
     */
    @NotNull static final FlexComponent.JustifyContentMode top = FlexComponent.JustifyContentMode.START

    /**
     * Children are positioned to the bottom of the available space.
     */
    @NotNull static final FlexComponent.JustifyContentMode bottom = FlexComponent.JustifyContentMode.END

    /**
     * Children are positioned vertically in the middle of the available space.
     */
    @NotNull static final FlexComponent.JustifyContentMode middle = FlexComponent.JustifyContentMode.CENTER

    /**
     * Items are positioned with space between the lines.
     */
    @NotNull static final FlexComponent.JustifyContentMode between = FlexComponent.JustifyContentMode.BETWEEN

    /**
     * Items are positioned with space before, between, and after the lines.
     */
    @NotNull static final FlexComponent.JustifyContentMode around = FlexComponent.JustifyContentMode.AROUND

    /**
     * Items have equal space around them.
     */
    @NotNull static final FlexComponent.JustifyContentMode evenly = FlexComponent.JustifyContentMode.EVENLY

    /**
     * Centers all children inside of the layout. Equal to setting [center] and [middle].
     */
    void center() {
        align(center, middle)
    }

    /**
     * Align the children as specified by the [horizontalAlignment] and [verticalAlignment].
     * @param horizontalAlignment use one of [left], [right], [center], [stretch], [top] which are
     * aliases for [FlexComponent.Alignment].
     * @param verticalAlignment one of [top], [bottom], [middle], [between], [around], [evenly],
     * which are aliases for [FlexComponent.JustifyContentMode].
     */
    void align(@NotNull FlexComponent.Alignment horizontalAlignment, @NotNull FlexComponent.JustifyContentMode verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment
        this.verticalAlignment = verticalAlignment
    }
}
