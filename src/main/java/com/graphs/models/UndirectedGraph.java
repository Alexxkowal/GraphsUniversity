package com.graphs.models;

import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class UndirectedGraph<T> extends AbstractGraph<T> {
    public UndirectedGraph(boolean isWeighted) {
        super(isWeighted);
    }

    public UndirectedGraph() {
        super();
    }

    public UndirectedGraph(String path) {
        super(path);
    }

    public UndirectedGraph(Graph<T> graph) {
        super(graph);
    }

    @Override
    public void addEdge(Node<T> firstNode, Node<T> secondNode, double weight) {
        addNode(firstNode);
        addNode(secondNode);
        List<Edge<T>> edgeList = EdgeFactory.createUndirectedEdge(firstNode, secondNode, weight);
        edges.get(firstNode).remove(edgeList.getFirst());
        edges.get(secondNode).remove(edgeList.get(1));
        edges.get(firstNode).add(edgeList.getFirst());
        edges.get(secondNode).add(edgeList.get(1));
    }


    @Override
    public void removeEdge(Node<T> firstNode, Node<T> secondNode) {
        Set<Edge<T>> firstSet = edges.get(firstNode);
        if (firstSet != null) {
            firstSet.remove(EdgeFactory.createDirectedEdge(firstNode, secondNode, 0));
        }
        Set<Edge<T>> secondSet = edges.get(secondNode);
        if (secondSet != null) {
            secondSet.remove(EdgeFactory.createDirectedEdge(secondNode, firstNode, 0));
        }
    }


    @Override
    public Set<Edge<T>> getAllEdges() {
        Set<Edge<T>> edgeSet = new HashSet<>();
        for (Set<Edge<T>> setEdges : edges.values()) {
            for (var edge : setEdges) {
                if (edge.getStartNode().hashCode() > edge.getEndNode().hashCode()) {
                    continue;
                }
                edgeSet.add(edge);
            }
        }
        return edgeSet;
    }

    public List<Edge<T>> findMinimumSpanningTree() {
        List<Edge<T>> mst = new ArrayList<>();
        Set<Node<T>> visited = new HashSet<>();

        PriorityQueue<Edge<T>> edgeQueue = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));
        if (edges.isEmpty()) return mst;
        Node<T> startNode = edges.keySet().iterator().next();
        visit(startNode, visited, edgeQueue);
        while (!edgeQueue.isEmpty() && visited.size() < edges.size()) {
            Edge<T> edge = edgeQueue.poll();
            Node<T> nextNode = edge.getEndNode();
            if (visited.contains(nextNode)) continue;
            mst.add(edge);
            visit(nextNode, visited, edgeQueue);
        }
        return mst;
    }

    private void visit(Node<T> node, Set<Node<T>> visited, PriorityQueue<Edge<T>> queue) {
        visited.add(node);
        for (Edge<T> edge : edges.getOrDefault(node, Collections.emptySet())) {
            if (!visited.contains(edge.getEndNode())) {
                queue.add(edge);
            }
        }
    }
}
