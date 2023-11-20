import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.util.*

plugins {
    id("java-library")
    groovy
    id("org.gretty") version "3.0.6"
    `maven-publish`
    id("com.vaadin") version "0.14.10.4" apply(false)
    signing
}

defaultTasks("clean", "build")

allprojects {
    group = "com.github.mvysny.vaadin-groovy-builder"
    version = "0.0.10-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply {
        plugin("java-library")
        plugin("maven-publish")
        plugin("groovy")
        plugin("org.gradle.signing")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            // to see the exceptions of failed tests in the CI console.
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    // creates a reusable function which configures proper deployment to Bintray
    ext["configureBintray"] = { artifactId: String ->

        // following https://dev.to/kengotoda/deploying-to-ossrh-with-gradle-in-2020-1lhi
        java {
            withJavadocJar()
            withSourcesJar()
        }

        tasks.withType<Javadoc> {
            isFailOnError = false
        }

        publishing {
            repositories {
                maven {
                    setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = project.properties["ossrhUsername"] as String? ?: "Unknown user"
                        password = project.properties["ossrhPassword"] as String? ?: "Unknown user"
                    }
                }
            }
            publications {
                create("mavenJava", MavenPublication::class.java).apply {
                    groupId = project.group.toString()
                    this.artifactId = artifactId
                    version = project.version.toString()
                    pom {
                        description = "Vaadin Groovy Builder, Groovy extensions/DSL for Vaadin"
                        name = artifactId
                        url = "https://github.com/mvysny/vaadin-groovy-builder"
                        licenses {
                            license {
                                name = "The MIT License (MIT)"
                                url = "https://opensource.org/licenses/MIT"
                                distribution = "repo"
                            }
                        }
                        developers {
                            developer {
                                id = "mavi"
                                name = "Martin Vysny"
                                email = "martin@vysny.me"
                            }
                        }
                        scm {
                            url = "https://github.com/mvysny/vaadin-groovy-builder"
                        }
                    }

                    from(components["java"])
                }
            }
        }

        signing {
            sign(publishing.publications["mavenJava"])
        }
    }
}
