package com.vaadin.starter.beveragebuddy.ui

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.Routes
import com.vaadin.starter.beveragebuddy.backend.CategoryService
import com.vaadin.starter.beveragebuddy.backend.ReviewService
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

/**
 * Properly configures the app in the test context, so that the app is properly initialized, and the database is emptied before every test.
 */
@CompileStatic
class AbstractAppTest {
    private static Routes routes
    @BeforeAll
    static void discoverRoutes() {
        routes = new Routes().autoDiscoverViews("com.vaadin.starter")
    }

    // since there is no servlet environment, Flow won't auto-detect the @Routes. We need to auto-discover all @Routes
    // and populate the RouteRegistry properly.
    @BeforeEach
    void setupVaadin() {
        MockVaadin.setup(routes)
    }
    @AfterEach
    void teardownVaadin() {
        MockVaadin.tearDown()
    }

    // it's a good practice to clear up the db before every test, to start every test with a predefined state.
    @AfterEach
    void cleanupDb() { ReviewService.INSTANCE.deleteAll(); CategoryService.INSTANCE.reset() }
}
