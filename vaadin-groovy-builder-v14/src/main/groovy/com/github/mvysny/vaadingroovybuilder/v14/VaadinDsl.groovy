package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

/**
 * The basic builder {@link #init} function.
 * @author mavi
 */
@CompileStatic
class VaadinDsl {
    /**
     * When introducing extensions for your custom components, just call this in your method. For example:
     * <pre>
     * static ShinyComponent shinyComponent(final HasComponents self,
     *         String caption = null, @DelegatesTo(value = ShinyComponent, strategy = Closure.DELEGATE_FIRST) Closure block) {
     *     init(self, new ShinyComponent(caption), block)
     * }
     * </pre>
     * Adds <code>component</code> to receiver, see {@link HasComponents#add} for details.
     * @param component the component to attach
     * @param block optional block to run over the component, allowing you to add children to the [component]
     */
    @NotNull
    static <C extends Component> C init(final HasComponents self,
                                        @DelegatesTo.Target @NotNull C component,
                                        @DelegatesTo(strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        self.add(component)
        // any method called in closure will be delegated to the component itself
        block.delegate = component
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        return component
    }
}
