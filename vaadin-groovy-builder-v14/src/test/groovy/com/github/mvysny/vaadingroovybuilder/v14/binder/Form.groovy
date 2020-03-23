package com.github.mvysny.vaadingroovybuilder.v14.binder

import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

import java.time.LocalDate

@CompileStatic
class Form extends VerticalLayout {
    final Binder<Person> binder

    TextField fullName
    DatePicker dateOfBirth
    Checkbox isAlive
    Checkbox testBoolean
    TextArea comment
    TextField testDouble
    TextField testInt
    TextField testLong
    TextField testBD
    TextField testBI
    DatePicker testInstant

    Form(Binder<Person> binder) {
        this.binder = binder
        fullName = textField("Full Name:") {
            // binding to a BeanValidationBinder will also validate the value automatically.
            // please read https://vaadin.com/docs/-/part/framework/datamodel/datamodel-forms.html for more information
            bind(binder).trimmingConverter().bind("fullName")
            value = "Zaphod Beeblebrox"
        }
        dateOfBirth = datePicker("Date of Birth:") {
            bind(binder).bind("dateOfBirth")
            value = LocalDate.of(2010, 1, 25)
        }
        isAlive = checkbox("Is Alive") {
            bind(binder).bind("alive")
        }
        testBoolean = checkbox("Test Boolean:") {
            bind(binder).bind("testBoolean")
        }
        comment = textArea("Comment:") {
            bind(binder).bind("comment")
            value = "some comment"
        }
        testDouble = textField("Test Double:") {
            bind(binder).toDouble().bind("testDouble")
        }
        testInt = textField("Test Int:") {
            bind(binder).toInt().bind("testInt")
        }
        testLong = textField("Test Long:") {
            bind(binder).toLong().bind("testLong")
        }
        testBD = textField("Test BigDecimal:") {
            bind(binder).toBigDecimal().bind("testBD")
        }
        testBI = textField("Test BigInteger:") {
            bind(binder).toBigInteger().bind("testBI")
        }
        testInstant = datePicker("Created:") {
            bind(binder).toInstant().bind("created")
        }
    }

    @CompileDynamic
    void clear() {
        [fullName, testDouble, testInt, testLong, testBD, testBI].forEach { it.value = "" }
        comment.value = ""
        dateOfBirth.value = null
        isAlive.value = false
    }
}

