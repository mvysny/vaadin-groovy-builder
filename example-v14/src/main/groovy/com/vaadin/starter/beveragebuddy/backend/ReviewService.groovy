package com.vaadin.starter.beveragebuddy.backend

import groovy.transform.CompileStatic

import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

/**
 * @author mavi
 */
@CompileStatic
class ReviewService {
    private final ConcurrentMap<Long, Review> reviews = new ConcurrentHashMap<Long, Review>()
    private final AtomicLong nextId = new AtomicLong(0)
    private ReviewService() {
        def r = new Random()
        def reviewCount = 20 + r.nextInt(30)
        def beverages = StaticData.BEVERAGES.entrySet().toList()

        for (i in 0..reviewCount) {
            def review = new Review()
            def beverage = beverages[r.nextInt(StaticData.BEVERAGES.size())]
            def category = CategoryService.INSTANCE.findCategoryOrThrow(beverage.value)
            review.name = beverage.key
            def testDay = LocalDate.of(1930 + r.nextInt(88),
                    1 + r.nextInt(12), 1 + r.nextInt(28))
            review.date = testDay
            review.score = 1 + r.nextInt(5)
            review.category = category
            review.count = 1 + r.nextInt(15)
            saveReview(review)
        }
    }

    /**
     * Fetches the reviews matching the given filter text.
     *
     * The matching is case insensitive. When passed an empty filter text,
     * the method returns all categories. The returned list is ordered
     * by name.
     *
     * @param filter    the filter text
     * @return          the list of matching reviews
     */
    List<Review> findReviews(String filter) {
        def normalizedFilter = filter.trim().toLowerCase()
        return reviews.values()
                .findAll { review -> filterTextOf(review).any { it.containsIgnoreCase(normalizedFilter) } }
                .toSorted { it.id }
    }

    static private List<String> filterTextOf(Review review) {
        def dateConverter = new LocalDateToStringConverter()
        return [review.name,
                review.category?.name,
                review.score.toString(),
                review.count.toString(),
                dateConverter.encode(review.date)].toSet().toList() - [null]
    }

    /**
     * Deletes the given review from the review store.
     * @param review    the review to delete
     * @return  true if the operation was successful, otherwise false
     */
    boolean deleteReview(Review review) {
        reviews.remove(review.id) != null
    }

    /**
     * Persists the given review into the review store.
     *
     * If the review is already persistent, the saved review will get updated
     * with the field values of the given review object.
     * If the review is new (i.e. its id is null), it will get a new unique id
     * before being saved.
     *
     * @param dto   the review to save
     */
    void saveReview(Review dto) {
        Review entity = dto.id == null ? null : reviews[dto.id]
        def category = dto.category

        // The case when the category is new (not persisted yet, thus
        // has null id) is not handled here, because it can't currently
        // occur via the UI.
        // Note that Category.UNDEFINED also gets mapped to null.
        category = category == null ? null : CategoryService.INSTANCE.findCategoryById(category.id)
        if (entity == null) {
            // Make a copy to keep entities and DTOs separated
            entity = dto.copy()
            if (dto.id == null) {
                entity.id = nextId.incrementAndGet()
            }
            reviews.put(entity.id, entity)
        } else {
            entity.score = dto.score
            entity.name = dto.name
            entity.date = dto.date
            entity.count = dto.count
        }
        entity.category = category
    }

    Review get(long id) {
        Objects.requireNonNull(reviews[id])
    }

    /**
     * Computes the total sum of [count] for all reviews belonging to given [categoryId].
     * @return the total sum, 0 or greater.
     */
    int getTotalCountForReviewsInCategory(long categoryId) {
        reviews.values().findAll { it.category?.id == categoryId } .sum { Review it -> it.count } as int
    }

    def deleteAll() {
        reviews.clear()
    }
}
