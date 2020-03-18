package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.*
import com.vaadin.flow.server.VaadinService
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static com.github.mvysny.vaadingroovybuilder.v14.VaadinDsl.init

/**
 * @author mavi
 */
@CompileStatic
class RouterUtils {
    @NotNull
    static RouterLink routerLink(
            HasComponents self,
            @Nullable VaadinIcon icon = null,
            @Nullable String text = null,
            @NotNull Class<? extends Component> viewType,
            @DelegatesTo(value = RouterLink, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def link = new RouterLink(null, viewType)
        if (icon != null) IconUtils.icon(link, icon)
        if (text != null) VaadinUtils.text(link, text)
        init(self, link, block)
        return link
    }

    @NotNull
    static <T, C extends Component & HasUrlParameter<T>> RouterLink routerLink(
            HasComponents self,
            @Nullable VaadinIcon icon = null,
            @Nullable String text = null,
            @NotNull Class<? extends C> viewType,
            @Nullable T parameter,
            @DelegatesTo(value = RouterLink, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        def link = new RouterLink(null, viewType as Class, parameter as Object)
        if (icon != null) IconUtils.icon(link, icon)
        if (text != null) VaadinUtils.text(link, text)
        init(self, link, block)
        return link
    }

    @NotNull
    static RouterLink routerLink(
            HasComponents self,
            @Nullable VaadinIcon icon = null,
            @Nullable String text = null,
            @DelegatesTo(value = RouterLink, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        // a RouterLink for which the navigation target is not yet known and is set lazily, perhaps in HasUrlParameter.setParameter()
        RouterLink link = new RouterLink()
        if (icon != null) IconUtils.icon(link, icon)
        if (text != null) VaadinUtils.text(link, text)
        init(self, link, block)
        return link
    }

    /**
     * Returns [UI.getRouter]/[VaadinService.router], whichever returns a non-null value.
     */
    private static Router getRouter() {
        Router router = UI.getCurrent()?.router
        if (router == null) {
            router = VaadinService.getCurrent().router
        }
        if (router == null) {
            throw new IllegalStateException("Implicit router instance is not available. Use overloaded method with explicit router parameter.")
        }
        return router
    }

    /**
     * Set the [navigationTarget] for this link.
     */
    static void setRoute(RouterLink self, @NotNull Class<? extends Component> navigationTarget) {
        self.setRoute(getRouter(), navigationTarget)
    }
    /**
     * Set the [navigationTarget] for this link.
     * @param parameter url parameter for navigation target
     * @param T url parameter type
     * @param C navigation target type
     */
    static <T, C extends Component & HasUrlParameter<T>> void setRoute(
            RouterLink self,
            @NotNull Class<? extends C> navigationTarget,
            @Nullable T parameter) {
        self.setRoute(getRouter(), navigationTarget, parameter)
    }

    /**
     * Returns the navigated-to view class.
     */
    @NotNull
    static Class<? extends Component> getViewClass(AfterNavigationEvent self) {
        (self.activeChain.first() as Component).getClass()
    }

    /**
     * Finds a view mapped to this location.
     * @param router router to use, defaults to [UI.getRouter]/[VaadinService.router].
     */
    @Nullable
    static Class<? extends Component> getViewClass(Location self, @NotNull Router router = getRouter()) {
        def params = new HashMap<String, String[]>()
        for (Map.Entry<String, List<String>> entry : self.queryParameters.parameters.entrySet()) {
            params[entry.key] = entry.value.toArray(new String[0]) as String[]
        }
        Optional<NavigationState> navigationTarget =
                router.resolveNavigationTarget("/${self.path}", params)
        return navigationTarget.orElse(null)?.navigationTarget
    }
}
