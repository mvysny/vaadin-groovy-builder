package com.vaadin.starter.beveragebuddy.ui.reviews

import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder
import com.vaadin.starter.beveragebuddy.backend.Category
import com.vaadin.starter.beveragebuddy.backend.CategoryService
import com.vaadin.starter.beveragebuddy.backend.Review
import com.vaadin.starter.beveragebuddy.ui.EditorForm
import groovy.transform.CompileStatic

import java.time.LocalDate

/**
 * A dialog for editing [Review] objects.
 */
@CompileStatic
class ReviewEditorForm implements EditorForm<Review> {
    @Override
    String getItemType() {
        return "Review"
    }

    private final Binder<Review> binder = new BeanValidationBinder<Review>(Review)

    @Override
    Binder<Review> getBinder() {
        return binder
    }

    @Override
    FormLayout getComponent() {
        def layout = new FormLayout()
        layout.with {
            // to propagate the changes made in the fields by the user, we will use binder to bind the field to the Review property.

            textField("Beverage name") {
                // no need to have validators here: they are automatically picked up from the bean field.
                bind(binder).trimmingConverter().bind("name")
            }
            textField("Times tasted") {
                pattern = "[0-9]*"
                setPreventInvalidInput(true)
                bind(binder).toInt().bind("count")
            }
            comboBox(Category, "Choose a category") {
                // we need to show a list of options for the user to choose from. For every option we need to retain at least:
                // 1. the category ID (to bind it to Review::category)
                // 2. the category name (to show it to the user when the combobox's option list is expanded)
                // since the Category class already provides these values, we will simply use that as the data source for the options.
                //
                // now we need to configure the item label generator so that we can extract the name out of Category and display it to the user:
                setItemLabelGenerator { (it as Category).name }

                // can't create new Categories here
                setAllowCustomValue(false)

                // provide the list of options as a DataProvider, providing instances of Category
                setItems(new ComboBox.ItemFilter<Category>() {
                    @Override
                    boolean test(Category item, String filterText) {
                        return CategoryService.INSTANCE.categoryMatchesFilter(item, filterText)
                    }
                }, CategoryService.INSTANCE.findAll())

                // bind the combo box to the Review::category field so that changes done by the user are stored.
                bind(binder).bind("category")
            }
            datePicker("Choose the date") {
                max = LocalDate.now()
                min = LocalDate.of(1, 1, 1)
                value = LocalDate.now()
                bind(binder).bind("date")
            }
            comboBox(String, "Mark a score") {
                setAllowCustomValue(false)
                setItems(["1", "2", "3", "4", "5"])
                bind(binder).toInt().bind("score")
            }
        }
        return layout
    }
}
