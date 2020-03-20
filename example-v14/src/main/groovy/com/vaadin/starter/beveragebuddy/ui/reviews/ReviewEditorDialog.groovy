package com.vaadin.starter.beveragebuddy.ui.reviews

import com.vaadin.flow.function.SerializableConsumer
import com.vaadin.starter.beveragebuddy.backend.Review
import com.vaadin.starter.beveragebuddy.ui.ConfirmationDialog
import com.vaadin.starter.beveragebuddy.ui.EditorDialogFrame

/**
 * A dialog for editing [Review] objects.
 * @property onSaveItem Callback to save the edited item
 * @property onDeleteItem Callback to delete the edited item
 */
class ReviewEditorDialog {
    private final SerializableConsumer<Review> onSaveItem
    private final SerializableConsumer<Review> onDeleteItem

    ReviewEditorDialog(SerializableConsumer<Review> onSaveItem, SerializableConsumer<Review> onDeleteItem) {
        this.onSaveItem = onSaveItem
        this.onDeleteItem = onDeleteItem
    }

    void createNew() {
        edit(new Review())
    }

    private void maybeDelete(EditorDialogFrame<Review> frame, Review item) {
        new ConfirmationDialog().open("""Delete beverage "${item.name}"?""", "", "",
                "Delete", true) {
            frame.close()
            onDeleteItem(item)
        }
    }

    void edit(Review review) {
        EditorDialogFrame<Review> frame = new EditorDialogFrame(new ReviewEditorForm())
        frame.onSaveItem = onSaveItem
        frame.onDeleteItem = new SerializableConsumer<Review>(){
            @Override
            void accept(Review item) {
                maybeDelete(frame, item)
            }
        }
        frame.open(review, review.id == null)
    }
}
