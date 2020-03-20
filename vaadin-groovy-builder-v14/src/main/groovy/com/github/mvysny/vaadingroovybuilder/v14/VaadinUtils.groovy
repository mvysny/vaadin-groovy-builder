package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.ClickNotifier
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.ComponentEvent
import com.vaadin.flow.component.ComponentUtil
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.Text
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

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.require

/**
 * A collection of basic Vaadin utility extension functions.
 * @author mavi
 */
@CompileStatic
class VaadinUtils {
    /**
     * Fires given event on the component.
     */
    static void fireEvent(Component self, @NotNull ComponentEvent<? extends Component> event) {
        ComponentUtil.fireEvent(self, event)
    }

    /**
     * Adds {@link com.vaadin.flow.component.button.Button#click} functionality to all {@link ClickNotifier}s.
     * This function directly calls
     * all click listeners, thus it avoids the roundtrip to client and back.
     * It even works with browserless testing.
     */
    static <R extends Component & ClickNotifier<R>> void serverClick(R self) {
        fireEvent(self, new ClickEvent<R>(self, true, -1, -1, -1, -1, 1, -1, false, false, false, false))
    }

    /**
     * Appends a text node with given <code>text</code> to the component.
     */
    @NotNull
    static Text text(HasComponents self, String text, @DelegatesTo(value = Text, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block = {}) {
        VaadinDsl.init(self, new Text(text), block)
    }

    /**
     * Gets the alignment of the text in the component. One of <code>center</code>, <code>left</code>, <code>right</code>, <code>justify</code>.
     */
    @Nullable
    static String getTextAlign(Component self) {
        self.element.style.get("textAlign")
    }

    /**
     * Sets the alignment of the text in the component. One of <code>center</code>, <code>left</code>, <code>right</code>, <code>justify</code>.
     */
    static void setTextAlign(Component self, @Nullable String align) {
        self.element.style.set("textAlign", align)
    }
    /**
     * Either calls {@link Element#setAttribute} (if the <code>value</code> is not null), or
     * {@link Element#removeAttribute} (if the <code>value</code> is null).
     * @param attribute the name of the attribute.
     */
    static void setOrRemoveAttribute(Element self, @NotNull String attribute, @Nullable String value) {
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
    static String getTooltip(Component self) { self.element.getAttribute("title") }

    /**
     * Sets or removes the <code>title</code> attribute on component's element.
     */
    static void setTooltip(Component self, @Nullable String tooltip) { setOrRemoveAttribute(self.element, "title", tooltip) }

    /**
     * Makes the client-side listener call [Event.preventDefault()](https://developer.mozilla.org/en-US/docs/Web/API/Event/preventDefault)
     * on the event.
     *
     * @return this
     */
    @NotNull
    static DomListenerRegistration preventDefault(DomListenerRegistration self) { self.addEventData("event.preventDefault()"); self }

    /**
     * Adds the right-click (context-menu) listener to the component. Also causes the right-click browser
     * menu not to be shown on this component (see {@link #preventDefault}).
     */
    @NotNull
    static DomListenerRegistration addContextMenuListener(Component self, @NotNull DomEventListener listener) {
        preventDefault(self.element.addEventListener("contextmenu", listener))
    }

    /**
     * Removes the component from its parent. Does nothing if the component is not attached to a parent.
     */
    static void removeFromParent(Component self) {
        (self.parent.orElse(null) as HasComponents)?.remove(self)
    }

    static boolean containsWhitespace(String self) {
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
}
