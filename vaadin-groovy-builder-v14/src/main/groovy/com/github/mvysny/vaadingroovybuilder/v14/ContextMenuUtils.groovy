package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.contextmenu.ContextMenu
import com.vaadin.flow.component.contextmenu.HasMenuItems
import com.vaadin.flow.component.contextmenu.MenuItem
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem
import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FirstParam
import groovy.transform.stc.SimpleType
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@CompileStatic
class ContextMenuUtils {
    /**
     * Allows you to define context menu for any component as follows:
     *
     * <pre>
     * button("foo") {
     *   contextMenu {
     *     item("save", { e -> println("saved") })
     *     item("style") {
     *       item("bold", { e -> println("bold") })
     *       item("italic", { e -> println("italic") })
     *     }
     *     item("clear", { e -> println("clear") })
     *   }
     * }
     * </pre>
     */
    @NotNull
    static ContextMenu contextMenu(@NotNull Component self,
                                   @DelegatesTo(value = ContextMenu, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def menu = new ContextMenu(self)
        block.delegate = menu
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        return menu
    }

    @NotNull
    static MenuItem item(@NotNull HasMenuItems self, @NotNull String text,
                         @Nullable @ClosureParams(value = SimpleType.class, options = "com.vaadin.flow.component.ClickEvent<MenuItem>") Closure clickListener,
                         @DelegatesTo(value = MenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def item = self.addItem(text, clickListener)
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block.delegate = item
        block()
        item
    }

    @NotNull
    static MenuItem item(@NotNull MenuItem self, @NotNull String text,
                         @Nullable @ClosureParams(value = SimpleType.class,options="com.vaadin.flow.component.ClickEvent<MenuItem>") Closure clickListener,
                         @DelegatesTo(value = MenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        item(self.subMenu, text, clickListener, block)
    }

    @NotNull
    static MenuItem item(@NotNull HasMenuItems self, @NotNull Component component,
                         @Nullable @ClosureParams(value = SimpleType.class,options="com.vaadin.flow.component.ClickEvent<MenuItem>") Closure clickListener,
                         @DelegatesTo(value = MenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def item = self.addItem(component, clickListener)
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block.delegate = item
        block()
        item
    }

    @NotNull
    static MenuItem item(@NotNull MenuItem self, @NotNull Component component,
                         @Nullable @ClosureParams(value= SimpleType.class,options="com.vaadin.flow.component.ClickEvent<MenuItem>") Closure clickListener,
                         @DelegatesTo(value = MenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        item(self.subMenu, component, clickListener, block)
    }

    /**
     * Akin to [Grid.addContextMenu], but allows you to configure the context menu via given [block], as follows:
     * ```
     * grid<Person> {
     *   gridContextMenu {
     *     item("edit", { person -> println("editing $person") })
     *     item("delete", { person -> person.delete(); println("deleted $person") })
     *   }
     * }
     * ```
     *
     * Note that you can attach both [GridContextMenu] and [ContextMenu] to the grid, but that's discouraged since both of
     * those will show on right click and will overlap.
     */
    @NotNull
    static <T> GridContextMenu<T> gridContextMenu(
            @NotNull Grid<T> self,
            @DelegatesTo(value = GridContextMenu, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def menu = self.addContextMenu()
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block.delegate = menu
        block()
        menu
    }

    private static Closure adaptClickListener(Closure clickListener) {
        if (clickListener == null) return null
        return { e ->
            def item = (e as com.vaadin.flow.component.grid.contextmenu.GridContextMenu.GridContextMenuItemClickEvent).item.orElse(null)
            clickListener(item)
        }
    }

    /**
     * @param clickListener may be invoked with null item if there are not enough rows in the grid and the user clicks the
     * empty space in the grid.
     */
    @NotNull
    static <T> GridMenuItem<T> item(@NotNull GridContextMenu<T> self,
                                    @NotNull String text,
                                    @Nullable @ClosureParams(FirstParam.FirstGenericType.class) Closure clickListener,
                                    @DelegatesTo(value = GridMenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def item = self.addItem(text, adaptClickListener(clickListener))
        block.delegate = item
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        item
    }

    @NotNull
    static <T> GridMenuItem<T> item(@NotNull GridMenuItem<T> self,
                                    @NotNull String text,
                                    @Nullable @ClosureParams(FirstParam.FirstGenericType.class) Closure clickListener,
                                    @DelegatesTo(value = GridMenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def item = self.subMenu.addItem(text, adaptClickListener(clickListener))
        block.delegate = item
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        item
    }

    @NotNull
    static <T> GridMenuItem<T> item(@NotNull GridContextMenu<T> self,
                                    @NotNull Component component,
                                    @Nullable @ClosureParams(FirstParam.FirstGenericType.class) Closure clickListener,
                                    @DelegatesTo(value = GridMenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def item = self.addItem(component, adaptClickListener(clickListener))
        block.delegate = item
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        item
    }

    @NotNull
    static <T> GridMenuItem<T> item(@NotNull GridMenuItem<T> self,
                                    @NotNull Component component,
                                    @Nullable @ClosureParams(FirstParam.FirstGenericType.class) Closure clickListener,
                                    @DelegatesTo(value = GridMenuItem, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def item = self.subMenu.addItem(component, adaptClickListener(clickListener))
        block.delegate = item
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        item
    }
}
