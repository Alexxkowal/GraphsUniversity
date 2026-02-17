package com.graphs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node<T> {
    private final List<Node<? extends  T>> neighbors;
    private T content;

    public Node() {
        neighbors = new ArrayList<>();
    }

    public Node(T content) {
        this.content = content;
        neighbors = new ArrayList<>();
    }

    public Node (Node<? extends T> other){
        this.content = other.getContent();
        this.neighbors = new ArrayList<>(other.getNeighbors());
    }

    public List<Node<? extends T>> getNeighbors() {
        return neighbors;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return this.content.toString();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;
        Node<?> object = (Node <?>) o;
        return Objects.equals(this.content, object.getContent());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.content);
    }
}
