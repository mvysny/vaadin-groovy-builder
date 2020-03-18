package com.vaadin.starter.beveragebuddy.backend

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import javax.validation.constraints.*
import java.time.LocalDate

/**
 * @author mavi
 */
@CompileStatic
@TupleConstructor
@EqualsAndHashCode(includes = ["id"])
@ToString
class Review {
    Long id = null

//    @NotNull
//    @Min(1L)
//    @Max(5L)
    int score = 1

//    @NotBlank
//    @Size(min = 3)
    String name = ""

//    @NotNull
//    @PastOrPresent
    LocalDate date = LocalDate.now()

//    @NotNull
    Category category = null

//    @NotNull
//    @Min(1L)
//    @Max(99L)
    int count = 1

    Review copy() {
        new Review(id, score, name, date, category, count)
    }
}
