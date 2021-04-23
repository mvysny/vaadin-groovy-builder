package com.vaadin.starter.beveragebuddy.ui.reviews

import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.function.SerializableRunnable
import com.vaadin.starter.beveragebuddy.backend.Review
import groovy.transform.CompileStatic

/**
 * Shows a single row stripe with information about a single [ReviewWithCategory].
 */
@CompileStatic
class ReviewItem extends Div {
    final Review review

    /**
     * Fired when this item is to be edited (the "Edit" button is pressed by the User).
     */
    SerializableRunnable onEdit = null

    ReviewItem(Review review) {
        this.review = review
        addClassName("review")
        div {
            addClassName("review__rating")
            p(review.score.toString()) {
                className = "review__score"
                element.setAttribute("data-score", review.score.toString())
            }
            p(review.count.toString()) {
                className = "review__count"
                span("times tasted") {}
            }
        }
        div {
            addClassName("review__details")
            h4(review.name) {
                addClassName("review__name")
            }
            p {
                className = "review__category"
                if (review.category != null) {
                    element.themeList.add("badge small")
                    element.style.set("--category", review.category.toString())
                    text = review.category.name
                } else {
                    element.style.set("--category", "-1")
                    text = "Undefined"
                }
            }
        }
        div {
            className = "review__date"
            h5("Last tasted") {}
            p(review.date.toString()) {}
        }
        button("Edit", VaadinIcon.EDIT.create()) {
            className = "review__edit"
            addThemeVariants(ButtonVariant.LUMO_TERTIARY)
            addClickListener { onEdit.run() }
        }
    }
}
