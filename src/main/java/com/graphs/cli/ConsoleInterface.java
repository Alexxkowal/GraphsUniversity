package com.graphs.cli;

import com.graphs.models.DirectedGraph;
import com.graphs.models.Graph;
import com.graphs.models.Node;
import com.graphs.models.UndirectedGraph;

import java.util.Scanner;

public class ConsoleInterface {
    private Graph<String> graph;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Для выхода из программы введите 0");
        graph = initialize();
        while (true) {

        }
    }

    private Graph<String> initialize() {
        String direct;
        String isWeightedInput;
        boolean isWeighted = false;
        boolean isDirected = false;
        boolean firstChoose = false;
        boolean secondChoose = false;
        while (!firstChoose) {
            System.out.println("Выберите взвешенность графа 1) Взешенный, 2) Невзвешенный");
            isWeightedInput = scanner.nextLine();
            switch (isWeightedInput) {
                case "1":
                    isWeighted = true;
                    firstChoose = true;
                    break;
                case "2":
                    firstChoose = true;
                    break;
                default:
                    System.out.println("Некорректный ввод");
                    break;
            }
        }
        while (!secondChoose) {
            System.out.println("Выберите тип графа: 1) Направленный; 2) Ненаправленный");
            direct = scanner.nextLine();
            switch (direct) {
                case "1":
                    isDirected = true;
                    secondChoose = true;
                    break;
                case "2":
                    secondChoose = true;
                    break;
                default:
                    System.out.println("Некорректный ввод");
                    break;
            }
        }
        if (isDirected) {
            return new DirectedGraph<>(isWeighted);
        } else {
            return new UndirectedGraph<>(isWeighted);
        }
    }

    private void addNode(String content) {
        Node<String> node = new Node<>(content.trim());
        boolean isAdded = graph.addNode(node);
        if (isAdded) {
            System.out.println("Узел с контентом " + content + " успешно добавлен в граф");
        } else {
            System.out.println("Узел с таким именем уже существует");
        }
    }

    private void removeNode(String content) {
        Node<String> nodeForRemove = new Node<>(content.trim());
        boolean isRemoved = graph.removeNode(nodeForRemove);
        if (isRemoved) {
            System.out.println("Узел " + content + " успешно удален");
        } else {
            System.out.println("Такого узла нет в графе");
        }
    }

    private void addEdge(String firstNodeContent, String secondNodeContent) {
        Node<String> firstNode = new Node<>(firstNodeContent);
        Node<String> secondNode = new Node<>(secondNodeContent);
        boolean firstChoose = false;
        boolean secondChoose = false;
        String choose;

        if (!graph.getNodes().contains(firstNode)) {
            System.out.println("Узла с именем " + firstNodeContent + " нет в графе, создать его?");
            System.out.println("1 - создать, 2 - не создавать (выйти)");
            while (!firstChoose) {
                choose = scanner.nextLine();
                switch (choose) {
                    case "1":
                        System.out.println("Узел успешно создан");
                        firstChoose = true;
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("Некорректный ввод");
                        break;
                }
            }
        }
        if (!graph.getNodes().contains(secondNode)) {
            System.out.println("Узла с именем " + secondNodeContent + " нет в графе, создать его?");
            System.out.println("1 - создать, 2 - не создавать (выйти)");

            while (!secondChoose) {
                choose = scanner.nextLine();
                switch (choose) {
                    case "1":
                        System.out.println("Узел успешно создан");
                        secondChoose = true;
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("Некорректный ввод");
                        break;
                }
            }
        }
        if (graph.isWeighted()){
            System.out.println("Введите вес");
            int weight = Integer.parseInt(scanner.nextLine());
            graph.addEdge(firstNode, secondNode, weight);
            return;
        }
        graph.addEdge(firstNode, secondNode);
    }

    private void removeEdge(String firstNodeContent, String secondNodeContent){
        Node<String> firstNode = new Node<>(firstNodeContent);
        Node<String> secondNode = new Node<>(secondNodeContent);
        if (!(graph.getNodes().contains(firstNode))){
            System.out.println("Узла с именем " + firstNodeContent + " не существует");
            return;
        }
        if (!(graph.getNodes().contains(secondNode))){
            System.out.println("Узла с именем " + secondNodeContent + " не существует");
            return;
        }
        System.out.println("Связь " + firstNodeContent + " и " + secondNodeContent + " удалена");
        graph.removeEdge(firstNode, secondNode);
    }

    private void showEdges(){
        var allEdges = graph.getAllEdges();
        if (allEdges.isEmpty()){
            System.out.println("Связей пока нет");
            return;
        }
        System.out.println("Список всех ребер:");
        for (var e : allEdges){
            System.out.print(e.getStartNode() + "->" + e.getEndNode());
            if (graph.isWeighted()){
                System.out.println(" вес: " + e.getWeight());
            }
            else{
                System.out.println();
            }
        }
    }
}



