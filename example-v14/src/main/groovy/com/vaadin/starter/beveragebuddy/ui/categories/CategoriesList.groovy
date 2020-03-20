package com.vaadin.starter.beveragebuddy.ui.categories

import com.github.mvysny.vaadingroovybuilder.v14.GridUtils
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.function.SerializableConsumer
import com.vaadin.flow.function.SerializableFunction
import com.vaadin.flow.function.SerializableRunnable
import com.vaadin.flow.function.ValueProvider
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.starter.beveragebuddy.backend.Category
import com.vaadin.starter.beveragebuddy.backend.CategoryService
import com.vaadin.starter.beveragebuddy.backend.ReviewService
import com.vaadin.starter.beveragebuddy.ui.MainLayout
import com.vaadin.starter.beveragebuddy.ui.Toolbar
import groovy.transform.CompileStatic

/**
 * Displays the list of available categories, with a search filter as well as
 * buttons to add a new category or edit existing ones.
 */
@Route(value = "categories", layout = MainLayout)
@PageTitle("Categories List")
@CompileStatic
class CategoriesList extends VerticalLayout {

    private H3 header
    private Toolbar toolbar
    private Grid categoryGrid
    // can't retrieve GridContextMenu from Grid: https://github.com/vaadin/vaadin-grid-flow/issues/523
    GridContextMenu<Object> gridContextMenu

    private final CategoryEditorDialog editorDialog = new CategoryEditorDialog(
            new SerializableConsumer<Category>() {
                @Override
                void accept(Category category) {
                    saveCategory(category)
                }
            }, new SerializableConsumer<Category>() {
        @Override
        void accept(Category category) {
            deleteCategory(category)
        }
    })

    CategoriesList() {
        setPadding(false); setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH)
        toolbar = new Toolbar("New category")
        add(toolbar)
        toolbar.with {
            onSearch = new SerializableConsumer<String>() {
                @Override
                void accept(String category) {
                    updateView()
                }
            }
            onCreate = new SerializableRunnable() {
                @Override
                void run() {
                    editorDialog.createNew()
                }
            }
        }
        header = h3 {}
        categoryGrid = grid(Category) {
            setExpand(true)
            addColumnForProperty("name") {
                setHeader("Category")
            }
            addColumn(new ValueProvider<Category, String>() {
                @Override
                String apply(Category o) {
                    return getReviewCount(o)
                }
            }).setHeader("Beverages")
            addColumn(new ComponentRenderer<Button, Category>(new SerializableFunction<Category, Button>() {
                @Override
                Button apply(Category category) {
                    return createEditButton(category)
                }
            })).with {
                flexGrow = 0; key = "edit"
            }
            element.themeList.add("row-dividers")

            this.gridContextMenu = gridContextMenu {
                item("New", { _ -> editorDialog.createNew() }) {}
                item("Edit", { cat -> if (cat != null) edit(cat as Category) }) {}
                item("Delete", { cat -> if (cat != null) deleteCategory(cat as Category) }) {}
            }
        }

        updateView()
    }

    private Button createEditButton(Category category) {
        def btn = new Button("Edit")
        btn.with {
            icon = new Icon(VaadinIcon.EDIT)
            addClassName("review__edit")
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            addClickListener { edit(category) }
        }
        btn
    }

    private void edit(Category category) {
        editorDialog.edit(category)
    }

    private static String getReviewCount(Category category) {
        ReviewService.INSTANCE.getTotalCountForReviewsInCategory(category.id).toString()
    }

    private void updateView() {
        def categories = CategoryService.INSTANCE.findCategories(toolbar.searchText)
        if (!toolbar.searchText.isBlank()) {
            header.text = "Search for “${toolbar.searchText}”"
        } else {
            header.text = "Categories"
        }
        categoryGrid.setItems(categories)
    }

    private void saveCategory(Category category) {
        def creating = category.id == null
        CategoryService.INSTANCE.saveCategory(category)
        def op = creating ? "added" : "saved"
        Notification.show("Category successfully ${op}.", 3000, Notification.Position.BOTTOM_START)
        updateView()
    }

    private void deleteCategory(Category category) {
        CategoryService.INSTANCE.deleteCategory(category)
        Notification.show("Category successfully deleted.", 3000, Notification.Position.BOTTOM_START)
        updateView()
    }
}
