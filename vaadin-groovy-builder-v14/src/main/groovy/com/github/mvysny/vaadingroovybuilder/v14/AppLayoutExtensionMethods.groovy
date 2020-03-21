package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.dom.Element
import com.vaadin.flow.router.RouterLink
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

import static com.github.mvysny.vaadingroovybuilder.v14.Utils.check
import static com.github.mvysny.vaadingroovybuilder.v14.Utils.require
import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * @author mavi
 */
@CompileStatic
class AppLayoutExtensionMethods {
    /**
     * Creates an [App Layout](https://vaadin.com/components/vaadin-app-layout). Example:
     *
     * <pre>
     * class MainLayout extends AppLayout {
     *   init {
     *     isDrawerOpened = false
     *     navbar {
     *       drawerToggle()
     *       h3("Beverage Buddy")
     *     }
     *     drawer {
     *       div {
     *         routerLink(VaadinIcon.LIST, "Reviews", ReviewsList::class)
     *       }
     *       div {
     *         routerLink(VaadinIcon.ARCHIVES, "Categories", CategoriesList::class)
     *       }
     *     }
     *   }
     * }
     * </pre>
     */
    @NotNull
    static AppLayout appLayout(@NotNull HasComponents self,
                               @DelegatesTo(value = AppLayout, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new AppLayout(), block)
    }

    /**
     * Allows you to populate the [AppLayout.addToNavbar] in a DSL fashion:
     * <pre>
     * appLayout {
     *   navbar { h3("My App") }
     * }
     * </pre>
     */
    static void navbar(@NotNull AppLayout self,
                          @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def dummy = new HasComponents() {
            @Override
            Element getElement() {
                throw new UnsupportedOperationException("Not expected to be called")
            }

            @Override
            void add(Component... components) {
                self.addToNavbar(components)
            }
        }
        block.delegate = dummy
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
    }

    /**
     * Populates the AppLayout drawer slot.
     */
    static void drawer(@NotNull AppLayout self,
                       @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def dummy = new HasComponents() {
            @Override
            Element getElement() {
                throw new UnsupportedOperationException("Not expected to be called")
            }

            @Override
            void add(Component... components) {
                self.addToDrawer(components)
            }
        }
        block.delegate = dummy
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
    }

    /**
     * Allows you to set the [AppLayout.setContent] in a DSL fashion.
     *
     * The [AppLayout] implements [RouterLayout] and is able to automatically populate itself with the view's contents.
     * If you wish to take advantage of this feature, just make your main layout extend the [AppLayout] class.
     */
    static void content(@NotNull AppLayout self,
                       @DelegatesTo(value = HasComponents, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def dummy = new HasComponents() {
            @Override
            Element getElement() {
                throw new UnsupportedOperationException("Not expected to be called")
            }

            @Override
            void add(Component... components) {
                require(components.size() < 2) {
                    "Too many components to add - AppLayout content can only host one! ${components.toList()}"
                }
                def component = VaadinUtils.firstOrNull(components)
                if (component == null) {
                    return
                }
                check(self.content == null) { "The content has already been initialized!" }
                self.content = component
            }
        }
        block.delegate = dummy
        block.resolveStrategy = Closure.DELEGATE_FIRST
        block()
    }

    /**
     * Adds the [DrawerToggle] to your [AppLayout]:
     * <pre>
     * appLayout {
     *   navbar {
     *     drawerToggle{}
     *     h3("My App")
     *   }
     * }
     * </pre>
     */
    @NotNull
    static DrawerToggle drawerToggle(@NotNull HasComponents self,
                                     @DelegatesTo(value = DrawerToggle, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new DrawerToggle(), block)
    }
}
