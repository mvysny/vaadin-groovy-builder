package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.html.Div
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import com.vaadin.flow.component.details.Details

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * Utility methods for the {@link Details} class.
 * @author mavi
 */
@CompileStatic
class DetailsUtils {
    private static Component checkOneChildAndGet(@NotNull Component component, @NotNull String producerName) {
        def children = component.children.toList()
        def count = children.size()
        Utils.check(count == 1) { "$producerName was expected to produce 1 component but it produced $count" }
        return children.first()
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-details">[Details]</a>. Code example:
     *
     * <pre>
     * details("Hello") {*   content { button("hi!") }*}* </pre>
     */
    @NotNull
    static Details details(@NotNull HasComponents self,
                           @Nullable String summaryText = null,
                           @DelegatesTo(value = Details, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def panel = new Details()
        if (summaryText != null) panel.setSummaryText(summaryText)
        init(self, panel, block)
    }

    /**
     * Allows you to set the summary of this [Details] as a component:
     * <pre>
     * details {*   summary { button("learn more") }*}* </pre>
     */
    @NotNull
    static void summary(@NotNull Details self,
                        @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def div = new Div()
        // throwaway div, will be used to grab components produced by block
        block.delegate = div
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        def child = checkOneChildAndGet(div, "block()")
        self.summary = child
    }

    /**
     * Removes all {@link Details#getContent() content} components from this {@link Details}.
     */
    static void clearContent(@NotNull Details self) {
        self.setContent(null)
    }

    /**
     * Clears previous contents and re-populates the content of this [Details].
     */
    @NotNull
    static void content(@NotNull Details self,
                        @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        clearContent(self)
        def div = new Div()
        block.delegate = div
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        Component[] children = VaadinUtils.toArray(div.children, new Component[0])
        self.addContent(children)
    }
}
