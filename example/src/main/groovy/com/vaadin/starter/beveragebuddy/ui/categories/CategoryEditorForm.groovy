package com.vaadin.starter.beveragebuddy.ui.categories

import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.validator.StringLengthValidator
import com.vaadin.starter.beveragebuddy.backend.Category
import com.vaadin.starter.beveragebuddy.backend.CategoryService
import com.vaadin.starter.beveragebuddy.ui.EditorForm
import groovy.transform.CompileStatic

/**
 * A form for editing [Category] objects.
 */
@CompileStatic
class CategoryEditorForm extends FormLayout implements EditorForm<Category> {
    Category category

    CategoryEditorForm(Category category) {
        this.category = category
        textField("Category Name") {
            bind(binder)
                    .trimmingConverter()
                    .withValidator(new StringLengthValidator(
                            "Category name must contain at least 3 printable characters",
                            3, null))
                    .withValidator({ name -> isNameUnique(name) }, "Category name must be unique")
                    .bind("name")
        }
    }

    private boolean isEditing() { category.id != null }

    @Override
    String getItemType() {
        return "Category"
    }

    private final Binder<Category> binder = new BeanValidationBinder<Category>(Category)

    @Override
    Binder<Category> getBinder() {
        return binder
    }

    @Override
    FormLayout getComponent() {
        this
    }

    private boolean isNameUnique(String name) {
        if (name == null || name.isAllWhitespace()) return true
        if (category.name == name && isEditing()) return true
        return CategoryService.INSTANCE.findCategoryByName(name) == null
    }
}
