package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.treegrid.TreeGrid
import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider
import com.vaadin.flow.data.selection.SelectionEvent
import com.vaadin.flow.data.selection.SelectionModel
import com.vaadin.flow.function.ValueProvider
import com.vaadin.flow.shared.util.SharedUtil
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * DSL for {@link Grid} and {@link TreeGrid}.
 * @author mavi
 */
@CompileStatic
class GridUtils {
    @NotNull
    static <T> Grid<T> grid(@NotNull HasComponents self,
            @NotNull Class<T> beanType,
                            @Nullable DataProvider<T, ?> dataProvider = null,
                            @DelegatesTo(value = Grid, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def grid = new Grid<T>(beanType, false)
        if (dataProvider != null) {
            grid.dataProvider = dataProvider
        }
        init(self, grid, block)
    }

    @NotNull
    static <T> TreeGrid<T> treeGrid(@NotNull HasComponents self,
                                    @NotNull Class<T> beanType,
                                    @Nullable HierarchicalDataProvider<T, ?> dataProvider = null,
                                    @DelegatesTo(value = TreeGrid, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def grid = new TreeGrid<T>(beanType)
        grid.removeAllColumns()
        if (dataProvider != null) {
            grid.dataProvider = dataProvider
        }
        init(self, grid, block)
    }

    /**
     * Refreshes the Grid and re-polls for data.
     */
    static void refresh(@NotNull Grid<?> self) {
        self.getDataProvider().refreshAll()
    }

    static boolean isMultiSelect(@NotNull Grid<?> self) {
        return self.selectionModel instanceof SelectionModel.Multi
    }

    static boolean isSingleSelect(@NotNull Grid<?> self) {
        return self.selectionModel instanceof SelectionModel.Single
    }

    static boolean isSelectionEmpty(@NotNull SelectionEvent<? extends Component, ?> self) {
        return !self.firstSelectedItem.isPresent()
    }

    /**
     * Adds a column for given [property]. The column key is set to the property name, so that you can look up the column
     * using [getColumnBy]. The column is also by default set to sortable
     * unless the [sortable] parameter is set otherwise. The header title is set to the property name, converted from camelCase to Human Friendly.
     * @param converter optionally converts the property value [V] to something else, typically to a String. Use this for formatting of the value.
     * @param block runs given block on the column.
     * @param T the type of the bean stored in the Grid
     * @param V the value that the column will display, deduced from the type of the [property].
     * @return the newly created column
     */
    @NotNull
    static <T> Grid.Column<T> addColumnForProperty(
            @NotNull Grid<T> self,
            @NotNull String propertyName,
            boolean sortable = true,
            @NotNull Closure converter = { it },
            @DelegatesTo(value = Grid.Column, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def getter = VaadinUtils.getGetter(self.getBeanType(), propertyName)
        def column = self.addColumn(new ValueProvider<T, Object>() {
            @Override
            Object apply(T t) {
                return converter(getter.invoke(t))
            }
        })
        column.with {
            key = propertyName
            if (sortable) setSortProperty(propertyName)
            setHeader(SharedUtil.camelCaseToHumanFriendly(propertyName))
        }
        block.delegate = column
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
        column
    }
}
