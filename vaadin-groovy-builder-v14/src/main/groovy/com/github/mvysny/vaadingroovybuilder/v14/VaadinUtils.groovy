package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.*
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.dom.ClassList
import com.vaadin.flow.dom.DomEventListener
import com.vaadin.flow.dom.DomListenerRegistration
import com.vaadin.flow.dom.Element
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors
import java.util.stream.Stream

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.checkNotNull
import static com.github.mvysny.vaadingroovybuilder.v14.Utils.require
import static java.util.Objects.requireNonNull

/**
 * A collection of basic Vaadin utility extension functions.
 * @author mavi
 */
@CompileStatic
class VaadinUtils {
    /**
     * Fires given event on the component.
     */
    static void fireEvent(@NotNull Component self, @NotNull ComponentEvent<? extends Component> event) {
        ComponentUtil.fireEvent(self, event)
    }

    /**
     * Adds {@link com.vaadin.flow.component.button.Button#click} functionality to all {@link ClickNotifier}s.
     * This function directly calls
     * all click listeners, thus it avoids the roundtrip to client and back.
     * It even works with browserless testing.
     */
    static <R extends Component & ClickNotifier<R>> void serverClick(@NotNull R self) {
        fireEvent(self, new ClickEvent<R>(self, true, -1, -1, -1, -1, 1, -1, false, false, false, false))
    }

    /**
     * Appends a text node with given <code>text</code> to the component.
     */
    @NotNull
    static Text text(@NotNull HasComponents self, String text, @DelegatesTo(value = Text, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        VaadinDsl.init(self, new Text(text), block)
    }

    /**
     * Gets the alignment of the text in the component. One of <code>center</code>, <code>left</code>, <code>right</code>, <code>justify</code>.
     */
    @Nullable
    static String getTextAlign(@NotNull Component self) {
        self.element.style.get("textAlign")
    }

    /**
     * Sets the alignment of the text in the component. One of <code>center</code>, <code>left</code>, <code>right</code>, <code>justify</code>.
     */
    static void setTextAlign(@NotNull Component self, @Nullable String align) {
        self.element.style.set("textAlign", align)
    }
    /**
     * Either calls {@link Element#setAttribute} (if the <code>value</code> is not null), or
     * {@link Element#removeAttribute} (if the <code>value</code> is null).
     * @param attribute the name of the attribute.
     */
    static void setOrRemoveAttribute(@NotNull Element self, @NotNull String attribute, @Nullable String value) {
        if (value == null) {
            self.removeAttribute(attribute)
        } else {
            self.setAttribute(attribute, value)
        }
    }

    /**
     * Gets the <code>title</code> attribute on component's element.
     */
    @Nullable
    static String getTooltip(@NotNull Component self) { self.element.getAttribute("title") }

    /**
     * Sets or removes the <code>title</code> attribute on component's element.
     */
    static void setTooltip(@NotNull Component self, @Nullable String tooltip) { setOrRemoveAttribute(self.element, "title", tooltip) }

    /**
     * Makes the client-side listener call [Event.preventDefault()](https://developer.mozilla.org/en-US/docs/Web/API/Event/preventDefault)
     * on the event.
     *
     * @return this
     */
    @NotNull
    static DomListenerRegistration preventDefault(@NotNull DomListenerRegistration self) { self.addEventData("event.preventDefault()"); self }

    /**
     * Adds the right-click (context-menu) listener to the component. Also causes the right-click browser
     * menu not to be shown on this component (see {@link #preventDefault}).
     */
    @NotNull
    static DomListenerRegistration addContextMenuListener(@NotNull Component self, @NotNull DomEventListener listener) {
        preventDefault(self.element.addEventListener("contextmenu", listener))
    }

    /**
     * Removes the component from its parent. Does nothing if the component is not attached to a parent.
     */
    static void removeFromParent(@NotNull Component self) {
        (self.parent.orElse(null) as HasComponents)?.remove(self)
    }

    static boolean containsWhitespace(@NotNull String self) {
        for (int i = 0; i < self.length(); i++) {
            if (Character.isWhitespace(self.charAt(i))) {
                return true
            }
        }
        return false
    }

    /**
     * Toggles className - removes it if it was there, or adds it if it wasn't there.
     * @param className the class name to toggle, cannot contain spaces.
     */
    static void toggle(ClassList self, @NotNull String className) {
        require(!containsWhitespace(className)) { "'$className' cannot contain whitespace" }
        self.set(className, !self.contains(className))
    }

    /**
     * Finds component's parent, parent's parent (etc) which satisfies given predicate.
     * Returns null if there is no such parent.
     */
    @Nullable
    static Component findAncestor(Component self, @NotNull Predicate<Component> predicate) {
        findAncestorOrSelf(self) { it != self && predicate.test(it) }
    }

    /**
     * Finds component, component's parent, parent's parent (etc) which satisfies given (predicate).
     * Returns null if no component on the ancestor-or-self axis satisfies.
     */
    @Nullable
    static Component findAncestorOrSelf(Component self, @NotNull Predicate<Component> predicate) {
        if (predicate.test(self)) {
            return self
        }
        Component p = self.parent.orElse(null)
        if (p == null) {
            return null
        }
        return findAncestorOrSelf(p, predicate)
    }

    /**
     * Checks if this component is nested in <code>potentialAncestor</code>.
     */
    static boolean isNestedIn(Component self, @NotNull Component potentialAncestor) {
        findAncestor(self) { it == potentialAncestor } != null
    }

    /**
     * Walks over this component and all descendants of this component, breadth-first.
     * @return iterable which iteratively walks over this component and all of its descendants.
     */
    @NotNull
    static Iterable<Component> walk(final Component self) {
        return new Iterable<Component>() {
            @Override
            Iterator<Component> iterator() {
                return new TreeIterator<Component>(self, new Function<Component, Iterator<Component>>() {
                    @Override
                    Iterator<Component> apply(Component t) {
                        return t.children.iterator()
                    }
                })
            }
        }
    }

    @NotNull
    static <T> List<T> toList(@NotNull Stream<T> self) {
        self.collect(Collectors.toList())
    }

    @NotNull
    static <T> T[] toArray(@NotNull Stream<T> self, @NotNull T[] emptyArray) {
        toList(self).toArray(emptyArray)
    }

    @Nullable
    static <T> T firstOrNull(@NotNull Iterable<T> iterable) {
        def iterator = iterable.iterator()
        (iterator.hasNext() ? iterator.next() : null) as T
    }

    @Nullable
    static <T> T firstOrNull(@NotNull List<T> list) {
        list.isEmpty() ? null : list.get(0)
    }

    @Nullable
    static <T> boolean isEmpty(@NotNull T[] self) {
        self.length == 0
    }

    @Nullable
    static <T> T firstOrNull(@NotNull T[] array) {
        isEmpty(array) ? null : array[0]
    }

    /**
     * Returns the getter method for given property name; fails if there is no such getter.
     */
    @NotNull
    static Getter getGetter(@NotNull Class<?> self, @NotNull String propertyName) {
        return new Getter(self, propertyName)
    }

    /**
     * Clones this object by serialization and returns the deserialized clone.
     * @return the clone of this
     */
    @NotNull
    static <T extends Serializable> T cloneBySerialization(@NotNull T self) {
        self.getClass().cast(deserialize(serializeToBytes(self))) as T
    }

    @Nullable
    static Object deserialize(@NotNull byte[] self) {
        new ObjectInputStream(new ByteArrayInputStream(self)).readObject()
    }

    /**
     * Serializes the object to a byte array
     * @return the byte array containing this object serialized form.
     */
    @NotNull
    static byte[] serializeToBytes(@Nullable Serializable obj) {
        def bout = new ByteArrayOutputStream()
        new ObjectOutputStream(bout).writeObject(obj)
        return bout.toByteArray()
    }

    /**
     * Adds the given components as children of this component.
     * <p>
     * In case the any of the specified components has already been added to
     * another parent, it will be removed from there and added to this one.
     */
    static void add(@NotNull HasComponents self, @NotNull Component component) {
        // a workaround for some crazy Groovy compiler bug:
        // `new VerticalLayout().add(new Button())` wouldn't compile for some reason in older Groovy version
        Component[] a = [component]
        self.add(a)
    }

    /**
     * Checks whether this component is currently attached to an [UI].
     */
    static boolean isAttached(@NotNull Component self) {
        // see https://github.com/vaadin/flow/issues/7911
        def ui = self.getUI().orElse(null)
        if (ui == null) {
            return false
        }
        return !ui.isClosing()
    }

    /**
     * Returns the current index of a tab within its [Tabs] container.
     */
    static int getIndex(@NotNull Tab self) {
        getOwner(self).indexOf(self)
    }

    /**
     * Returns the owner [Tabs] of this tab. Fails if the tab is not attached to any [Tabs] owner.
     */
    @NotNull
    static Tabs getOwner(@NotNull Tab self) {
        checkNotNull((self.parent.orElse(null)) as Tabs) { "tab $this is not attached to a parent" }
    }

    @NotNull
    static TabSheet getOwnerTabSheet(@NotNull Tab self) {
        checkNotNull(findAncestor(self) { it instanceof TabSheet }) { "tab $this is not attached to a TabSheet" } as TabSheet
    }

    /**
     * Returns or sets this tab contents in the [TabSheet]. Works only for tabs nested in a [TabSheet].
     */
    @Nullable
    static Component getContents(@NotNull Tab self) {
        getOwnerTabSheet(self).getTabContents(self)
    }

    static void setContents(@NotNull Tab self, @Nullable Component contents) {
        getOwnerTabSheet(self).setTabContents(self, contents)
    }

    /**
     * Inserts [newNode] as a child, right before an [existingNode].
     */
    static void insertBefore(@NotNull Element self, @NotNull Element newNode, @NotNull Element existingNode) {
        Element parent = existingNode.parent
        requireNonNull(parent) { "$existingNode has no parent element" }
        require(parent == self) { "$existingNode is not nested in $self" }
        self.insertChild(self.indexOfChild(existingNode), newNode)
    }

    /**
     * Inserts this component as a child, right before an [existing] one.
     * <p></p>
     * In case the specified component has already been added to another parent,
     * it will be removed from there and added to this one.
     */
    static void insertBefore(@NotNull HasOrderedComponents<?> self,
                             @NotNull Component newComponent,
                             @NotNull Component existing) {
        Component parent = existing.parent.orElse(null)
        requireNonNull(parent) { "$existing has no parent" }
        require(parent == self) { "$existing is not nested in $self" }
        self.addComponentAtIndex(self.indexOf(existing), newComponent)
    }
}
