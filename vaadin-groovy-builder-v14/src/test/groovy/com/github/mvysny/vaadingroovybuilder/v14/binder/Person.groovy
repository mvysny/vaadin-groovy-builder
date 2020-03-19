package com.github.mvysny.vaadingroovybuilder.v14.binder

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Size
import java.time.Instant
import java.time.LocalDate

/**
 * @author mavi
 */
@CompileStatic
@TupleConstructor
@EqualsAndHashCode
@ToString
class Person implements Serializable {
    @NotNull
    @Size(min = 3, max = 200)
    String fullName

    @NotNull
    @PastOrPresent
    LocalDate dateOfBirth

    @NotNull
    boolean alive = false

    Boolean testBoolean

    String comment

    Double testDouble

    Integer testInt

    Long testLong

    BigDecimal testBD

    BigInteger testBI

    Instant created
}
