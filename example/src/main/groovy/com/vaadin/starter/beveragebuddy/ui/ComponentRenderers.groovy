package com.vaadin.starter.beveragebuddy.ui

import com.vaadin.flow.component.Component
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.function.SerializableFunction
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

@CompileStatic
class ComponentRenderers {
    @NotNull
    static <SOURCE> ComponentRenderer<Component, SOURCE> create(@NotNull final Closure<Component> componentFunction) {
        final cf = componentFunction
        return new ComponentRenderer<Component, SOURCE>(new SerializableFunction<SOURCE, Component>() {
            @Override
            Component apply(SOURCE source) {
                return cf.call(source)
            }
        })
    }
}
