package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.ironlist.IronList
import com.vaadin.flow.data.provider.DataProvider
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * @author Martin Vysny <mavi@vaadin.com>
 */
@CompileStatic
class IronListUtils {
    @NotNull
    static <ITEM> IronList<ITEM> ironList(@NotNull HasComponents self,
                                  @Nullable DataProvider<ITEM, ?> dataProvider = null,
                                  @DelegatesTo(type = "com.vaadin.flow.component.ironlist.IronList<ITEM>", strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def list = new IronList<ITEM>()
        if (dataProvider != null) {
            list.dataProvider = dataProvider
        }
        init(self, list, block)
    }

    /**
     * Refreshes the Grid and re-polls for data.
     */
    static void refresh(@NotNull IronList<?> self) {
        self.getDataProvider().refreshAll()
    }
}
