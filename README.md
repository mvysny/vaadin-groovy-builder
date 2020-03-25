[![GitHub tag](https://img.shields.io/github/tag/mvysny/vaadin-groovy-builder.svg)](https://github.com/mvysny/vaadin-groovy-builder/tags)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mvysny.vaadin-groovy-builder/vaadin-groovy-builder-v14/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mvysny.vaadin-groovy-builder/vaadin-groovy-builder-v14)
![Java CI](https://github.com/mvysny/vaadin-groovy-builder/workflows/Java%20CI/badge.svg)

# Vaadin Groovy Builder (Vaadin Groovy Extensions)

This is a Groovy extensions and DSL library for the [Vaadin](https://www.vaadin.com) framework.

This library:

* Allows you to create Vaadin UI designs/component graphs in the structured code way; the idea behind
  is explained in the [DSLs: Explained for Vaadin 8](http://www.vaadinonkotlin.eu/dsl_explained.html) article /
  [DSLs: Explained for Vaadin 10](http://www.vaadinonkotlin.eu/dsl_explained-v10.html) article.
  The general DSL idea is explained in [Kotlin Type-Safe Builders](https://kotlinlang.org/docs/reference/type-safe-builders.html).
* Additional useful methods which Vaadin lacks

The full documentation:

* Unfortunately there is no support for Vaadin 8 and lower
* [Vaadin-Groovy-Builder Vaadin Platform (Vaadin 14+) tutorial](vaadin-groovy-builder-v14)

## Example Project

Code example:

```groovy
@Route("")
@CompileStatic
class MainView extends GComposite {
    private TextField name
    private Button sayHello
    private def root = ui {
        verticalLayout {
            name = textField("Your name") {}
            sayHello = button("Say hello") {}
        }
    }
    MainView() {
        sayHello.addClickListener {
            Notification.show("Hello, ${name.value}")
        }
    }
}
```

See [Vaadin Groovy Builder Example](https://github.com/mvysny/vaadin-groovy-builder-example).

# License

Licensed under the [MIT License](https://opensource.org/licenses/MIT).

Copyright (c) 2017-2018 Martin Vysny

All rights reserved.

Permission is hereby granted, free  of charge, to any person obtaining
a  copy  of this  software  and  associated  documentation files  (the
"Software"), to  deal in  the Software without  restriction, including
without limitation  the rights to  use, copy, modify,  merge, publish,
distribute,  sublicense, and/or sell  copies of  the Software,  and to
permit persons to whom the Software  is furnished to do so, subject to
the following conditions:

The  above  copyright  notice  and  this permission  notice  shall  be
included in all copies or substantial portions of the Software.
THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
