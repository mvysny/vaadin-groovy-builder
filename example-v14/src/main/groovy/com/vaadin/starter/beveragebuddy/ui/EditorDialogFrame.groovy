package com.vaadin.starter.beveragebuddy.ui

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.function.SerializableConsumer
import com.vaadin.flow.shared.Registration
import groovy.transform.CompileStatic

/**
 * A dialog frame for dialogs adding, editing or deleting items.
 *
 * Users are expected to:
 *  * Set [form] with a proper implementation.
 *  * Set [onSaveItem] and [onDeleteItem]
 *  * Call [open] to show the dialog.
 * @param T the type of the item to be added, edited or deleted
 * @property form the form itself
 */
@CompileStatic
class EditorDialogFrame<T extends Serializable> extends Dialog {
    private EditorForm<T> form

    private H2 titleField
    private Button saveButton
    private Button cancelButton
    private Button deleteButton
    private Registration registrationForSave

    /**
     * The item currently being edited.
     */
    private T currentItem
    T getCurrentItem() { currentItem }

    /**
     * Callback to save the edited item. The dialog frame is closed automatically.
     */
    SerializableConsumer<T> onSaveItem

    /**
     * Callback to delete the edited item. Should open confirmation dialog (or delete the item directly if possible).
     * Note that the frame is not closed automatically and must be closed manually.
     */
    SerializableConsumer<T> onDeleteItem

    EditorDialogFrame(EditorForm<T> form) {
        this.form = form
        setCloseOnEsc(true)
        setCloseOnOutsideClick(false)

        titleField = h2{}
        div {
            // form layout wrapper
            addClassName("has-padding")

            add(form.component)
            form.component.setResponsiveSteps([new FormLayout.ResponsiveStep("0", 1),
                                               new FormLayout.ResponsiveStep("50em", 2)])
            form.component.addClassName("no-padding")
        }
        horizontalLayout {
            // button bar
            className = "buttons"
            saveButton = button("Save") {
                setAutofocus(true)
                setPrimary()
            }
            cancelButton = button("Cancel") {
                addClickListener { close() }
            }
            deleteButton = button("Delete") {
                addThemeVariants(ButtonVariant.LUMO_ERROR)
                addClickListener { onDeleteItem.accept(currentItem) }
            }
        }
    }

    /**
     * Opens given item for editing in this dialog.
     *
     * @param item The item to edit; it may be an existing or a newly created instance
     * @param creating if true, the item is being created; if false, the item is being edited.
     */
    void open(T item, boolean creating) {
        currentItem = item
        def operation = creating ? "Add new" : "Edit"
        titleField.text = "$operation ${form.itemType}"
        registrationForSave?.remove()
        registrationForSave = saveButton.addClickListener { saveClicked() }
        form.binder.readBean(currentItem)

        deleteButton.setEnabled(!creating)
        open()
    }

    private void saveClicked() {
        if (form.binder.validate().isOk() && form.binder.writeBeanIfValid(currentItem)) {
            onSaveItem.accept(currentItem)
            close()
        } else {
            Notification.show("Please correct the errors in the form", 2000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR)
        }
    }
}
