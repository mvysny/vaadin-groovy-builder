package com.vaadin.starter.beveragebuddy.backend

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * Represents a beverage category.
 * @author mavi
 */
@CompileStatic
@TupleConstructor
@EqualsAndHashCode(includes = ["id"])
@ToString
class Category implements Serializable {
    Long id = null
    String name = null

    Category copy() {
        new Category(id, name)
    }
}
