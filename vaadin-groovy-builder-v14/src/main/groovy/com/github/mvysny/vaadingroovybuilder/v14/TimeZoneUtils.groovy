package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.component.page.ExtendedClientDetails
import com.vaadin.flow.server.VaadinSession
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.time.ZoneId

/**
 * @author mavi
 */
@CompileStatic
class TimeZoneUtils {
    /**
     * You need to populate this field first, by using {@link com.vaadin.flow.component.page.Page#retrieveExtendedClientDetails(com.vaadin.flow.component.page.Page.ExtendedClientDetailsReceiver)}
     */
    static void setExtendedClientDetails(@Nullable ExtendedClientDetails value) {
        VaadinSession.getCurrent().setAttribute(ExtendedClientDetails, value)
    }

    @Nullable
    static ExtendedClientDetails getExtendedClientDetails() {
        return VaadinSession.getCurrent().getAttribute(ExtendedClientDetails)
    }

    /**
     * The time zone as reported by the browser. You need to populate the [extendedClientDetails] first, otherwise the
     * UTC Time zone is going to be returned!
     */
    @NotNull
    static ZoneId getBrowserTimeZone() {
        def clientDetails = getExtendedClientDetails()
        if (clientDetails != null) {
            return TimeZoneExtensionMethods.getTimeZone(clientDetails)
        }
        return ZoneId.of("UTC")
    }
}
