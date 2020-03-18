package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.server.InputStreamFactory
import com.vaadin.flow.server.StreamResource
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author mavi
 */
@CompileStatic
class HtmlTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test
    void smoke() {
        def isf = new InputStreamFactory() {
            @Override
            InputStream createInputStream() {
                return new ByteArrayInputStream("foo".bytes)
            }
        }
        UI.getCurrent().flexLayout {
            div{}
            h1{}
            h2{}
            h3{}
            h4{}
            h5{}
            h6{}
            hr{}
            p{}
            em{}
            span{}
            anchor("") {}
            anchor(new StreamResource("foo.txt", isf), null) {}
            image{}
            image(new StreamResource("foo.txt", isf)) {}
            label{}
            input{}
            nativeButton{}
            strong{}
            br{}
            ol{}
            li{}
            iframe{}
            article{}
            aside{}
            dl{}
            dd{}
            dt{}
            footer{}
            header{}
            pre{}
            ul{}
        }
    }
}
