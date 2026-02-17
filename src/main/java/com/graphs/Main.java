package com.graphs;

import com.graphs.models.Node;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Node<Integer> node1 = new Node<>();
        node1.setContent(123321);
        Node<Integer> node2 = new Node<>(node1);
        System.out.println(node2.getContent());
    }
}