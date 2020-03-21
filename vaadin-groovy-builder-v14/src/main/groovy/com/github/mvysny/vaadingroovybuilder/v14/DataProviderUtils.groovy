package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.data.provider.Query
import com.vaadin.flow.data.provider.QuerySortOrder
import com.vaadin.flow.data.provider.SortDirection
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

/**
 * @author mavi
 */
@CompileStatic
class DataProviderUtils {
    @NotNull
    static <T, F> List<T> getAll(@NotNull DataProvider<T, F> self) {
        VaadinUtils.toList(self.fetch(new Query<T, F>()))
    }

    @NotNull
    static QuerySortOrder getAsc(@NotNull String self) {
        new QuerySortOrder(self, SortDirection.ASCENDING)
    }

    @NotNull
    static QuerySortOrder getDesc(@NotNull String self) {
        new QuerySortOrder(self, SortDirection.DESCENDING)
    }
}
