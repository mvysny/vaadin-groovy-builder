# Contributing

Thank you so much for making the library better.

The library is in Groovy, just use the default Groovy formatter rules.

## Tests

All tests are written using Junit5. See `vaadin-groovy-builder-v14/src/test/groovy` for more details.

### Running Tests

In order to launch the suite, simply navigate to the `vaadin-groovy-builder-v14`
project in your Intellij, then right-click the `test` folder and select
*Run All Tests*.

You can run all tests with all Vaadin versions simply by running Gradle from the command-line:

```
./gradlew test
```

# Developing

Please feel free to open bug reports to discuss new features; PRs are welcome as well :)

## Releasing

To release the library to Maven Central:

1. Edit `build.gradle.kts` and remove `-SNAPSHOT` in the `version=` stanza
2. Commit with the commit message of simply being the version being released, e.g. "0.0.9"
3. git tag the commit with the same tag name as the commit message above, e.g. `0.0.9`
4. `git push`, `git push --tags`
5. Run `./gradlew clean build publish`
6. Continue to the [OSSRH Nexus](https://oss.sonatype.org/#stagingRepositories) and follow the [release procedure](https://central.sonatype.org/pages/releasing-the-deployment.html).
7. Add the `-SNAPSHOT` back to the `version=` while increasing the version to something which will be released in the future,
   e.g. 0.0.10-SNAPSHOT, then commit with the commit message `0.0.10-SNAPSHOT` and push.

