package com.vaadin.starter.beveragebuddy.ui.reviews

import com.github.mvysny.vaadingroovybuilder.v14.GComposite
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.function.SerializableFunction
import com.vaadin.flow.function.SerializableRunnable
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.starter.beveragebuddy.backend.Review
import com.vaadin.starter.beveragebuddy.backend.ReviewService
import com.vaadin.starter.beveragebuddy.ui.ComponentRenderers
import com.vaadin.starter.beveragebuddy.ui.MainLayout
import com.vaadin.starter.beveragebuddy.ui.Toolbar
import groovy.transform.CompileStatic

/**
 * Displays the list of available categories, with a search filter as well as
 * buttons to add a new category or edit existing ones.
 */
@Route(value = "", layout = MainLayout)
@PageTitle("Review List")
@CompileStatic
class ReviewsList extends GComposite {

    private Toolbar t
    private H3 header
    private Grid<Review> reviewsGrid
    private final ReviewEditorDialog editDialog = new ReviewEditorDialog(
            { Review review -> save(review) },
            { Review review -> delete(review) })

    private final root = ui {
        verticalLayout(false) {
            content { align(stretch, top) }
            t = toolbar("New review") {
                onSearch = { updateList() }
                onCreate = { editDialog.createNew() }
            }
            header = h3 {
                setId("header")
            }
            reviewsGrid = grid(Review) {
                setExpand(true)
                addClassName("reviews")
                addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_NO_BORDER)
                addColumn(ComponentRenderers.create { Review review ->
                    def item = new ReviewItem(review)
                    item.onEdit = { editDialog.edit(ReviewService.INSTANCE.get(review.id)) }
                    item
                })
            }
        }
    }

    ReviewsList() {
        updateList()
    }

    private void save(Review review) {
        def creating = review.id == null
        ReviewService.INSTANCE.saveReview(review)
        def op = creating ? "added" : "saved"
        updateList()
        Notification.show("Beverage successfully ${op}.", 3000, Notification.Position.BOTTOM_START)
    }

    private void delete(Review review) {
        ReviewService.INSTANCE.deleteReview(review)
        updateList()
        Notification.show("Beverage successfully deleted.", 3000, Notification.Position.BOTTOM_START)
    }

    private void updateList() {
        List<Review> reviews = ReviewService.INSTANCE.findReviews(t.searchText)
        if (t.searchText.isBlank()) {
            header.text = "Reviews"
            header.add(new Span("${reviews.size()} in total"))
        } else {
            header.text = "Search for “${t.searchText}”"
            header.add(new Span("${reviews.size()} results"))
        }
        reviewsGrid.setItems(reviews)
    }
}
