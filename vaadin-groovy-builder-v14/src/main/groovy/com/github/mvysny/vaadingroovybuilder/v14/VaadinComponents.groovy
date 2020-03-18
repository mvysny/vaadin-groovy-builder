package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.checkbox.CheckboxGroup
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.component.textfield.BigDecimalField
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.timepicker.TimePicker
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * Builder methods for built-in Vaadin components.
 * @author mavi
 */
@CompileStatic
class VaadinComponents {
    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-button">Vaadin Button</a> with an optional [text] and an [icon], and adds it to the parent.
     * <p></p>
     * See <a href="https://vaadin.com/elements/vaadin-button/html-examples/button-lumo-theme-demos">Vaadin Button Demos</a> for a list
     * of possible alternative themes for the button.
     * @param icon the icon, to use icons provided by Lumo. Just use <code>VaadinIcon.TRASH.create()</code> or <code>new Icon("lumo", "plus")</code>
     * @param block runs the block with the button as a receiver.
     */
    @NotNull
    static Button button(HasComponents self, @Nullable String text = null, @Nullable Component icon = null,
                         @DelegatesTo(value = Button, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Button(text, icon), block)
    }

    /**
     * Utility method which creates a <a href="https://vaadin.com/elements/vaadin-button">Vaadin Button</a> which
     * acts as an icon-only button (using the {@link ButtonVariant#LUMO_ICON} variant).
     * <p></p>
     * See <a href="https://vaadin.com/elements/vaadin-button/html-examples/button-lumo-theme-demos">vaadin-button demos</a> for a list
     * of possible alternative themes for the button.
     * @param icon the icon, to use icons provided by Lumo just use `Icon("lumo", "plus")` or `Icon(VaadinIcons.TRASH)`
     * @param block runs the block with the button as a receiver.
     */
    @NotNull
    static Button iconButton(HasComponents self, @NotNull Component icon,
                             @DelegatesTo(value = Button, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        button(self, null, icon) {
            addThemeVariants(ButtonVariant.LUMO_ICON)
            block.delegate = delegate
            block.resolveStrategy = Closure.DELEGATE_FIRST
            block()
        }
    }

    /**
     * Sets the button as primary. It effectively adds the {@link ButtonVariant#LUMO_PRIMARY} theme variant.
     */
    static void setPrimary(Button self) {
        self.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-checkbox/">Vaadin Checkbox</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static Checkbox checkBox(HasComponents self, @Nullable String label = null,
                             @DelegatesTo(value = Checkbox, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Checkbox(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-combo-box">Vaadin Combo Box</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static <T> ComboBox<T> comboBox(HasComponents self, @Nullable String label = null,
                                    @DelegatesTo(value = ComboBox, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new ComboBox<T>(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-select">Vaadin Select</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     * <p></p>
     * The difference between combobox and select is that select isn't lazy, but you can add any child component into the select
     * and it will appear in the popup.
     */
    @NotNull
    static <T> Select<T> select(HasComponents self, @Nullable String label = null,
                                @DelegatesTo(type = "com.vaadin.flow.component.select.Select<T>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def select = new Select<T>()
        if (label != null) select.setLabel(label)
        init(self, select, block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-date-picker">[Vaadin Date Picker]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static DatePicker datePicker(HasComponents self, @Nullable String label = null,
                                 @DelegatesTo(value = DatePicker, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DatePicker(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-dialog">[Vaadin Dialog]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static Dialog dialog(HasComponents self,
                         @DelegatesTo(value = Dialog, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Dialog(), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-text-field">[Password Field]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static PasswordField passwordField(HasComponents self, @Nullable String label = null,
                                       @DelegatesTo(value = PasswordField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new PasswordField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-split-layout">[Split Layout]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static SplitLayout splitLayout(HasComponents self,
                                   @DelegatesTo(value = SplitLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new SplitLayout(), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-text-field">[Text Field]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static TextField textField(HasComponents self, @Nullable String label = null,
                               @DelegatesTo(value = TextField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new TextField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-text-field">[Email Field]</a>. See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static EmailField emailField(HasComponents self, @Nullable String label = null,
                                 @DelegatesTo(value = EmailField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new EmailField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-number-field/java-examples/number-field">[Number Field]</a>.
     * See the HTML Examples link for a list
     * of possible alternative themes for the button.
     */
    @NotNull
    static NumberField numberField(HasComponents self, @Nullable String label = null,
                                   @DelegatesTo(value = NumberField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new NumberField(label), block)
    }

    @NotNull
    static TextArea textArea(HasComponents self, @Nullable String label = null,
                             @DelegatesTo(value = TextArea, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new TextArea(label), block)
    }

    @NotNull
    static Tabs tabs(HasComponents self,
                     @DelegatesTo(value = Tabs, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Tabs(), block)
    }

    @NotNull
    static Tab tab(Tabs self, @Nullable String label = null,
                    @DelegatesTo(value = Tab, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Tab(label), block)
    }

    @NotNull
    static <T> CheckboxGroup<T> checkboxGroup(HasComponents self,
                                              @DelegatesTo(type = "com.vaadin.flow.component.checkbox.CheckboxGroup<T>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new CheckboxGroup<T>(), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-time-picker">[Time Picker]</a> field.
     */
    @NotNull
    static TimePicker timePicker(HasComponents self, @Nullable String label = null,
                                 @DelegatesTo(value = TimePicker, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new TimePicker(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-number-field/java-examples/number-field">[Integer Field]</a>.
     * See the HTML Examples link for a list
     * of possible alternative themes for the button.
     * <p></p>
     * Only available starting with Vaadin 14.1.
     */
    @NotNull
    static IntegerField integerField(HasComponents self, @Nullable String label = null,
                                     @DelegatesTo(value = IntegerField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new IntegerField(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-number-field/java-examples/number-field">[BigDecimal Field]</a>.
     * See the HTML Examples link for a list
     * of possible alternative themes for the button.
     * <p></p>
     * Only available starting with Vaadin 14.1.
     */
    @NotNull
    static BigDecimalField bigDecimalField(HasComponents self, @Nullable String label = null,
                                           @DelegatesTo(value = BigDecimalField, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new BigDecimalField(label), block)
    }
}