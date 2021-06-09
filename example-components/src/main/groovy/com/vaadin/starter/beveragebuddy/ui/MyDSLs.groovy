package com.vaadin.starter.beveragebuddy.ui

import com.vaadin.flow.component.HasComponents
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * This class needs to be in a separate Gradle project since Groovy can't
 * both define and consume DSL functions in one project :(
 */
@CompileStatic
class MyDSLs {
    @NotNull
    static Toolbar toolbar(
            @NotNull HasComponents self,
            @NotNull String createCaption,
            @NotNull @DelegatesTo(value = Toolbar, strategy = Closure.DELEGATE_FIRST) Closure block
    ) {
        init(self, new Toolbar(createCaption), block)
    }
}
