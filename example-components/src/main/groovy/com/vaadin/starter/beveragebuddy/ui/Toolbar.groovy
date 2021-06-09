package com.vaadin.starter.beveragebuddy.ui

import com.github.mvysny.vaadingroovybuilder.v14.GComposite
import com.vaadin.flow.component.HasSize
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.function.SerializableConsumer
import com.vaadin.flow.function.SerializableRunnable
import groovy.transform.CompileStatic

/**
 * A toolbar with a search box and a "Create New" button. Don't forget to provide proper listeners
 * for {@link #onSearch} and {@link #onCreate}.
 * @param createCaption the caption of the "Create New" button.
 */
@CompileStatic
class Toolbar extends GComposite implements HasSize {
    /**
     * Fired when the text in the search text field changes.
     */
    SerializableConsumer<String> onSearch
    /**
     * Fired when the "Create new" button is clicked.
     */
    SerializableRunnable onCreate
    private TextField searchField
    private Button createButton

    /**
     * Current search text. Never null, trimmed, may be blank.
     */
    String getSearchText() { searchField.getValue().trim() }

    private final root = ui {
        div {
            addClassName("view-toolbar")
            searchField = textField {
                prefixComponent = new Icon(VaadinIcon.SEARCH)
                addClassName("view-toolbar__search-field")
                placeholder = "Search"
                addValueChangeListener { onSearch.accept(searchText) }
                valueChangeMode = ValueChangeMode.EAGER
            }
            createButton = button("", new Icon(VaadinIcon.PLUS)) {
                setPrimary()
                addClassName("view-toolbar__button")
                addClickListener { onCreate.run() }
            }
        }
    }

    Toolbar(String createCaption) {
        createButton.setText(createCaption)
    }
}
