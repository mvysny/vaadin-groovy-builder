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
    implementation(project(":example-components"))
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")
    implementation("org.slf4j:slf4j-simple:${properties["slf4j_version"]}")

    // testing
    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v10-groovy:${properties["kaributesting_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${properties["junit_version"]}")
}
