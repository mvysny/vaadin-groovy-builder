package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.accordion.Accordion
import com.vaadin.flow.component.accordion.AccordionPanel
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

@CompileStatic
class AccordionUtils {
    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-accordion">[Accordion]</a>. Code example:
     *
     * <pre>
     * accordion {*   panel("lorem ipsum") {*     content { label("dolor sit amet") }*}*   panel {*     summary { checkBox("More Lorem Ipsum?") }*     content { label("dolor sit amet") }*}*}* </pre>
     */
    @NotNull
    static Accordion accordion(HasComponents self,
                               @DelegatesTo(value = Accordion, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Accordion(), block)
    }

    /**
     * Adds a new {@link AccordionPanel} to this {@link Accordion}.
     */
    @NotNull
    static AccordionPanel panel(Accordion self,
                                @Nullable String summaryText = null,
                                @DelegatesTo(value = AccordionPanel, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def panel = new AccordionPanel()
        self.add(panel)
        if (summaryText != null) panel.setSummaryText(summaryText)
        block.delegate = panel
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        panel
    }
}
