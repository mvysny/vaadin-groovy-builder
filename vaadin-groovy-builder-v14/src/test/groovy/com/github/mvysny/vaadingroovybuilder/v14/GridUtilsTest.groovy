package com.github.mvysny.vaadingroovybuilder.v14

import com.github.mvysny.kaributesting.v10.GridKt
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.vaadingroovybuilder.v14.binder.Person
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridSortOrder
import com.vaadin.flow.data.provider.ListDataProvider
import com.vaadin.flow.data.provider.SortDirection
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static com.github.mvysny.kaributesting.v10.UtilsKt.expectList
import static kotlin.test.AssertionsKt.expect

@CompileStatic
class GridUtilsTest {
    @BeforeEach
    void setup() { MockVaadin.setup() }

    @AfterEach
    void tearDown() { MockVaadin.tearDown() }

    private static void sort(Grid<?> grid, String sortSpec) {
        def col = grid.getColumnByKey(sortSpec.split()[0])
        Utils.require(col.isSortable()) { "Column for $sortSpec is not marked sortable" }
        def order = new GridSortOrder(col, sortSpec.split()[1] == "desc" ? SortDirection.DESCENDING : SortDirection.ASCENDING)
        grid.sort([order])
    }

    @Test
    void "sets column by default to sortable"() {
        def grid = UI.current.grid(Person) {
            addColumnForProperty("fullName") {}
        }
        expect(["fullName"]) {
            grid.getColumnByKey("fullName").getSortOrder(SortDirection.ASCENDING).toList().collect { it.sorted }
        }
    }

    @Test
    void "column header is set properly"() {
        def grid = UI.current.grid(Person) {
            addColumnForProperty("fullName") {}
            addColumnForProperty("alive") {}
            addColumnForProperty("dateOfBirth") {}
        }
        expect("Full Name") { GridKt.getHeader2(grid.getColumnByKey("fullName")) }
        expect("Alive") { GridKt.getHeader2(grid.getColumnByKey("alive")) }
        expect("Date Of Birth") { GridKt.getHeader2(grid.getColumnByKey("dateOfBirth")) }
    }

    @Test
    void "sorting by column also works with in-memory container"() {
        def grid = UI.current.grid(Person) {
            addColumnForProperty("fullName") {}
            setItems((0..2).collect { new Person(fullName: it.toString()) })
        }
        expect(ListDataProvider) { grid.dataProvider.getClass() }
        sort(grid, "fullName desc")
        expectList(["2", "1", "0"] as String[]) {
            GridKt._fetch(grid, 0, Integer.MAX_VALUE).collect { it.fullName }
        }
    }

    @Test
    void "column isExpand"() {
        def grid = new Grid<Person>(Person, false)
        def col = grid.addColumnForProperty("alive") {}
        expect(1) { col.flexGrow }  // by default the flexGrow is 1
        def col2 = grid.addColumnForProperty("fullName") { setExpand(false) }
        expect(0) { col2.flexGrow }
    }
}
