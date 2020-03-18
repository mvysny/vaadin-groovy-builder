package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.IronIcon
import com.vaadin.flow.component.icon.VaadinIcon
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * Adds support for {@link Icon} and {@link IronIcon}.
 * @author mavi
 */
@CompileStatic
class IconUtils {
    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-icons/">[Iron Icon]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static Icon icon(HasComponents self, @NotNull VaadinIcon icon,
                     @DelegatesTo(value = Icon, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new Icon(icon), block)
    }

    @NotNull
    static Icon icon(HasComponents self, @NotNull String collection, @NotNull String icon,
                     @DelegatesTo(value = Icon, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new Icon(collection, icon), block)
    }

    @NotNull
    static IronIcon ironIcon(HasComponents self, @NotNull String collection, @NotNull String icon,
                     @DelegatesTo(value = IronIcon, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new IronIcon(collection, icon), block)
    }
}
