package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Label
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * @author mavi
 */
@CompileStatic
class FormLayouts {
    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-form-layout">[Form Layout]</a>.
     * See the HTML Examples link for a list
     * of possible alternative themes for the layout.
     */
    @NotNull
    static FormLayout formLayout(HasComponents self,
                                 @DelegatesTo(value = FormLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new FormLayout(), block)
    }

    /**
     * Creates a form item inside of the {@link FormLayout}, with an optional label.
     * See <a href="https://vaadin.com/elements/vaadin-form-layout">[Form Layout]</a>
     * documentation for more details.
     */
    @NotNull
    static GFormItem formItem(FormLayout self, @Nullable Component label = null,
                              @DelegatesTo(value = FormLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        init(self, new GFormItem()) {
            if (label != null) {
                delegate.addToLabel(label)
            }
            block.delegate = delegate
            block.resolveStrategy = Closure.DELEGATE_FIRST
            block()
        }
    }

    /**
     * Creates a form item inside of the {@link FormLayout}, with an optional label.
     * See <a href="https://vaadin.com/elements/vaadin-form-layout">[Form Layout]</a>
     * documentation for more details.
     */
    @NotNull
    static GFormItem formItem(FormLayout self, @NotNull String label,
                              @DelegatesTo(value = FormLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        formItem(self, new Label(label), block)
    }
}

/**
 * Makes {@link #addToLabel} public so that we can call it.
 */
class GFormItem extends FormLayout.FormItem {
    @Override
    void addToLabel(Component... components) {
        super.addToLabel(components)
    }
}
