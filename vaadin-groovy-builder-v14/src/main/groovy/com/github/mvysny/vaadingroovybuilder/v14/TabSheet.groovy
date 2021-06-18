package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.vaadingroovybuilder.v14.layout.Layouts
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.HasSize
import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.dom.Element
import com.vaadin.flow.shared.Registration
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.*
import static com.github.mvysny.vaadingroovybuilder.v14.html.HtmlUtils.div
import static com.github.mvysny.vaadingroovybuilder.v14.layout.Layouts.*

/**
 * A TabSheet - shows both the {@link Tabs} component and the tab contents.
 * <p></p>
 * You can add and populate tabs in two ways:
 * <ul><li>eagerly, by calling either {@link TabSheet#tab()} or {@link TabSheet#addTab(java.lang.String)} function,</li>
 * <li>lazily, by calling {@link TabSheet#addLazyTab(groovy.lang.Closure)}</li></ul>
 * Example code:
 * <pre>
 * tabSheet {
 *   tab("DSL-style tab") {
 *     span("Hello")
 *   }
 *   addTab("Old-school style", Span("Hi"))
 *   addLazyTab("Populated when first selected") { Span("Lazy") }
 * }
 * </pre>
 */
@CompileStatic
final class TabSheet extends GComposite implements HasStyle, HasSize {
    @NotNull
    private Tabs tabsComponent
    @NotNull
    private Div tabsContainer

    /**
     * Maps [Tab] to the contents of the tab.
     */
    private final Map<Tab, Component> tabsToComponents = new HashMap<>()

    /**
     * Maps [Tab] to the provider of the contents of the tab.
     */
    private final Map<Tab, Closure<? extends Component>> tabsToComponentProvider = new HashMap<>()

    @NotNull
    private final def root = ui {
        verticalLayout(delegate, false, false) {
            setWidthFull()
            Layouts.content(delegate as VerticalLayout) {
                align(stretch, top)
            }
            addClassName("tabsheet")

            tabsComponent = VaadinComponents.tabs(delegate) {}
            tabsContainer = div(delegate) {
                setWidthFull()
                // when TabSheet's height is defined, the following rules allow the container to grow or shrink as necessary.
                setExpand(delegate, true); setFlexShrink(delegate, 1);
                setMinHeight("0px")
                element.classList.add("tabsheet-container")
            }
        }
    }

    TabSheet() {
        tabsComponent.addSelectedChangeListener { update() }
    }

    /**
     * Adds a new tab to the tab host, with optional <code>label</code> and optional <code>contents</code>.
     * <p></p>
     * Example:
     * <pre>
     * tabSheet {
     *   tab("Hello") {
     *     verticalLayout {
     *       span("Hello, world!") {}
     *     }
     *   }
     * }
     * </pre>
     */
    @NotNull
    Tab tab(@Nullable String label = null,
            @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure<? extends Component> block = { null }) {
        Component root = null
        def dummy = new HasComponents() {
            @Override
            Element getElement() {
                throw new UnsupportedOperationException("Not expected to be called")
            }

            @Override
            void add(@NotNull Component... components) {
                require(components.size() < 2) { "Too many components to add - tab can only host one! ${components.toList()}" }
                Component component = VaadinUtils.firstOrNull(components)
                if (component != null) {
                    check(root == null) { "The content has already been initialized!" }
                    root = component
                }
            }
        }
        block.delegate = dummy
        block.resolveStrategy = Closure.DELEGATE_FIRST
        final Component content = block()
        return addTab(label, content)
    }

    /**
     * Adds a new tab to the tab host, with optional <code>label</code> and optional <code>contents</code>.
     * <p></p>
     * You can either provide the tab contents eagerly, or you can populate the tab
     * later on, by calling {@link #setTabContents(com.vaadin.flow.component.tabs.Tab, com.vaadin.flow.component.Component)} or
     * {@link VaadinUtils#getContents(com.vaadin.flow.component.tabs.Tab)}. To make the tab populate itself automatically when it's shown
     * for the first time, use {@link #addLazyTab}.
     */
    @NotNull
    Tab addTab(@Nullable String label = null, @Nullable Component contents = null) {
        Tab tab = VaadinComponents.tab(tabsComponent, label) {}
        tabsToComponents[tab] = contents
        update()
        return tab
    }

    /**
     * Adds a new tab to the tab host, with optional [label]. The tab contents is
     * constructed lazily when the tab is selected for the first time.
     */
    @NotNull
    Tab addLazyTab(@Nullable String label = null, @NotNull Closure<? extends Component> contentsProvider) {
        Tab tab = VaadinComponents.tab(tabsComponent, label) {}
        tabsToComponents.put(tab, null)
        tabsToComponentProvider[tab] = contentsProvider
        update()
        return tab
    }

    /**
     * Sets the contents of given [tab] to [newContents].
     */
    void setTabContents(@NotNull Tab tab, @Nullable Component newContents) {
        checkOurTab(tab)
        tabsToComponents[tab] = newContents
        tabsToComponentProvider.remove(tab)
        update()
    }

    /**
     * Finds a tab containing given [contents]. Returns null if there is no
     * such tab.
     */
    @Nullable
    Tab findTabWithContents(@NotNull Component contents) {
        tabsToComponents.entrySet().find { it.value == contents }?.key
    }

    /**
     * Finds a tab which transitively contains given [component].
     */
    @Nullable
    Tab findTabContaining(@NotNull Component component) {
        def contentComponents = tabsToComponents.values().findAll { it != null }.toSet()
        Component contents = VaadinUtils.findAncestorOrSelf(component) { contentComponents.contains(it) }
        return contents == null ? null : findTabWithContents(contents)
    }

    /**
     * Returns the contents of given [tab].
     */
    @Nullable
    Component getTabContents(@NotNull Tab tab) {
        checkOurTab(tab)
        return tabsToComponents[tab]
    }

    private void checkOurTab(@NotNull Tab tab) {
        require(tabsToComponents.containsKey(tab)) {
            "Tab $tab is not hosted in this TabSheet"
        }
    }

    /**
     * Removes a [tab]. If the tab is selected, another tab is selected automatically (if possible).
     */
    void remove(@NotNull Tab tab) {
        tabsToComponents.remove(tab)
        tabsComponent.remove(tab)
        update()
    }

    /**
     * Currently selected tab. Defaults to null since by default there are no tabs.
     */
    @Nullable
    Tab getSelectedTab() {
        tabsComponent.selectedTab
    }

    void setSelectedTab(@Nullable Tab tab) {
        tabsComponent.selectedTab = tab
    }

    /**
     * Returns the 0-based index of the currently selected tab.
     */
    int getSelectedIndex() {
        tabsComponent.selectedIndex
    }

    void setSelectedIndex(int value) {
        tabsComponent.selectedIndex = value
    }

    /**
     * Returns the current number of tabs.
     */
    int getTabCount() {
        tabsToComponents.keySet().size()
    }

    /**
     * The [orientation] of this tab sheet. Defaults to [Tabs.Orientation.HORIZONTAL].
     */
    @NotNull
    Tabs.Orientation getOrientation() {
        tabsComponent.orientation
    }

    void setOrientation(@NotNull Tabs.Orientation value) {
        tabsComponent.orientation = value
    }

    private void update() {
        Component currentTabComponent = tabsContainer.children.findFirst().orElse(null)
        Tab selectedTab1 = getSelectedTab()

        Component newTabComponent
        if (selectedTab1 == null) {
            newTabComponent = null
        } else {
            newTabComponent = tabsToComponents[selectedTab1]
            if (newTabComponent == null) {
                def provider = tabsToComponentProvider[selectedTab1]
                if (provider != null) {
                    newTabComponent = provider()
                    tabsToComponentProvider.remove(selectedTab1)
                    tabsToComponents[selectedTab1] = newTabComponent
                }
            }
        }

        if (currentTabComponent != newTabComponent) {
            tabsContainer.removeAll()
            if (newTabComponent != null) {
                tabsContainer.add(newTabComponent)
            }
        }
    }

    /**
     * Removes all tabs.
     */
    void removeAll() {
        tabsToComponents.clear()
        tabsComponent.removeAll()
        update()
    }

    /**
     * Returns a live list of all tabs. The list is read-only but live: it reflects changes when tabs are added or removed.
     */
    @NotNull
    final List<Tab> tabs = new AbstractList<Tab>() {
        @Override
        Tab get(int index) {
            tabsComponent.getComponentAt(index) as Tab
        }

        @Override
        int size() {
            getTabCount()
        }
    }

    /**
     * Adds a listener fired when the current tab changes (both by user or programatically).
     * @param listener the listener, not null.
     * @return registration used to remove the listener.
     */
    @NotNull
    Registration addSelectedChangeListener(@NotNull ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        tabsComponent.addSelectedChangeListener(listener)
    }
}
