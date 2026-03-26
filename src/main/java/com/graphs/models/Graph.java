package com.graphs.models;

import com.graphs.models.edge.Edge;

import java.util.*;
import java.util.stream.Collectors;

public interface Graph<T> {
    boolean addNode(Node<T> node);

    boolean removeNode(Node<T> node);

    void addEdge(Node<T> firstNode, Node<T> secondNode);

    void addEdge(Node<T> firstNode, Node<T> secondNode, double weight);

    void removeEdge(Node<T> firstNode, Node<T> secondNode);

    boolean isWeighted();

    Set<Edge<T>> getEdges(Node<T> node);

    Set<Node<T>> getNodes();

    Set<Edge<T>> getAllEdges();

    boolean hasEdge(Node<T> firsNode, Node<T> secondNode);

    boolean hasNode(T content);

    String getAdjacencyList(Node<T> node);

    Set<Node<T>> getSharedNeighbor(Node<T> u, Node<T> v);

    void setEdges(Map<Node<T>, Set<Edge<T>>> map);

    static <T> Graph<T> graphIntersection(Graph<T> firstGraph, Graph<T> secondGraph) throws NoSuchMethodException, IllegalArgumentException {

        if (!firstGraph.getClass().isInstance(secondGraph) || (firstGraph.isWeighted() != secondGraph.isWeighted())) {
            throw new IllegalArgumentException("Классы графов не совпадают");
        }
        try {
            Graph<T> resultGraph = firstGraph.getClass().getDeclaredConstructor().newInstance();
            HashSet<Node<T>> secondNodes = new HashSet<>(secondGraph.getNodes());
            Set<Node<T>> equalNodes = firstGraph.getNodes().stream().filter(secondNodes::contains).collect(Collectors.toSet());
            if (equalNodes.isEmpty()) {
                return resultGraph;
            }
            HashMap<Node<T>, Set<Edge<T>>> edgeHashMap = new HashMap<>();
            for (Node<T> node : equalNodes) {
                Collection<Edge<T>> secondEdges = secondGraph.getEdges(node);
                Set<Edge<T>> intersection = firstGraph.getEdges(node).stream().filter(secondEdges::contains).collect(Collectors.toSet());
                edgeHashMap.put(node, intersection);
            }
            resultGraph.setEdges(edgeHashMap);
            return  resultGraph;
        } catch (Exception e) {
            throw new NoSuchMethodException("Проверьте наличие пустого конструктора у графа");
        }
    }

    int countPaths(Node<T> u, Node<T> v);
}
