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
     * Creates a <a href="https://vaadin.com/elements/vaadin-button">Vaadin Button</a> with an optional [text] and an [icon], and adds it to the parent.
     * <p></p>
     * See <a href="https://vaadin.com/elements/vaadin-button/html-examples/button-lumo-theme-demos">Vaadin Button Demos</a> for a list
     * of possible alternative themes for the button.
     * @param icon the icon, to use icons provided by Lumo. Just use <code>VaadinIcon.TRASH.create()</code> or <code>new Icon("lumo", "plus")</code>
     * @param block runs the block with the button as a receiver.
     */
    @NotNull
    static Button button(HasComponents self, @Nullable String text = null, @Nullable Component icon = null,
                         @DelegatesTo(value = Button, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new Button(text, icon), block)
    }
}
