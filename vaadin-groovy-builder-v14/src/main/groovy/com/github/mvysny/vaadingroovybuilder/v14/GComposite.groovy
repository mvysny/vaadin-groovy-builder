package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.dom.Element
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.util.stream.Stream

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.*

/**
 * Replaces Vaadin's UI auto-creation magic by explicit UI creation. You need to call [ui] to populate this composite with content:
 * <ul>
 * <li>The recommended way is to call [ui] in a field initializer; for example you can create a field named `root` and
 * call the `ui{}` there. See example below for details.</li>
 * <li>Alternatively you can call [ui] in the `init{}` block, or in the constructor</li>
 * <li>Alternatively you can override [initContent] and call [ui] there.</li>
 * </ul>
 * For example:
 * <pre>
 * class ButtonBar extends GComposite {
 *   private final root = ui {
 *     // create the component UI here; maybe even attach very simple listeners here
 *     horizontalLayout {
 *       button("ok") {
 *         addClickListener { okClicked() }
 *       }
 *       button("cancel") {
 *         addClickListener { cancelClicked() }
 *       }
 *     }
 *   }
 *
 *   ButtonBar() {
 *     // perform any further initialization here
 *   }
 *
 *   // listener methods here
 *   private void okClicked() {}
 *   private void cancelClicked() {}
 * }
 * </pre>
 */
@CompileStatic
abstract class GComposite extends Composite<Component> {
    @Nullable private Component root = null

    @Override @NotNull
    protected final Component initContent() {
        checkNotNull(root) { "The content has not yet been initialized, please call the ui() function in the constructor" }
    }

    /**
     * You can alternatively specify the content straight in the constructor if it is known upfront.
     * @param content
     */
    protected GComposite(@Nullable Component content = null) {
        super()
        root = content
    }

    // prevent accidental override
    @Override @NotNull
    final Component getContent() {
        super.getContent()
    }

    // prevent accidental override
    @Override
    final Element getElement() {
        super.getElement()
    }

    // prevent accidental override
    @Override
    Stream<Component> getChildren() {
        super.getChildren()
    }

    /**
     * Initializes the UI of this composite. Returns the component created by the block, so that we can store the created
     * component in the `root` field and access it later on, as shown above.
     */
    @NotNull
    protected final <T extends Component> T ui(
            @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure<T> block) {
        def root = this.root
        check(root == null) { "The content has already been initialized!" }
        block.delegate = new HasComponents() {
            @Override Element getElement() {
                throw new UnsupportedOperationException("Not expected to be called")
            }
            @Override void add(Component... components) {
                require(components.size() < 2) { "Too many components to add - composite can only host one! ${components.toList()}" }
                Component component = VaadinUtils.firstOrNull(components)
                if (component == null) {
                    return
                }
                check(root == null) { "The content has already been initialized!" }
                root = component
            }
        }

        block.resolveStrategy = Closure.DELEGATE_FIRST
        T component = block() as T
        this.root = root
        checkNotNull(component)
        checkNotNull(root) { "`block` must add exactly one component" }
        return component
    }
}
