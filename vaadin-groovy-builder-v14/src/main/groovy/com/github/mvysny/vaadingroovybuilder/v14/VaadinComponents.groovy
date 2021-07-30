package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.checkbox.CheckboxGroup
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.datetimepicker.DateTimePicker
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.listbox.ListBox
import com.vaadin.flow.component.listbox.MultiSelectListBox
import com.vaadin.flow.component.menubar.MenuBar
import com.vaadin.flow.component.orderedlayout.Scroller
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.component.timepicker.TimePicker
import com.vaadin.flow.dom.Element
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.check
import static com.github.mvysny.vaadingroovybuilder.v14.Utils.checkNotNull
import static com.github.mvysny.vaadingroovybuilder.v14.Utils.require
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
    static Button button(@NotNull HasComponents self, @Nullable String text = null, @Nullable Component icon = null,
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
    static Button iconButton(@NotNull HasComponents self, @NotNull Component icon,
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
    static void setPrimary(@NotNull Button self) {
        self.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-checkbox/">Vaadin Checkbox</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static Checkbox checkbox(@NotNull HasComponents self, @Nullable String label = null,
                             @DelegatesTo(value = Checkbox, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Checkbox(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-combo-box">Vaadin Combo Box</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static <ITEM> ComboBox<ITEM> comboBox(@NotNull HasComponents self,
                                    @NotNull Class<ITEM> itemClass,
                                    @Nullable String label = null,
                                    @DelegatesTo(type = "com.vaadin.flow.component.combobox.ComboBox<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new ComboBox<ITEM>(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-select">Vaadin Select</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     * <p></p>
     * The difference between combobox and select is that select isn't lazy, but you can add any child component into the select
     * and it will appear in the popup.
     */
    @NotNull
    static <ITEM> Select<ITEM> select(@NotNull HasComponents self,
                                @NotNull Class<ITEM> itemClass,
                                @Nullable String label = null,
                                @DelegatesTo(type = "com.vaadin.flow.component.select.Select<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def select = new Select<ITEM>()
        if (label != null) select.setLabel(label)
        init(self, select, block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-date-picker">[Vaadin Date Picker]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static DatePicker datePicker(@NotNull HasComponents self,
                                 @Nullable String label = null,
                                 @DelegatesTo(value = DatePicker, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DatePicker(label), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-dialog">[Vaadin Dialog]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static Dialog dialog(@NotNull HasComponents self,
                         @DelegatesTo(value = Dialog, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Dialog(), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/elements/vaadin-split-layout">[Split Layout]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     */
    @NotNull
    static SplitLayout splitLayout(@NotNull HasComponents self,
                                   @DelegatesTo(value = SplitLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new SplitLayout(), block)
    }

    @NotNull
    static Tabs tabs(@NotNull HasComponents self,
                     @DelegatesTo(value = Tabs, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Tabs(), block)
    }

    @NotNull
    static Tab tab(@NotNull Tabs self, @Nullable String label = null,
                    @DelegatesTo(value = Tab, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new Tab(label), block)
    }

    @NotNull
    static <ITEM> CheckboxGroup<ITEM> checkboxGroup(@NotNull HasComponents self,
                                              @NotNull Class<ITEM> itemClass,
                                              @DelegatesTo(type = "com.vaadin.flow.component.checkbox.CheckboxGroup<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new CheckboxGroup<ITEM>(), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-time-picker">[Time Picker]</a> field.
     */
    @NotNull
    static TimePicker timePicker(@NotNull HasComponents self, @Nullable String label = null,
                                 @DelegatesTo(value = TimePicker, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new TimePicker(label), block)
    }

    /**
     * Allows you to define a menu bar as follows:
     *
     * <pre>
     *   menuBar {
     *     item("save", { _ -> println("saved") }) {}
     *     item("style", null) {
     *       item("bold", { _ -> println("bold") })  {}
     *       item("italic", { _ -> println("italic") })  {}
     *     }
     *     item("clear", { _ -> println("clear") }) {}
     *   }
     * </pre>
     */
    @NotNull
    static MenuBar menuBar(@NotNull HasComponents self,
                           @DelegatesTo(value = MenuBar, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new MenuBar(), block)
    }

    /**
     * Creates a [TabSheet] component which shows both the list of tabs, and the tab contents itself.
     */
    @NotNull
    static TabSheet tabSheet(@NotNull HasComponents self,
                             @DelegatesTo(value = TabSheet, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new TabSheet(), block)
    }

    /**
     * Creates a <a href="https://vaadin.com/components/vaadin-list-box">[Vaadin List Box]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     * <p></p>
     * Unfortunately no label support for now: https://github.com/vaadin/vaadin-list-box-flow/issues/75
     */
    @NotNull
    static <ITEM> ListBox<ITEM> listBox(
            @NotNull HasComponents self,
            @NotNull Class<ITEM> itemClass,
            @DelegatesTo(type = "com.vaadin.flow.component.listbox.ListBox<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new ListBox<ITEM>(), block)
    }

    /**
     * Creates a multi-select <a href="https://vaadin.com/components/vaadin-list-box">[Vaadin List Box]</a>. See the HTML Examples link for a list
     * of possible alternative themes.
     * <p></p>
     * Unfortunately no label support for now: https://github.com/vaadin/vaadin-list-box-flow/issues/75
     */
    @NotNull
    static <ITEM> MultiSelectListBox<ITEM> multiSelectListBox(
            @NotNull HasComponents self,
            @NotNull Class<ITEM> itemClass,
            @DelegatesTo(type = "com.vaadin.flow.component.listbox.MultiSelectListBox<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new MultiSelectListBox<ITEM>(), block)
    }

    @NotNull
    static DateTimePicker dateTimePicker(@NotNull HasComponents self,
                                         @Nullable String label = null,
                                         @DelegatesTo(value = DateTimePicker, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def picker = new DateTimePicker()
        picker.label = label
        return init(self, picker, block)
    }

    /**
     * Use as follows:
     * <pre>
     * scroller {
     *   content {
     *     div {
     *       width = "200px"; height = "200px"; element.styles.add("background-color", "red")
     *     }
     *   }
     * }
     * </pre>
     */
    @NotNull
    static Scroller scroller(@NotNull HasComponents self,
                             @DelegatesTo(value = Scroller, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        return init(self, new Scroller(), block)
    }

    @Nullable
    static <T> T content(@NotNull Scroller self,
                         @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure<T> block) {
        self.element.removeAllChildren()
        block.delegate = new HasComponents() {
            @Override Element getElement() {
                throw new UnsupportedOperationException("Not expected to be called")
            }
            @Override void add(Component... components) {
                require(components.length < 2) { "Too many components to add - scroller can only host one! ${components.toList()}" }
                Component component = VaadinUtils.firstOrNull(components)
                if (component != null) {
                    check(self.element.childCount == 0) { "The scroller can only host one component at most" }
                    self.content = component
                }
            }
        }
        block.resolveStrategy = Closure.DELEGATE_FIRST
        T result = block()
        checkNotNull(self.content) { "`block` must add exactly one component to the scroller" }
        return result
    }
}
