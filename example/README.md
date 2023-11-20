# Vaadin 14 Groovy Builder Example App / Archetype

A port of [Vaadin Beverage Buddy](https://github.com/mvysny/beverage-buddy-vok)
to Groovy. Runs on [Vaadin Boot](https://github.com/mvysny/vaadin-boot).

# Preparing Environment

The Vaadin 14 build requires node.js and npm. Vaadin Gradle plugin will install it for
you automatically (handy for the CI); alternatively you can install it to your OS:

* Windows: [node.js Download site](https://nodejs.org/en/download/) - use the .msi 64-bit installer
* Linux: `sudo apt install npm`

Also make sure that you have Java 8 (or higher) JDK installed.

## Getting Started

To quickly start the app, just type this into your terminal:

```bash
./gradlew clean build example:run
```

Gradle will automatically download all dependencies will run your app in it. Your app will be running on
[http://localhost:8080](http://localhost:8080).

We suggest you use [Intellij IDEA](https://www.jetbrains.com/idea/download)
to edit the project files; the Community edition is enough for all development purposes.
From your IDE, simply run the `Main.main()` function.

## Supported Modes

Runs in Vaadin 14 npm mode, using the [Vaadin Gradle Plugin](https://github.com/vaadin/vaadin-gradle-plugin).

Both the [development and production modes](https://vaadin.com/docs/v14/flow/production/tutorial-production-mode-basic.html) are supported.
To prepare for development mode, just run:

```bash
./gradlew clean example:vaadinPrepareFrontend
```

To build in production mode, just run:

```bash
./gradlew clean build -Pvaadin.productionMode
```

If you don't have node installed in your CI environment, Vaadin Gradle plugin will
install it for you automatically.

# Workflow

To compile the entire project in production mode, run `./gradlew -Pvaadin.productionMode`.

To run the application in development mode, run `./gradlew clean build example:run` and open [http://localhost:8080/](http://localhost:8080/).

To produce a runnable production-mode app:
- run `./gradlew -Pvaadin.productionMode`
- You will find the app zip file in the `example/build/distributions/` folder.
- To revert your environment back to development mode, just run `./gradlew` or `./gradlew vaadinPrepareFrontend`
  (omit the `-Pvaadin.productionMode`) switch.

This will allow you to quickly start the example app and allow you to do some basic modifications.

Note that the app doesn't build to WAR, but builds into a self-contained runnable app instead.
