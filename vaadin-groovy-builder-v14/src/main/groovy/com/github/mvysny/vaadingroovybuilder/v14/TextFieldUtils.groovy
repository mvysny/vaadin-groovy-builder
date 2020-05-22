package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.textfield.BigDecimalField
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.GeneratedVaadinTextArea
import com.vaadin.flow.component.textfield.GeneratedVaadinTextField
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.require
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

    /**
     * Selects all text in this text field. The selection is not really visible in
     * the browser unless the field is focused.
     */
    static void selectAll(@NotNull GeneratedVaadinTextField<?, String> self) {
        self.getElement().executeJs("this.inputElement.select()");
    }

    /**
     * Clears the selection in the text field and moves the cursor to the end of
     * the text. There may be no effect if the field is unfocused - the browser
     * generally selects all when the field gains focus.
     */
    static void selectNone(@NotNull GeneratedVaadinTextField<?, String> self) {
        setCursorLocation(self, self.getValue()?.length() ?: 0)
    }

    /**
     * Moves the cursor within the text field. Has the side-effect of clearing the selection.
     * <p></p>
     * There may be no effect if the field is unfocused - the browser
     * generally selects all when the field gains focus.
     */
    static void setCursorLocation(@NotNull GeneratedVaadinTextField<?, String> self,
                                  int cursorLocation) {
        select(self, cursorLocation, cursorLocation)
    }

    /**
     * Selects given characters in this text field. The selection is not really visible in
     * the browser unless the field is focused.
     */
    static void select(@NotNull GeneratedVaadinTextField<?, String> self,
            int selectionStart,
            int selectionEnd) {
        require(selectionStart >= 0) { "selectionStart: must be 0 or greater but was $selectionStart" }
        require(selectionEnd >= 0) { "selectionEnd: must be 0 or greater but was $selectionEnd" }
        self.getElement().executeJs("this.inputElement.setSelectionRange($selectionStart, $selectionEnd)")
    }

    /**
     * Selects all text in this text field. The selection is not really visible in
     * the browser unless the field is focused.
     */
    static void selectAll(@NotNull GeneratedVaadinTextArea<?, String> self) {
        self.getElement().executeJs("this.inputElement.select()");
    }

    /**
     * Clears the selection in the text field and moves the cursor to the end of
     * the text. There may be no effect if the field is unfocused - the browser
     * generally selects all when the field gains focus.
     */
    static void selectNone(@NotNull GeneratedVaadinTextArea<?, String> self) {
        setCursorLocation(self, self.getValue()?.length() ?: 0)
    }

    /**
     * Moves the cursor within the text field. Has the side-effect of clearing the selection.
     * <p></p>
     * There may be no effect if the field is unfocused - the browser
     * generally selects all when the field gains focus.
     */
    static void setCursorLocation(@NotNull GeneratedVaadinTextArea<?, String> self,
                                  int cursorLocation) {
        select(self, cursorLocation, cursorLocation)
    }

    /**
     * Selects given characters in this text field. The selection is not really visible in
     * the browser unless the field is focused.
     */
    static void select(@NotNull GeneratedVaadinTextArea<?, String> self,
                       int selectionStart,
                       int selectionEnd) {
        require(selectionStart >= 0) { "selectionStart: must be 0 or greater but was $selectionStart" }
        require(selectionEnd >= 0) { "selectionEnd: must be 0 or greater but was $selectionEnd" }
        self.getElement().executeJs("this.inputElement.setSelectionRange($selectionStart, $selectionEnd)")
    }
}
