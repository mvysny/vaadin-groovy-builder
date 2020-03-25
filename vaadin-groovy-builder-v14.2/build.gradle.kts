dependencies {
    // use Groovy 2.5.0+ otherwise compiling classes with javax.validation annotations will fail with
    // BUG! exception in phase 'class generation' in source unit '*' unsupported Target TYPE_USE
    // Use Groovy 2.5.7+ in order to compile this on Java 11+
    // However, Groovy 2.5.7 contains some bugs which make the tests fail. Use 2.5.10.
    compile(project(":vaadin-groovy-builder-v14"))

    // testing
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${properties["junit_version"]}")
    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v10-groovy:${properties["kaributesting_version"]}")
    testImplementation("org.slf4j:slf4j-simple:${properties["slf4j_version"]}")

    // Vaadin
    // don't compile-depend on vaadin-core anymore: the app itself should manage Vaadin dependencies, for example
    // using the gradle-flow-plugin or direct dependency on vaadin-core. The reason is that the app may wish to use the
    // npm mode and exclude all webjars.
    compileOnly("com.vaadin:vaadin-core:${properties["vaadin14_2_version"]}") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    testImplementation("com.vaadin:vaadin-core:${properties["vaadin14_2_version"]}") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    compileOnly("javax.servlet:javax.servlet-api:3.1.0")

    // IDEA language injections
    compile("com.intellij:annotations:12.0")

    // always include support for bean validation
    compile("javax.validation:validation-api:2.0.1.Final")  // so that the BeanFieldGroup will perform JSR303 validations
    implementation("org.hibernate.validator:hibernate-validator:${properties["hibernate_validator_version"]}") {
        exclude(module = "jakarta.validation-api")
    }
    // EL is required: http://hibernate.org/validator/documentation/getting-started/
    implementation("org.glassfish:javax.el:3.0.1-b08")
}

val configureBintray = ext["configureBintray"] as (artifactId: String) -> Unit
configureBintray("vaadin-groovy-builder-v14.2")
