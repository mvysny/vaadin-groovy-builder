package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Label
import groovy.transform.CompileStatic
import org.jetbrains.annotations.Nullable

/**
 * Makes {@link #addToLabel} public so that we can call it.
 */
@CompileStatic
class GFormItem extends FormLayout.FormItem {
    /**
     * Utility constructor which takes in an optional label and an optional child component.
     * @param label
     * @param field
     */
    GFormItem(@Nullable String label = null, @Nullable Component field = null) {
        if (label != null) {
            addToLabel(new Label(label))
        }
        if (field != null) {
            add(field)
        }
    }

    @Override
    void addToLabel(Component... components) {
        super.addToLabel(components)
    }
}