package com.vaadin.starter.beveragebuddy.ui

import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.data.binder.Binder

/**
 * The editor form which edits beans of type [T]. The UI is contained in [component] and all fields are referenced
 * via [binder]. Nest this into the [EditorDialogFrame].
 */
interface EditorForm<T extends Serializable> {
    /**
     * The displayable name of the item type [T].
     */
    String getItemType()
    /**
     * All form fields are registered to this binder.
     */
    Binder<T> getBinder()
    /**
     * Contains all form UI fields.
     */
    FormLayout getComponent()
}
