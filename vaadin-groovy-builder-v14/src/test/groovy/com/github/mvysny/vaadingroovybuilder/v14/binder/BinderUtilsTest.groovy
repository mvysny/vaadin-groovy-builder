package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.time.ZoneId

import static kotlin.test.AssertionsKt.expect

@CompileStatic
class BinderUtilsTest {
    @BeforeEach void setup() { MockVaadin.setup() }
    @AfterEach void tearDown() { MockVaadin.tearDown() }

    @Test void "ReadBeanWithNullFields"() {
        // https://github.com/vaadin/framework/issues/8664
        def binder = new Binder(Person)
        def form = new Form(binder)
        form.clear()
        binder.readBean(new Person())
        expect("") { form.fullName.value }
        expect(null) { form.dateOfBirth.value }
        expect(false) { form.isAlive.value }
        expect("") { form.comment.value }
        expect("") { form.testDouble.value }
        expect("") { form.testInt.value }
        expect("") { form.testBD.value }
        expect("") { form.testBI.value }
        expect("") { form.testLong.value }
    }

    @Test void "WriteBeanWithNullFields"() {
        // https://github.com/vaadin/framework/issues/8664
        def binder = new Binder(Person)
        def form = new Form(binder)
        form.clear()
        def person = new Person("Zaphod Beeblebrox",
                LocalDate.of(2010, 1, 25),
                false,
                null,
                "some comment",
                25.5d,
                5,
                555L,
                new BigDecimal("77.11"),
                new BigInteger("123"))
        binder.writeBean(person)
        expect(new Person(testBoolean: false)) { person }
    }

    @Test void "SimpleBindings"() {
        def binder = new Binder(Person)
        def form = new Form(binder)
        form.testDouble.value = "25.5"
        form.testInt.value = "5"
        form.testLong.value = "555"
        form.testBI.value = "123"
        form.testBD.value = "77.11"
        form.testBoolean.value = true
        form.testInstant.value = LocalDate.of(2015, 1, 25)
        def person = new Person()
        expect(true) { binder.writeBeanIfValid(person) }
        def instant = LocalDate.of(2015, 1, 25).atStartOfDay(ZoneId.of("UTC")).toInstant()
        expect(new Person("Zaphod Beeblebrox", LocalDate.of(2010, 1, 25), false, true, "some comment",
                25.5d, 5, 555L, new BigDecimal("77.11"), new BigInteger("123"), instant)) { person }
    }

    @Test void "ValidatingBindings"() {
        def binder = new BeanValidationBinder(Person)
        new Form(binder)
        def person = new Person()
        expect(true) { binder.writeBeanIfValid(person) }
        expect(new Person("Zaphod Beeblebrox", LocalDate.of(2010, 1, 25), false, false, "some comment")) { person }
    }

    @Test void "test validation errors"() {
        def binder = new BeanValidationBinder(Person)
        def form = new Form(binder)
        form.fullName.value = ""
        def person = new Person()
        expect(false) { binder.writeBeanIfValid(person) }
        expect(new Person()) { person }  // make sure that no value has been written
    }

    @Test void "test that bind() supports both non-nullable and nullable properties"() {
        def binder = new BeanValidationBinder(TestingPerson)
        new TextField().bind(binder).bind("foo")
        new TextField().bind(binder).bind("baz")
    }

    @Test void "SimpleBindings with NumberField - write"() {
        def binder = new Binder(Person)
        def form = new Form2(binder)
        form.testInt.value = 5.0d
        form.testLong.value = 555.0d
        form.testBI.value = 123.0d
        form.testBD.value = 77.11d
        def person = new Person()
        expect(true) { binder.writeBeanIfValid(person) }
        expect(new Person(testInt: 5, testLong: 555L, testBD: new BigDecimal("77.11"), testBI: new BigInteger("123"))) { person }
    }

    @Test void "SimpleBindings with NumberField - load"() {
        def binder = new Binder(Person)
        def form = new Form2(binder)
        binder.readBean(new Person(testInt: 5, testLong: 555L, testBD: new BigDecimal("77.11"), testBI: new BigInteger("123")))
        expect(5.0d) { form.testInt.value }
        expect(555.0d) { form.testLong.value }
        expect(123.0d) { form.testBI.value }
        expect(77.11d) { form.testBD.value }
    }
}
