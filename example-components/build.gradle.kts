// The Beverage Buddy sample project ported to Kotlin.
// Original project: https://github.com/vaadin/beverage-starter-flow

dependencies {
    api(project(":vaadin-groovy-builder-v14"))
    // Vaadin 14
    api("com.vaadin:vaadin-core:${properties["vaadin_version"]}") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    api("org.slf4j:slf4j-api:${properties["slf4j_version"]}")

    // testing
    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v10-groovy:${properties["kaributesting_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${properties["junit_version"]}")
    testImplementation("org.slf4j:slf4j-simple:${properties["slf4j_version"]}")
}
