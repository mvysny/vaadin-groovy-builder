package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.formlayout.FormLayout
import groovy.transform.CompileStatic

/**
 * Makes {@link #addToLabel} public so that we can call it.
 */
@CompileStatic
class GFormItem extends FormLayout.FormItem {
    @Override
    void addToLabel(Component... components) {
        super.addToLabel(components)
    }
}