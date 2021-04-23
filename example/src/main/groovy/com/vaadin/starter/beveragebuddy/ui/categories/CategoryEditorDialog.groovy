package com.vaadin.starter.beveragebuddy.ui.categories

import com.vaadin.flow.function.SerializableConsumer
import com.vaadin.starter.beveragebuddy.backend.Category
import com.vaadin.starter.beveragebuddy.backend.ReviewService
import com.vaadin.starter.beveragebuddy.ui.ConfirmationDialog
import com.vaadin.starter.beveragebuddy.ui.EditorDialogFrame
import groovy.transform.CompileStatic

/**
 * Opens dialogs for editing [Category] objects.
 * @property onSaveItem Callback to save the edited item
 * @property onDeleteItem Callback to delete the edited item
 */
@CompileStatic
class CategoryEditorDialog {

    private final SerializableConsumer<Category> onSaveItem
    private final SerializableConsumer<Category> onDeleteItem

    CategoryEditorDialog(SerializableConsumer<Category> onSaveItem, SerializableConsumer<Category> onDeleteItem) {
        this.onSaveItem = onSaveItem
        this.onDeleteItem = onDeleteItem
    }

    private void maybeDelete(EditorDialogFrame<Category> frame, Category item) {
        def reviewCount = ReviewService.INSTANCE.getTotalCountForReviewsInCategory(item.id)
        if (reviewCount == 0) {
            frame.close()
            onDeleteItem.accept(item)
        } else {
            def additionalMessage = "Deleting the category will mark the associated reviews as “undefined”. You may link the reviews to other categories on the edit page."
            new ConfirmationDialog().open("Delete Category “${item.name}”?",
                    "There are $reviewCount reviews associated with this category.",
                    additionalMessage, "Delete", true) {
                frame.close()
                onDeleteItem.accept(item)
            }
        }
    }

    void createNew() {
        edit(new Category())
    }

    void edit(Category category) {
        def frame = new EditorDialogFrame(new CategoryEditorForm(category))
        frame.onSaveItem = onSaveItem
        frame.onDeleteItem = new SerializableConsumer<Category>() {
            @Override
            void accept(Category cat) {
                maybeDelete(frame, cat)
            }
        }
        frame.open(category, category.id == null)
    }
}
