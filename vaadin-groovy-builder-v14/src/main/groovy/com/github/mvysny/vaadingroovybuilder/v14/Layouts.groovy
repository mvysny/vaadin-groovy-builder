package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.orderedlayout.FlexLayout
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
                                 @DelegatesTo(value = FlexLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new FlexLayout(), block)
    }
}
