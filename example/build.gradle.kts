// The Beverage Buddy sample project ported to Kotlin.
// Original project: https://github.com/vaadin/beverage-starter-flow

plugins {
    java
    id("com.vaadin")
    application
}

dependencies {
    implementation(project(":example-components"))
    implementation("org.slf4j:slf4j-simple:${properties["slf4j_version"]}")
    implementation("com.github.mvysny.vaadin-boot:vaadin-boot:10.5")

    // testing
    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v10-groovy:${properties["kaributesting_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${properties["junit_version"]}")
}

application {
    mainClass = "com.vaadin.starter.beveragebuddy.Main"
}
