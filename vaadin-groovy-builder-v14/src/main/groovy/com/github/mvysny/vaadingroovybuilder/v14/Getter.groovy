package com.github.mvysny.vaadingroovybuilder.v14

import com.vaadin.flow.function.SerializableFunction
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import java.beans.Introspector
import java.beans.PropertyDescriptor
import java.lang.reflect.Method

/**
 * A getter which is serializable.
 * @author mavi
 */
@CompileStatic
@EqualsAndHashCode(includes = ["self", "propertyName"])
class Getter implements SerializableFunction<Object, Object> {
    @NotNull final Class<?> self
    @NotNull final String propertyName

    private transient Method getter

    Getter(@NotNull Class<?> self, @NotNull String propertyName) {
        Objects.<Object>requireNonNull(self, "self")
        Objects.<Object>requireNonNull(propertyName, "propertyName")
        this.self = self
        this.propertyName = propertyName
        getGetter() // verifies that the getter exists
    }

    @NotNull
    private Method getGetter() {
        if (getter == null) {
            PropertyDescriptor[] descriptors = Introspector.getBeanInfo(self).propertyDescriptors
            PropertyDescriptor descriptor = descriptors.find { it.name == propertyName }
            Objects.requireNonNull(descriptor) {
                "No such field '$propertyName' in $self; available properties: ${descriptors.collect { it.name } .join(", ")}".toString()
            }
            getter = Objects.requireNonNull(descriptor.readMethod) {
                "The $self.$propertyName property does not have a getter: $descriptor".toString()
            }
        }
        return getter
    }

    /**
     * Returns the value of the property for given bean.
     * @param bean the bean, not null.
     * @return the value, may be null.
     */
    @Nullable
    Object get(@NotNull Object bean) {
        getGetter().invoke(bean)
    }

    @Override
    String toString() {
        return "Getter{${getGetter()}}"
    }

    @Override
    Object apply(Object o) {
        get(o)
    }
}
