// The Beverage Buddy sample project ported to Kotlin.
// Original project: https://github.com/vaadin/beverage-starter-flow

plugins {
    id("org.gretty")
    war
    id("com.vaadin")
}

gretty {
    contextPath = "/"
    servletContainer = "jetty9.4"
}

dependencies {
    implementation(project(":vaadin-groovy-builder-v14"))
    // Vaadin 14
    implementation("com.vaadin:vaadin-core:${properties["vaadin10_version"]}") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")
    implementation("org.slf4j:slf4j-simple:${properties["slf4j_version"]}")

    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v10:${properties["kaributesting_version"]}")
}
