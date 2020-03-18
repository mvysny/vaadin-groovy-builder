package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * Builder methods for built-in Vaadin components.
 * @author mavi
 */
@CompileStatic
class VaadinComponents {
    /**
     * Creates a Vaadin Button https://vaadin.com/elements/vaadin-button with an optional [text] and an [icon], and adds it to the parent.
     * <p></p>
     * See https://vaadin.com/elements/vaadin-button/html-examples/button-lumo-theme-demos for a list
     * of possible alternative themes for the button.
     * @param icon the icon, to use icons provided by Lumo. Just use `VaadinIcon.TRASH.create()` or `new Icon("lumo", "plus")`
     * @param block runs the block with the button as a receiver.
     */
    @NotNull
    static Button button(HasComponents self, @Nullable String text = null, @Nullable Component icon = null,
                         @DelegatesTo(value = Button, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new Button(text, icon), block)
    }
}
