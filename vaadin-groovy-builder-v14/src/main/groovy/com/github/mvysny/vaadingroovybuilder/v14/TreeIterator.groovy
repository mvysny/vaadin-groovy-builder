package com.github.mvysny.vaadingroovybuilder.v14

import groovy.transform.CompileStatic

import java.util.function.Function

/**
 * Given a tree root and a closure which computes children, iterates recursively over a tree of objects.
 * Iterator works breadth-first: initially the root itself is offered, then its children, then the child's children etc.
 * @property root the root object of the tree
 * @property children for given node, returns a list of its children.
 */
@CompileStatic
class TreeIterator<T> implements Iterator<T> {
    private final T root
    private final Function<T, Iterator<T>> children

    /**
     * The items to iterate over. Gradually filled with children, until there are no more items to iterate over.
     */
    private final Queue<T> queue = new LinkedList<T>([root])

    TreeIterator(T root, Function<T, Iterator<T>> children) {
        this.root = Objects.requireNonNull(root)
        this.children = Objects.requireNonNull(children)
    }

    @Override
    boolean hasNext() {
        return !queue.isEmpty()
    }

    @Override
    T next() {
        if (!hasNext()) throw new NoSuchElementException()
        final T result = queue.remove()
        children.apply(result).forEachRemaining { T it -> queue.add(it) }
        return result
    }
}
