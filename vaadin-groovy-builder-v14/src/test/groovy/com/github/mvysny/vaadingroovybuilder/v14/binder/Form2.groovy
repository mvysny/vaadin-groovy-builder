package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.data.binder.Binder
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
class Form2 extends VerticalLayout {
    final Binder<Person> binder
    NumberField testInt
    NumberField testLong
    NumberField testBD
    NumberField testBI

    Form2(Binder<Person> binder) {
        this.binder = binder
        testInt = numberField("Test Int:") {
            bind(binder).toInt2().bind("testInt")
        }
        testLong = numberField("Test Long:") {
            bind(binder).toLong2().bind("testLong")
        }
        testBD = numberField("Test BigDecimal:") {
            bind(binder).toBigDecimal2().bind("testBD")
        }
        testBI = numberField("Test BigInteger:") {
            bind(binder).toBigInteger2().bind("testBI")
        }
    }

    @CompileDynamic
    void clear() {
        [testInt, testLong, testBD, testBI].forEach { it.value = null }
    }
}
