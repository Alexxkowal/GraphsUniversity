package com.graphs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node<T> {
    private T content;

    public Node() {
    }

    public Node(T content) {
        this.content = content;
    }

    public Node(Node<? extends T> other) {
        this.content = other.getContent();
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.content.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;
        Node<?> object = (Node<?>) o;
        return Objects.equals(this.content, object.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.content);
    }
}
