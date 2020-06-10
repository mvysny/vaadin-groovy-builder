package com.github.mvysny.vaadingroovybuilder.v14


import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.vaadingroovybuilder.v14.binder.Person
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.ironlist.IronList
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@CompileStatic
class IronListUtilsTest {
    @BeforeEach
    void setup() { MockVaadin.setup() }

    @AfterEach
    void tearDown() { MockVaadin.tearDown() }

    @Test
    void "iron list dsl - basic properties"() {
        IronList<String> il = UI.getCurrent().ironList {}
        il.refresh()
        // todo assert that it has 0 items. Need support from Karibu-Testing
    }

    @Test
    void "serialization"() {
        IronList<Person> il = new IronList<Person>()
        il.setRenderer { it.toString() }
        il.cloneBySerialization()
    }
}
