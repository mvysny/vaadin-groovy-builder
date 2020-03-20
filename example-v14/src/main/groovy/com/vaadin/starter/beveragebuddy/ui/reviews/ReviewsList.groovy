package com.vaadin.starter.beveragebuddy.ui.reviews

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.function.SerializableConsumer
import com.vaadin.flow.function.SerializableFunction
import com.vaadin.flow.function.SerializableRunnable
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.starter.beveragebuddy.backend.Review
import com.vaadin.starter.beveragebuddy.backend.ReviewService
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
class ReviewsList extends VerticalLayout {

    private Toolbar toolbar
    private H3 header
    private Grid<Review> reviewsGrid
    private final ReviewEditorDialog editDialog = new ReviewEditorDialog(
            new SerializableConsumer<Review>()
            {
                @Override
                void accept(Review review) {
                    save(review)
                }
            },
            new SerializableConsumer<Review>() {
                @Override
                void accept(Review review) {
                    delete(review)
                }
            })

    ReviewsList() {
        setPadding(false); setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH)
        toolbar = new Toolbar("New review")
        add(toolbar)
        toolbar.with {
            onSearch = new SerializableConsumer<String>() {
                @Override
                void accept(String s) {
                    updateList()
                }
            }
            onCreate = new SerializableRunnable() {
                @Override
                void run() {
                    editDialog.createNew()
                }
            }
        }
        header = h3 {
            setId("header")
        }
        reviewsGrid = grid(Review) {
            setExpand(true)
            addClassName("reviews")
            addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_NO_BORDER)
            addColumn(new ComponentRenderer<ReviewItem, Review>(new SerializableFunction<Review, ReviewItem>() {
                @Override
                ReviewItem apply(Review review) {
                    def item = new ReviewItem(review)
                    item.onEdit = new SerializableRunnable() {
                        @Override
                        void run() {
                            editDialog.edit(ReviewService.INSTANCE.get(review.id))
                        }
                    }
                    item
                }
            }))
        }

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
        List<Review> reviews = ReviewService.INSTANCE.findReviews(toolbar.searchText)
        if (toolbar.searchText.isBlank()) {
            header.text = "Reviews"
            header.add(new Span("${reviews.size()} in total"))
        } else {
            header.text = "Search for “${toolbar.searchText}”"
            header.add(new Span("${reviews.size()} results"))
        }
        reviewsGrid.setItems(reviews)
    }
}
