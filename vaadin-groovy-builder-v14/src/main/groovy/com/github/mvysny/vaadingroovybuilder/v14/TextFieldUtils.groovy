package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.textfield.BigDecimalField
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * @author Martin Vysny <mavi@vaadin.com>
 */
@CompileStatic
class TextFieldUtils {

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-text-field">[Text Field]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static TextField textField(@NotNull HasComponents self, @Nullable String label = null,
                               @DelegatesTo(value = TextField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new TextField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-text-field">[Email Field]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static EmailField emailField(@NotNull HasComponents self,
                                 @Nullable String label = null,
                                 @DelegatesTo(value = EmailField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new EmailField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-number-field/java-examples/number-field">[Number Field]</a>.
     * See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static NumberField numberField(@NotNull HasComponents self,
                                   @Nullable String label = null,
                                   @DelegatesTo(value = NumberField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new NumberField(label), block)
    }

    @NotNull
    static TextArea textArea(@NotNull HasComponents self,
                             @Nullable String label = null,
                             @DelegatesTo(value = TextArea, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new TextArea(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-text-field">[Password Field]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static PasswordField passwordField(@NotNull HasComponents self, @Nullable String label = null,
                                       @DelegatesTo(value = PasswordField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new PasswordField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-number-field/java-examples/number-field">[Integer Field]</a>.
     * See the HTML Examples link for a list
     * of possible alternative themes.
     * @since Vaadin 14.1
     */
    @NotNull
    static IntegerField integerField(@NotNull HasComponents self, @Nullable String label = null,
                                     @DelegatesTo(value = IntegerField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new IntegerField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-number-field/java-examples/number-field">[BigDecimal Field]</a>.
     * See the HTML Examples link for a list
     * of possible alternative themes.
     * @since Vaadin 14.1
     */
    @NotNull
    static BigDecimalField bigDecimalField(@NotNull HasComponents self, @Nullable String label = null,
                                           @DelegatesTo(value = BigDecimalField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new BigDecimalField(label), block)
    }
}
