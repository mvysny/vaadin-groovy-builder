package com.github.mvysny.vaadingroovybuilder.v14
import com.vaadin.flow.component.page.Page
import com.vaadin.flow.component.page.ExtendedClientDetails
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * @author mavi
 */
@CompileStatic
class TimeZoneExtensionMethods {
    /**
     * The time zone as reported by the browser. Use {@link Page#retrieveExtendedClientDetails(com.vaadin.flow.component.page.Page.ExtendedClientDetailsReceiver)}
     * to get {@link ExtendedClientDetails}.
     */
    @NotNull
    static ZoneId getTimeZone(ExtendedClientDetails self) {
        if (self.timeZoneId != null && !self.timeZoneId.isAllWhitespace()) {
            // take into account zone ID. This is important for historical dates, to properly compute date with daylight savings.
            return ZoneId.of(self.timeZoneId)
        }
        // fallback to time zone offset
        return ZoneOffset.ofTotalSeconds(self.timezoneOffset.intdiv(1000))
    }


    /**
     * Returns the current date and time at browser's current time zone.
     */
    @NotNull
    static LocalDateTime getCurrentDateTime(ExtendedClientDetails self) {
        LocalDateTime.now(ZoneOffset.ofTotalSeconds(self.timezoneOffset.intdiv(1000)))
    }
}
