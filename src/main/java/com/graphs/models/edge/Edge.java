package com.graphs.models.edge;

import com.graphs.models.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Edge<T> {
    private final Node<T> startNode;
    private final Node<T> endNode;
    private double weight;

    Edge(Node<T> startNode, Node<T> endNode, double weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    public Node<T> getStartNode() {
        return startNode;
    }

    public Node<T> getEndNode() {
        return endNode;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge<?> edge)) return false;
        return
//                this.weight == edge.getWeight() &&
                        Objects.equals(this.startNode, edge.getStartNode()) &&
                        Objects.equals(this.endNode, edge.getEndNode());
    }

    // Добавлять вес??
    @Override
    public int hashCode() {
        return Objects.hash(startNode, endNode);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        if (Double.isNaN(weight)){
            return startNode + " " + endNode;
        }
        return startNode + " " + endNode + " " + weight;
    }

}
