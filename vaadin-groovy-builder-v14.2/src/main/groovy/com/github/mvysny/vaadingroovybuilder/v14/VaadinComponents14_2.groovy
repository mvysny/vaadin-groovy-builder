package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.datetimepicker.DateTimePicker
import com.vaadin.flow.component.orderedlayout.Scroller
import com.vaadin.flow.dom.Element
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import static com.github.mvysny.vaadingroovybuilder.v14.Utils.*

/**
 * New components present in Vaadin 14.2 only.
 * @author mavi
 */
@CompileStatic
class VaadinComponents14_2 {

    @NotNull
    static DateTimePicker dateTimePicker(@NotNull HasComponents self,
                                         @Nullable String label = null,
                                         @DelegatesTo(value = DateTimePicker, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def picker = new DateTimePicker()
        picker.label = label
        return VaadinDsl.init(self, picker, block)
    }

    /**
     * Use as follows:
     * <pre>
     * scroller {
     *   content {
     *     div {
     *       width = "200px"; height = "200px"; element.styles.add("background-color", "red")
     *     }
     *   }
     * }
     * </pre>
     */
    @NotNull
    static Scroller scroller(@NotNull HasComponents self,
                             @DelegatesTo(value = Scroller, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        return VaadinDsl.init(self, new Scroller(), block)
    }

    @Nullable
    static <T> T content(@NotNull Scroller self,
                         @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure<T> block) {
        self.element.removeAllChildren()
        block.delegate = new HasComponents() {
            @Override Element getElement() {
                throw new UnsupportedOperationException("Not expected to be called")
            }
            @Override void add(Component... components) {
                Utils.require(components.length < 2) { "Too many components to add - scroller can only host one! ${components.toList()}" }
                Component component = VaadinUtils.firstOrNull(components)
                if (component != null) {
                    Utils.check(self.element.childCount == 0) { "The scroller can only host one component at most" }
                    self.content = component
                }
            }
        }
        block.resolveStrategy = Closure.DELEGATE_FIRST
        T result = block()
        Utils.checkNotNull(self.content) { "`block` must add exactly one component to the scroller" }
        return result
    }
}
