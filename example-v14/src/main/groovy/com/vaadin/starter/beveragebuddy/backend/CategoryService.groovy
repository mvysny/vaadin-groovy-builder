package com.vaadin.starter.beveragebuddy.backend

import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.require

/**
 * @author mavi
 */
@CompileStatic
class CategoryService {
    private CategoryService() {
        StaticData.BEVERAGES.values().toSet().forEach { String name -> saveCategory(new Category(null, name)) }
    }

    public static final CategoryService INSTANCE = new CategoryService()

    /**
     * Simple backend service to store and retrieve [Category] instances.
     */
    private final ConcurrentMap<Long, Category> categories = new ConcurrentHashMap<Long, Category>()
    private final AtomicLong nextId = new AtomicLong(0L)

    /**
     * Fetches the categories whose name matches the given filter text.
     *
     * The matching is case insensitive. When passed an empty filter text,
     * the method returns all categories. The returned list is ordered
     * by name.
     *
     * @param filter the filter text
     * @return the list of matching categories
     */
    List<Category> findCategories(String filter) {
        def normalizedFilter = filter.trim()
        // Make a copy of each matching item to keep entities and DTOs separated
        return categories.values()
                .findAll { categoryMatchesFilter(it, normalizedFilter) }
                .collect { it.copy() }
                .toSorted { it.name }
    }

    static boolean categoryMatchesFilter(Category category, String filter) {
        String normalizedFilter = filter.trim().toLowerCase()
        return category.name.toLowerCase().contains(normalizedFilter)
    }

    /**
     * Searches for the exact category whose name matches the given [filter] text.
     *
     * The matching is substring-based and case insensitive.
     * @return the category or null.
     * @throws IllegalStateException    if the result is ambiguous
     */
    Category findCategoryByName(String filter) {
        def categoriesMatching = findCategories(filter)
        require(categoriesMatching.size() <= 1) { "Category $filter is ambiguous" }
        return categoriesMatching.find { true }
    }

    /**
     * Fetches the exact category whose name matches the given [filter] text.
     *
     * Behaves like [findCategoryByName], except that returns
     * a [Category] instead of an [Optional]. If the category
     * can't be identified, an exception is thrown.
     *
     * @return the category, if found
     * @throws IllegalStateException    if not exactly one category matches the given name
     */
    Category findCategoryOrThrow(String filter) {
        Objects.requireNonNull(findCategoryByName(filter))
    }

    /**
     * Searches for the exact category with the given id.
     * @param id the category id
     * @return the category
     */
    Category findCategoryById(long id) { categories[id] }

    Category getById(long id) {
        Objects.requireNonNull(categories[id])
    }

    /**
     * Deletes the given category from the category store.
     * @param category the category to delete
     * @return true if the operation was successful, otherwise false
     */
    boolean deleteCategory(Category category) {
        Objects.requireNonNull(category.id)
        return categories.remove(category.id) != null
    }

    /**
     * Persists the given category into the category store.
     *
     * If the category is already persistent, the saved category will get updated
     * with the name of the given category object.
     * If the category is new (i.e. its id is null), it will get a new unique id
     * before being saved.
     *
     * @param dto the category to save
     */
    void saveCategory(Category dto) {
        if (dto.id == null) {
            // create
            // Make a copy to keep entities and DTOs separated
            def entity = dto.copy()
            entity.id = nextId.incrementAndGet()
            dto.id = entity.id
            categories[entity.id] = entity
            return
        }
        def entity = categories[dto.id]

        if (entity == null) {
            // Make a copy to keep entities and DTOs separated
            entity = dto.copy()
            categories[entity.id] = entity
        } else {
            entity.name = dto.name
        }
    }

    List<Category> findAll() {
        findCategories("")
    }

    void deleteAll() {
        categories.clear()
    }
}