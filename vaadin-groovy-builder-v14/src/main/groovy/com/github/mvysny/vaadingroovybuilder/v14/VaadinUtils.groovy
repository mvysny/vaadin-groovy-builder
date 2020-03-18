package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.ClickNotifier
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.ComponentEvent
import com.vaadin.flow.component.ComponentUtil
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.Text
import com.vaadin.flow.dom.Element
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * A collection of basic Vaadin utility extension functions.
 * @author mavi
 */
@CompileStatic
class VaadinUtils {
    /**
     * Fires given event on the component.
     */
    static void fireEvent(Component self, @NotNull ComponentEvent<? extends Component> event) {
        ComponentUtil.fireEvent(self, event)
    }

    /**
     * Adds {@link com.vaadin.flow.component.button.Button#click} functionality to all {@link ClickNotifier}s.
     * This function directly calls
     * all click listeners, thus it avoids the roundtrip to client and back.
     * It even works with browserless testing.
     */
    static <R extends Component & ClickNotifier<R>> void serverClick(R self) {
        fireEvent(self, new ClickEvent<R>(self, true, -1, -1, -1, -1, 1, -1, false, false, false, false))
    }

    /**
     * Appends a text node with given <code>text</code> to the component.
     */
    @NotNull
    static Text text(HasComponents self, String text, @DelegatesTo(value = Text, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        VaadinDsl.init(self, new Text(text), block)
    }

    /**
     * Gets the alignment of the text in the component. One of <code>center</code>, <code>left</code>, <code>right</code>, <code>justify</code>.
     */
    @Nullable
    static String getTextAlign(Component self) {
        self.element.style.get("textAlign")
    }

    /**
     * Sets the alignment of the text in the component. One of <code>center</code>, <code>left</code>, <code>right</code>, <code>justify</code>.
     */
    static void setTextAlign(Component self, @Nullable String align) {
        self.element.style.set("textAlign", align)
    }
    /**
     * Either calls {@link Element#setAttribute} (if the <code>value</code> is not null), or
     * {@link Element#removeAttribute} (if the <code>value</code> is null).
     * @param attribute the name of the attribute.
     */
    static void setOrRemoveAttribute(Element self, @NotNull String attribute, @Nullable String value) {
        if (value == null) {
            self.removeAttribute(attribute)
        } else {
            self.setAttribute(attribute, value)
        }
    }
}
