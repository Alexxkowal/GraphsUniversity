package com.graphs.utils;

import com.graphs.models.DirectedGraph;
import com.graphs.models.Graph;
import com.graphs.models.Node;
import com.graphs.models.UndirectedGraph;
import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;

import java.io.*;
import java.util.*;

public class TextGraphSerializer<T> implements GraphSerializer<T> {
    @Override
    public void exportGraph(Graph<T> graph, String filePath) {
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file)) {
            String className = graph instanceof DirectedGraph<T> ? "directed" : "undirected";
            String graphType = graph.isWeighted() ? "weighted" : "unweighted";
            fileWriter.write(className + " " + graphType + System.lineSeparator());
            Set<Node<T>> nodeSet = new HashSet<>();
            for (Node<T> node : graph.getNodes()) {
                for (Edge<T> edge : graph.getEdges(node)) {
                    String s;
                    if (graph instanceof UndirectedGraph<T>) {
                        if (edge.getStartNode().hashCode() > edge.getEndNode().hashCode()) {
                            continue;
                        }
                    }
                    if (graph.isWeighted()) {
                        s = edge.getStartNode() + " " + edge.getEndNode() + " " + edge.getWeight() + System.lineSeparator();
                    } else {
                        s = edge.getStartNode() + " " + edge.getEndNode() + System.lineSeparator();
                    }
                    nodeSet.add(edge.getStartNode());
                    nodeSet.add(edge.getEndNode());
                    fileWriter.write(s);
                }
            }
            Set<Node<T>> remainderNodes = new HashSet<>(graph.getNodes());
            remainderNodes.removeAll(nodeSet);
            for(Node<T> node: remainderNodes){
                fileWriter.write(node.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importGraph(Graph<T> graph, String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = bufferedReader.readLine();
            boolean isDirected, isWeighted;
            List<String> parts = List.of(line.split(" "));
            isDirected = parts.getFirst().equals("directed");
            if (isDirected && !(graph instanceof DirectedGraph<T>)){
                throw new IllegalArgumentException("Graph is Undirected!!!");
            }
            isWeighted = parts.get(1).equals("weighted");
            Map<String, Node<T>> nodeCache = new HashMap<>();
            Node<T> firstNode;
            Node<T> secondNode;
            int weight;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                parts = List.of(line.split(" "));
                if (parts.size() == 1){
                    firstNode = nodeCache.computeIfAbsent(parts.getFirst(), name -> (Node<T>) new Node<>(name));
                    graph.addNode(firstNode);
                    continue;
                }
                firstNode = nodeCache.computeIfAbsent(parts.getFirst(), name -> (Node<T>) new Node<>(name));
                secondNode = nodeCache.computeIfAbsent(parts.get(1), name -> (Node<T>) new Node<>(name));
                weight = isWeighted?Integer.parseInt(parts.get(2)):1;
                graph.addEdge(firstNode, secondNode, weight);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

