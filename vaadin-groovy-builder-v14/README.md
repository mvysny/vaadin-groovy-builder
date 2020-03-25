[![GitHub tag](https://img.shields.io/github/tag/mvysny/vaadin-groovy-builder.svg)](https://github.com/mvysny/vaadin-groovy-builder/tags)

# Vaadin 14 Groovy Builder

Compatibility chart:

| Vaadin-Groovy Builder version | Supports |
|------------------------|----------|
| 0.0.x | Vaadin 14+ |

> Note: 0.0.x works with Vaadin 13 as well, with some limitations
  as outlined in [Issue #19](https://github.com/mvysny/karibu-dsl/issues/19).

## Using in your projects

You can include Vaadin-Groovy-Builder library into your WAR project very easily,
simply by including appropriate Gradle dependency:

```groovy
repositories {
    jcenter()  // or mavenCentral()
}

dependencies {
    implementation("com.github.mvysny.vaadin-groovy-builder:vaadin-groovy-builder-v14:x.y.z")
}
```

> Note: obtain the newest version from the latest tag name above

Maven: since Vaadin-Groovy-Builder is in Maven-Central, it's very simple, just add it as a dependency:

```xml
<project>
	<dependencies>
		<dependency>
			<groupId>com.github.mvysny.vaadin-groovy-builder</groupId>
			<artifactId>vaadin-groovy-builder-v14</artifactId>
			<version>x.y.z</version>
		</dependency>
    </dependencies>
</project>
```

However, to quickly test out Vaadin Groovy Builder you can simply start with one
of the example applications below.

> Note: If you're using Vaadin 14.2, please use the following artifact instead:
> `vaadin-groovy-builder-v14.2`. It contains support for additional components
> like Scroller and DateTimePicker.

### UI Base Application

A very simple Gradle-based application archetype is located here: [Vaadin Groovy Builder Example](https://github.com/mvysny/vaadin-groovy-builder-example).
The project only shows a very simple Button, which makes it an ideal quick start application for experimenting
and further development. For more complex example please take a look at the [example-v14](../example-v14).
 
To generate the archetype and run the app:
  
```bash
git clone https://github.com/mvysny/vaadin-groovy-builder-example
cd vaadin-groovy-builder-example
./gradlew appRun
```

The app will run on [http://localhost:8080](http://localhost:8080).

Please see the [archetype github page](https://github.com/mvysny/vaadin-groovy-builder-example) for further
details on development.

### The "Beverage Buddy" Example Application

The [example-v14](../example-v14) is the Beverage Buddy app
backed by a dummy data (no database).
Quickly run the bundled example application from the command-line:

```bash
git clone https://github.com/mvysny/vaadin-groovy-builder
cd vaadin-groovy-builder
./gradlew example-v14:appRun
```

The example app will be running at [http://localhost:8080](http://localhost:8080).

In case of questions you can always [browse the sample project sources](example-v14).

## Tutorials

### How to write DSLs

The following example shows a really simple form with two fields and one "Save" button (todo screenshot).

```groovy
verticalLayout {
  w = null

  formLayout {
    textField("Name:") {
      focus()
    }
    textField("Age:") {}
  }
  horizontalLayout {
    content { align(right, middle) }
    button("Save") {
      addClickListener { okPressed() }
      setPrimary()
    }
  }
}
```

### VerticalLayout / HorizontalLayout

Vaadin 8 layouting used to depend extensively on the usage of `VerticalLayout` and `HorizontalLayout`. However, the layouting
in Vaadin 10 has been changed radically and it now employs the so-called *flex layout*. Vaadin 10 still
does provide `VerticalLayout` and `HorizontalLayout` classes which loosely resembles their Vaadin 8 counterparts,
however they use flex layout under the hood and hence there are very important differences.

Generally you use the new `HorizontalLayout` as follows:

```groovy
horizontalLayout {
  content { align(right, middle) }
}
```

Important notes:

* `HorizontalLayout` only supports one row of components; if you have multiple rows you need to use `FlexLayout`.
* Never use `setSizeFull()` nor set the `setWidth("100%")` as you used to do with Vaadin 8. With Vaadin 10 it will
not work as you expect. With Vaadin 8 the child would fill the slot allocated by HorizontalLayout. However with Vaadin 10 and flex layout
there are no slots; setting the width to `100%` would make the component match the width of parent - it would set it to be as wide as
the HorizontalLayout is.

Instead of setting the width of the child to `100%`, set the width to some ideal
width, say, `100px` (or `null` to wrap its contents).
The child will initially be exactly
as wide as you tell it to be; if you use `flexGrow` the component will
enlarge itself automatically.

To alter the layout further, call the following properties on children:
* Most important: `flexGrow` (and its brother `isExpand`) expands that particular
  child to take up all of the remaining space. The child
  is automatically enlarged.
* `verticalAlignSelf` to align child vertically; it is not possible to align particular child horizontally
* `flexShrink` - when there is not enough room for all children then they are shrank
* `flexBasis`

Please read the [Vaadin 10 server-side layouting for Vaadin 8 and Android developers](http://mavi.logdown.com/posts/6855605) for a tutorial on how to
use `VerticalLayout`/`HorizontalLayout`.

### Writing your own components

Usually one writes custom components by extending the `GComposite` class. Please read the [Creating a Component](https://vaadin.com/docs/v10/flow/creating-components/tutorial-component-composite.html) for more details.

> We promote composition over inheritance, similar to [React's Composition vs Inheritance](https://reactjs.org/docs/composition-vs-inheritance.html).
You should always extend `GComposite` instead of extending e.g. `HorizontalLayout` - extending from `HorizontalLayout` makes
it part of your class and you can't replace it with e.g. `FlowLayout` without breaking backward compatibility.

An example of such a component:

```groovy
class ButtonBar extends GComposite {
    private final root = ui {
        // create the component UI here; maybe even attach very simple listeners here
        horizontalLayout {
            button("ok") {
                addClickListener { okClicked() }
            }
            button("cancel") {
                addClickListener { cancelClicked() }
            }
        }
    }

    ButtonBar() {
        // perform any further initialization here
    }

    // listener methods here
    private void okClicked() {}

    private void cancelClicked() {}
}
```

To allow for your component to be inserted into other layouts, you will need to
introduce your own extension method for your component.
Just write the following code below your component (see
[Groovy Extension Modules](https://mrhaki.blogspot.com/2013/01/groovy-goodness-adding-extra-methods.html)
on how to register the extension module class to Groovy):

```groovy
class MyExtensionMethods {
    @NotNull
    static ButtonBar flexLayout(@NotNull HasComponents self,
                                 @DelegatesTo(value = ButtonBar, strategy = Closure.DELEGATE_FIRST) @NotNull Closure block) {
        init(self, new ButtonBar(), block)
    }
}
```

This will allow you to use your component as follows:

```groovy
verticalLayout {
  buttonBar {
    styleName = "black"
    ... // etc
  }
}
```

#### The `GComposite` Pattern

The advantage of extending from `GComposite`, instead of extending the layout (e.g. `VerticalLayout`) directly, is as follows:

* The component public API is not polluted by methods coming from the `VerticalLayout`,
  resulting in a more compact and to-the-point API. The API coming from `GComposite` is
  tiny in comparison.
* Since the `VerticalLayout` API doesn't leak into our component, we are free to
  replace the `VerticalLayout` with any other layout in the future, without breaking the API.
* The UI structure is more clearly visible. Take the `ButtonBar` class below as
  an example: it can clearly be seen that the buttons are nested in the `HorizontalLayout`:

Example 1.: ButtonBar extending `GComposite` with a clear UI hierarchy
```groovy
class ButtonBar extends GComposite {
    val root = ui {
        horizontalLayout {
            button("ok")
        }
    }
}
```

Example 2.: ButtonBar extending HorizontalLayout without a clear indication that
the button is nested in a horizontal layout:
```groovy
class ButtonBar extends HorizontalLayout {
    ButtonBar() {
        button("ok")
    }
}
```

The `root` variable will be marked by the IDE as unused. This is okay: the
side-effect of the `ui {}` is that it runs the `horizontalLayout()` function
which then attaches the `HorizontalLayout` to the `GComposite` itself.
However, you may prefer to get rid of this unused `root` variable and call the
`ui {}` from the constructor. The downside is that the
UI-creating code will be indented by two tabs instead of one.

## Launching the example project in Intellij IDEA:

### Gradle's Gretty appRun

You will need to download and install [Intellij Community Edition](https://www.jetbrains.com/idea/download/index.html).
Then, import the project in question by telling Intellij to open the `build.gradle` file as Project.

After the project is imported and all libraries are downloaded, just click the right-most tool window labeled "Gradle";
in the tree just expand the "Tasks" / "gretty", right-click `appRun` and select the topmost option 'Debug ...' - that
way you'll gain both the debugging possibilities and a very basic code redeployment.

Disadvantages:

* For proper code hot-redeployment you'll need JRebel since Java's default debugger hot-redeployment is pretty weak.
  You can work around this by using Tomcat as said below, however that requires the Intellij Ultimate Edition. Luckily,
  it's cheaper than JRebel :-D
* You can also install the [DCEVM](http://ssw.jku.at/dcevm/) patches. It's very easy on Ubuntu - just install the `openjdk-8-jre-dcevm`
  package and enable it by passing the `-dcevm` VM option.

### Tomcat

Please see https://github.com/mvysny/karibu-helloworld-application for tutorial on how to launch a WAR project in Tomcat in Intellij.
Then:

* Open this whole project in Intellij IDEA Ultimate, simply by File / Open... and clicking [build.gradle](build.gradle).
* Navigate to the `example-v8` project and launch it as an exploded WAR in Tomcat

## Testing

Please see the [Karibu-Testing](https://github.com/mvysny/karibu-testing) library, to learn more about how to test Karibu-DSL apps.
