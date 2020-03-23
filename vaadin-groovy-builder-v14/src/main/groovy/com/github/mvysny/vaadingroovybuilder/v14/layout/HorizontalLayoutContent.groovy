package com.github.mvysny.vaadingroovybuilder.v14.layout

import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

/**
 * @author mavi
 */
@CompileStatic
class HorizontalLayoutContent {
    private final HorizontalLayout owner

    HorizontalLayoutContent(HorizontalLayout owner) {
        this.owner = owner
    }

    /**
     * This aligns the container's components within when there is extra space. See [justify-content](https://css-tricks.com/snippets/css/a-guide-to-flexbox/#article-header-id-6)
     * for more details.
     * <p></p>
     * Note: contrary to [verticalAlignment] this setting can not be overridden by individual children. This is a limitation of
     * the flex layout. Calling [horizontalAlignSelf] on children will throw an exception.
     */
    @NotNull FlexComponent.JustifyContentMode getHorizontalAlignment() {
        return owner.justifyContentMode
    }

    /**
     * This aligns the container's components within when there is extra space. See [justify-content](https://css-tricks.com/snippets/css/a-guide-to-flexbox/#article-header-id-6)
     * for more details.
     * <p></p>
     * Note: contrary to [verticalAlignment] this setting can not be overridden by individual children. This is a limitation of
     * the flex layout. Calling [horizontalAlignSelf] on children will throw an exception.
     */
    void setHorizontalAlignment(@NotNull FlexComponent.JustifyContentMode horizontalAlignment) {
        owner.justifyContentMode = horizontalAlignment
    }

    /**
     * The default vertical alignment to be used by all components without
     * individual alignments inside the layout. Individual components can be
     * aligned by using the [verticalAlignSelf] property.
     * <p></p>
     * It effectively sets the `"alignItems"` style value.
     * <p></p>
     * The default alignment is [top].
     * <p></p>
     * It's the same as the [HorizontalLayout.getDefaultVerticalComponentAlignment] method.
     */
    @NotNull FlexComponent.Alignment getVerticalAlignment() {
        return owner.defaultVerticalComponentAlignment
    }
    /**
     * The default vertical alignment to be used by all components without
     * individual alignments inside the layout. Individual components can be
     * aligned by using the [verticalAlignSelf] property.
     * <p></p>
     * It effectively sets the `"alignItems"` style value.
     * <p></p>
     * The default alignment is [top].
     * <p></p>
     * It's the same as the [HorizontalLayout.getDefaultVerticalComponentAlignment] method.
     */
    void setVerticalAlignment(@NotNull FlexComponent.Alignment alignment) {
        owner.defaultVerticalComponentAlignment = alignment
    }

    /**
     * Items are positioned to the top of the available space.
     */
    @NotNull
    static final FlexComponent.Alignment top = FlexComponent.Alignment.START

    /**
     * Children are positioned to the bottom of the available space.
     */
    @NotNull
    static final FlexComponent.Alignment bottm = FlexComponent.Alignment.END

    /**
     * Children are positioned vertically in the middle of the available space.
     */
    @NotNull
    static final FlexComponent.Alignment middle = FlexComponent.Alignment.CENTER

    /**
     * Children are stretched to fit the container's height.
     */
    @NotNull
    static final FlexComponent.Alignment stretch = FlexComponent.Alignment.STRETCH

    /**
     * Children are positioned at the baseline of the container.
     */
    @NotNull
    static final FlexComponent.Alignment baseline = FlexComponent.Alignment.BASELINE

    /**
     * Children are positioned to the left of the available space.
     */
    @NotNull
    static final FlexComponent.JustifyContentMode left = FlexComponent.JustifyContentMode.START

    /**
     * Children are positioned to the right of the available space.
     */
    @NotNull
    static final FlexComponent.JustifyContentMode right = FlexComponent.JustifyContentMode.END

    /**
     * Children are positioned at the horizontal center of the container.
     */
    @NotNull
    static final FlexComponent.JustifyContentMode center = FlexComponent.JustifyContentMode.CENTER

    /**
     * Children are positioned with space between the lines.
     */
    @NotNull
    static final FlexComponent.JustifyContentMode between = FlexComponent.JustifyContentMode.BETWEEN

    /**
     * Children are positioned with space before, between, and after the lines.
     */
    @NotNull
    static final FlexComponent.JustifyContentMode around = FlexComponent.JustifyContentMode.AROUND

    /**
     * Children have equal space around them.
     */
    @NotNull
    static final FlexComponent.JustifyContentMode evenly = FlexComponent.JustifyContentMode.EVENLY

    /**
     * Centers all children inside of the layout. Equal to setting [center] and [middle].
     */
    void center() {
        align(center, middle)
    }

    /**
     * Align the children as specified by the [horizontalAlignment] and [verticalAlignment].
     * @param horizontalAlignment use one of [left], [right], [center], [between], [around] or [evenly],
     * which are shortcuts for [FlexComponent.JustifyContentMode].
     * @param verticalAlignment use one of [top], [bottom], [stretch], [middle], [baseline],
     * which are shortcuts for [FlexComponent.Alignment].
     */
    void align(@NotNull FlexComponent.JustifyContentMode horizontalAlignment,
               @NotNull FlexComponent.Alignment verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment
        this.verticalAlignment = verticalAlignment
    }
}
