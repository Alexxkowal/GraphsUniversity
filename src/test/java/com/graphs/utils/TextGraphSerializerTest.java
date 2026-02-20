package com.graphs.utils;

import com.graphs.models.DirectedGraph;
import com.graphs.models.Graph;
import com.graphs.models.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextGraphSerializerTest {

    @Test
    void testExportAndImport() {
        DirectedGraph<String> directedGraph = new DirectedGraph<>(true);
        TextGraphSerializer<String> textGraphSerializer = new TextGraphSerializer<>();
        directedGraph.addNode(new Node<>("T"));
        directedGraph.addEdge(new Node<>("A"), new Node<>("B"), 5);
        directedGraph.addEdge(new Node<>("B"), new Node<>("C"), 2);
        directedGraph.addEdge(new Node<>("C"), new Node<>("A"), 3);
        directedGraph.addEdge(new Node<>("D"), new Node<>("F"), 4);
        directedGraph.addEdge(new Node<>("G"), new Node<>("G"), 5);
        directedGraph.addNode(new Node<>("H"));
        textGraphSerializer.exportGraph(directedGraph, "test.txt");
        DirectedGraph<String> directedGraph2 = new DirectedGraph<>(true);
        textGraphSerializer.importGraph(directedGraph2, "test.txt");
        Assertions.assertEquals(directedGraph.getNodes().size(), directedGraph2.getNodes().size());
        Node<String> nodeA = new Node<>("A");
        var edgesA = directedGraph2.getEdges(nodeA);

        Assertions.assertFalse(edgesA.isEmpty(), "Список ребер не должен быть пустым!");
    }
}