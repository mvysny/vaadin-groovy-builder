package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.FooterRow
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.HeaderRow
import com.vaadin.flow.component.treegrid.TreeGrid
import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.data.renderer.Renderer
import com.vaadin.flow.data.selection.SelectionEvent
import com.vaadin.flow.data.selection.SelectionModel
import com.vaadin.flow.function.ValueProvider
import com.vaadin.flow.shared.util.SharedUtil
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.lang.reflect.Method

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.require
import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * DSL for {@link Grid} and {@link TreeGrid}.
 * @author mavi
 */
@CompileStatic
class GridUtils {
    @NotNull
    static <ITEM> Grid<ITEM> grid(@NotNull HasComponents self,
                            @NotNull Class<ITEM> beanType,
                            @Nullable DataProvider<ITEM, ?> dataProvider = null,
                            @DelegatesTo(type = "com.vaadin.flow.component.grid.Grid<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def grid = new Grid<ITEM>(beanType, false)
        if (dataProvider != null) {
            grid.dataProvider = dataProvider
        }
        init(self, grid, block)
    }

    @NotNull
    static <ITEM> TreeGrid<ITEM> treeGrid(@NotNull HasComponents self,
                                    @NotNull Class<ITEM> beanType,
                                    @Nullable HierarchicalDataProvider<ITEM, ?> dataProvider = null,
                                    @DelegatesTo(type = "com.vaadin.flow.component.treegrid.TreeGrid<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def grid = new TreeGrid<ITEM>(beanType)
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
                return converter(getter.get(t))
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

    @NotNull
    private static final Class<?> abstractCellClass = Class.forName("com.vaadin.flow.component.grid.AbstractRow\$AbstractCell")
    @NotNull
    private static final Class<?> abstractColumnClass = Class.forName("com.vaadin.flow.component.grid.AbstractColumn")

    /**
     * Returns `com.vaadin.flow.component.grid.AbstractColumn`
     */
    @NotNull
    private static Object getColumn(@NotNull HeaderRow.HeaderCell cell) {
        Method getColumn = abstractCellClass.getDeclaredMethod("getColumn")
        getColumn.setAccessible(true)
        return getColumn.invoke(cell)
    }

    /**
     * Returns `com.vaadin.flow.component.grid.AbstractColumn`
     */
    @NotNull
    private static Object getColumn(@NotNull FooterRow.FooterCell cell) {
        Method getColumn = abstractCellClass.getDeclaredMethod("getColumn")
        getColumn.setAccessible(true)
        return getColumn.invoke(cell)
    }

    /**
     * Retrieves the cell for given [key].
     * @return the corresponding cell
     * @throws IllegalArgumentException if no such column exists.
     */
    @NotNull
    static HeaderRow.HeaderCell getCell(@NotNull HeaderRow self, @NotNull String key) {
        HeaderRow.HeaderCell cell = self.cells.find { getColumnKey(getColumn(it as HeaderRow.HeaderCell)) == key }
        require(cell != null) { "This grid has no column with key ${key}: ${self.cells}" }
        return cell
    }

    private static String getColumnKey(Object column) {
        abstractColumnClass.cast(column)
        Method method = abstractColumnClass.getDeclaredMethod("getBottomLevelColumn")
        method.setAccessible(true)
        Grid.Column<?> gridColumn = method.invoke(column) as Grid.Column<?>
        return gridColumn.key
    }

    /**
     * Retrieves the cell for given [key].
     * @return the corresponding cell
     * @throws IllegalArgumentException if no such column exists.
     */
    @NotNull
    static FooterRow.FooterCell getCell(@NotNull FooterRow self, @NotNull String key) {
        FooterRow.FooterCell cell = self.cells.find { getColumnKey(getColumn(it as FooterRow.FooterCell)) == key }
        require(cell != null) { "This grid has no column with key ${key}: ${self.cells}" }
        return cell
    }

    @Nullable
    static Renderer<?> getRenderer(@NotNull HeaderRow.HeaderCell self) {
        Method method = abstractColumnClass.getDeclaredMethod("getHeaderRenderer")
        method.setAccessible(true)
        def renderer = method.invoke(getColumn(self))
        return renderer as Renderer<?>
    }

    @Nullable
    static Renderer<?> getRenderer(@NotNull FooterRow.FooterCell self) {
        Method method = abstractColumnClass.getDeclaredMethod("getFooterRenderer")
        method.setAccessible(true)
        def renderer = method.invoke(getColumn(self))
        return renderer as Renderer<?>
    }

    @Nullable
    static Component getComponent(@NotNull FooterRow.FooterCell self) {
        def renderer = getRenderer(self)
        if (!(renderer instanceof ComponentRenderer<? extends Component, ?>)) {
            return null
        }
        return (renderer as ComponentRenderer<? extends Component, ?>).createComponent(null) as Component
    }

    @Nullable
    private static final Class<?> gridSorterComponentRendererClass
    static {
        try {
            gridSorterComponentRendererClass = Class.forName("com.vaadin.flow.component.grid.GridSorterComponentRenderer")
        } catch (ClassNotFoundException ex) {
            // Vaadin 18.0.3+ and Vaadin 14.5.0+ doesn't contain this class anymore and simply uses ComponentRenderer
            gridSorterComponentRendererClass = null
        }
    }

    @Nullable
    static Component getComponent(@NotNull HeaderRow.HeaderCell self) {
        def renderer = getRenderer(self)
        if (gridSorterComponentRendererClass != null && gridSorterComponentRendererClass.isInstance(renderer)) {
            def componentField = gridSorterComponentRendererClass.getDeclaredField("component")
            componentField.setAccessible(true)
            return componentField.get(renderer) as Component
        }
        if (renderer instanceof ComponentRenderer) {
            return ((ComponentRenderer) renderer).createComponent(null)
        }
        return null
    }
}
