package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static com.github.mvysny.kaributesting.v10.groovy.LocatorG.*
import static com.github.mvysny.vaadingroovybuilder.v14.TestUtils.*

/**
 * This is also an API test that we can create components based on [KComposite]. The components should be final,
 * so that developers prefer [composition over inheritance](https://reactjs.org/docs/composition-vs-inheritance.html).
 */
@CompileStatic
class ButtonBar extends GComposite {
    private final root = ui {
        // create the component UI here; maybe even attach very simple listeners here
        horizontalLayout {
            button("ok") {
                addClickListener { okClicked() }
            }
            button("cancel") {
                addClickListener { cancelClicked() }
            }
        }
    }

    ButtonBar() {
        // perform any further initialization here
    }

    // listener methods here
    private void okClicked() {}

    private void cancelClicked() {}
}

@CompileStatic
class GCompositeTest {
    @BeforeEach
    void setup() { MockVaadin.setup() }

    @AfterEach
    void tearDown() { MockVaadin.tearDown() }

    @Test
    void "lookup"() {
        UI.getCurrent().add(new ButtonBar())
        _get(Button) { caption = "ok" }
    }

    @Test
    void "serialize"() {
        UI.getCurrent().add(new ButtonBar())
        UI.getCurrent().cloneBySerialization()
    }

    @Test
    void "uninitialized composite fails with informative exception"() {
        expectThrows(IllegalStateException, "The content has not yet been initialized") {
            UI.getCurrent().add(new GComposite() {})
        }
    }
}
