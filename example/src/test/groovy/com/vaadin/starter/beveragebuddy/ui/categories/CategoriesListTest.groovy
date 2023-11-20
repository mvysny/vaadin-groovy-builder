package com.vaadin.starter.beveragebuddy.ui.categories

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.starter.beveragebuddy.backend.Category
import com.vaadin.starter.beveragebuddy.backend.CategoryService
import com.vaadin.starter.beveragebuddy.ui.AbstractAppTest
import com.vaadin.starter.beveragebuddy.ui.EditorDialogFrame
import groovy.transform.CompileStatic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static com.github.mvysny.kaributesting.v10.NotificationsKt.*
import static com.github.mvysny.kaributesting.v10.groovy.LocatorG.*
import static org.junit.jupiter.api.Assertions.*

@CompileStatic
class CategoriesListTest extends AbstractAppTest {
    @BeforeEach
    void navigate() {
        UI.getCurrent().navigate("categories")
        _get(CategoriesList)  // make sure that the navigation succeeded
    }

    @Test void "grid lists all categories"() {
        // prepare testing data
        CategoryService.INSTANCE.saveCategory(new Category(name: "Beers"))
        UI.getCurrent().page.reload()

        // now the "Categories" list should be displayed. Look up the Grid and assert on its contents.
        def grid = _get(Grid)
        assertEquals(1, grid.dataProvider._size())
    }

    @Test void "create new category"() {
        UI.getCurrent().navigate("categories")
        _get(Button) { caption = "New category" } ._click()

        // make sure that the "New Category" dialog is opened
        _expectOne(EditorDialogFrame)

        // do the happy flow: fill in the form with valid values and click "Save"
        _get(TextField) { caption = "Category Name" } ._value = "Beer"
        clearNotifications()
        _get(Button) { caption = "Save" } ._click()
        expectNotifications("Category successfully added.")

        _expectNone(EditorDialogFrame)     // expect the dialog to close
        assertEquals(["Beer"], CategoryService.INSTANCE.findAll().collect { it.name })
    }

    @Test void "edit existing category"() {
        def cat = new Category(name: "Beers")
        CategoryService.INSTANCE.saveCategory(cat)
        UI.getCurrent().page.reload()
        Grid<Category> grid = _get(Grid)
        grid.expectRow(0, "Beers", "0", "Button[text='Edit', icon='vaadin:edit', @class='review__edit', @theme='tertiary']")
        grid._clickRenderer(0, "edit")

        // make sure that the "Edit Category" dialog is opened
        _expectOne(EditorDialogFrame)
        assertEquals(cat.name, _get(TextField) { caption = "Category Name" } ._value)
    }

    @Test void "edit existing category via context menu"() {
        def cat = new Category(name: "Beers")
        CategoryService.INSTANCE.saveCategory(cat)
        UI.getCurrent().page.reload()
        Grid<Category> grid = _get(Grid)
        grid.expectRow(0, "Beers", "0", "Button[text='Edit', icon='vaadin:edit', @class='review__edit', @theme='tertiary']")
        _get(CategoriesList).gridContextMenu._clickItemWithCaption("Edit", cat)

        // make sure that the "Edit Category" dialog is opened
        _expectOne(EditorDialogFrame)
        assertEquals(cat.name, _get(TextField) { caption = "Category Name" } ._value)
    }

    @Test void "delete existing category via context menu"() {
        def cat = new Category(name: "Beers")
        CategoryService.INSTANCE.saveCategory(cat)
        UI.getCurrent().page.reload()
        Grid<Category> grid = _get(Grid)
        grid.expectRow(0, "Beers", "0", "Button[text='Edit', icon='vaadin:edit', @class='review__edit', @theme='tertiary']")
        _get(CategoriesList).gridContextMenu._clickItemWithCaption("Delete", cat)
        assertEquals([], CategoryService.INSTANCE.findAll())
        _get(Grid).expectRows(0)
    }
}
