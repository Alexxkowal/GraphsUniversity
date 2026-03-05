package com.graphs.cli;

import com.graphs.models.DirectedGraph;
import com.graphs.models.Graph;
import com.graphs.models.Node;
import com.graphs.models.UndirectedGraph;
import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleInterface {
    private Graph<String> graph;
    private final Scanner scanner = new Scanner(System.in);
    Map<String, Graph<String>> graphs = new HashMap<>();
    String currentGraphName;

    public void start() {
        System.out.println("Для выхода из программы введите 0");
        graph = initialize();
        String choose;
        while (true) {
            System.out.printf("Текущий граф %s \n", currentGraphName);
            System.out.println("Для выхода из программы введите 0");
            System.out.println("1 - Добавить вершину");
            System.out.println("2 - Удалить вершину");
            System.out.println("3 - Добавить ребро");
            System.out.println("4 - Удалить ребро");
            System.out.println("5 - Показать все ребра");
            System.out.println("6 - Показать все узлы");
            System.out.println("7 - Показать список смежности для узла");
            System.out.println("8 - Показать список смежности ");
            System.out.println("9 - Создать новый граф");
            System.out.println("10 - Переключить граф");

            choose = scanner.nextLine().trim();
            switch (choose) {
                case "1":
                    addNode();
                    break;
                case "2":
                    removeNode();
                    break;
                case "3":
                    addEdge();
                    break;
                case "4":
                    removeEdge();
                case "5":
                    showEdges();
                    break;
                case "6":
                    showAllNodes();
                    break;
                case "7":
                    showNodeAdjacencyList();
                    break;
                case "8":
                    showAdjacencyList();
                    break;
                case "9":
                    initialize();
                    break;
                case "10":
                    changeGraph();
                    break;
                default:
                    System.out.println("Неправильный ввод, повторите попытку");
                    break;
            }
        }
    }

    private void showNodeAdjacencyList() {
        String content;
        System.out.println("Введите узел ");
        content = scanner.nextLine().trim();
        if (graph.hasNode(content)) {
            System.out.println(graph.getAdjacencyList(new Node<>(content)));
        } else {
            System.out.println("Такого узла нет");
        }
    }

    private void showAdjacencyList() {
        System.out.println(graph.toString());
    }

    private Graph<String> initialize() {
        String graphName;
        String direct;
        String isWeightedInput;
        Graph<String> createdGraph;
        boolean isWeighted = false;
        boolean isDirected = false;
        boolean firstChoose = false;
        boolean secondChoose = false;
        System.out.println("Введите название графа: ");
        graphName  = scanner.nextLine().trim();
        while (graphs.containsKey(graphName) || graphName.isEmpty()){
            System.out.println("Имя занято, введите другое");
            graphName = scanner.nextLine().trim();
        }
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
            System.out.println("Создан направленный граф, взвешенность: " + (isWeighted ? "взвешенный" : "невзвешенный"));
            createdGraph = new DirectedGraph<>(isWeighted);
        } else {
            System.out.println("Создан ненаправленный граф, взвешенность: " + (isWeighted ? "взвешенный" : "невзвешенный"));
            createdGraph = new UndirectedGraph<>(isWeighted);
        }
        setCurrentGraphName(graphName);
        graphs.put(graphName, createdGraph);
        return createdGraph;
    }

    private void addNode() {
        System.out.println("Введите содержимое узла");
        String content = scanner.nextLine().trim();
        Node<String> node = new Node<>(content.trim());
        boolean isAdded = graph.addNode(node);
        if (isAdded) {
            System.out.println("Узел с контентом " + content + " успешно добавлен в граф");
        } else {
            System.out.println("Узел с таким именем уже существует");
        }
    }

    private void removeNode() {
        System.out.println("Введите узел");
        String content = scanner.nextLine().trim();
        Node<String> nodeForRemove = new Node<>(content.trim());
        boolean isRemoved = graph.removeNode(nodeForRemove);
        if (isRemoved) {
            System.out.println("Узел " + content + " успешно удален");
        } else {
            System.out.println("Такого узла нет в графе");
        }
    }

    private void addEdge() {
        System.out.println("Введите первый узел");
        String firstNodeContent = scanner.nextLine().trim();
        System.out.println("Введите второй узел");
        String secondNodeContent = scanner.nextLine().trim();
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
        if (graph.hasEdge(new Node<>(firstNodeContent), new Node<>(secondNodeContent))) {
            String rewrite;
            System.out.println("Такая связь уже существует. Перезаписать?");
            System.out.println("1 - да, другое - нет");
            rewrite = scanner.nextLine();
            switch (rewrite) {
                case "1":
                    System.out.println("Перезапись...");
                    break;
                default:
                    return;
            }
        }
        if (graph.isWeighted()) {
            System.out.println("Введите вес");
            int weight = Integer.parseInt(scanner.nextLine());
            System.out.println("Связь " + firstNode + " и " + secondNode + " с весом " + weight + " создана");
            graph.addEdge(firstNode, secondNode, weight);
            return;
        }
        System.out.println("Связь " + firstNode + " и " + secondNode + " создана");
        graph.addEdge(firstNode, secondNode);
    }

    private void removeEdge() {
        System.out.println("Введите первый узел");
        String firstNodeContent = scanner.nextLine().trim();
        System.out.println("Введите второй узел");
        String secondNodeContent = scanner.nextLine().trim();
        Node<String> firstNode = new Node<>(firstNodeContent);
        Node<String> secondNode = new Node<>(secondNodeContent);
        if (!(graph.getNodes().contains(firstNode))) {
            System.out.println("Узла с именем " + firstNodeContent + " не существует");
            return;
        }
        if (!(graph.getNodes().contains(secondNode))) {
            System.out.println("Узла с именем " + secondNodeContent + " не существует");
            return;
        }
        System.out.println("Связь " + firstNodeContent + " и " + secondNodeContent + " удалена");
        graph.removeEdge(firstNode, secondNode);
    }

    private void showEdges() {
        var allEdges = graph.getAllEdges();
        if (allEdges.isEmpty()) {
            System.out.println("Связей пока нет");
            return;
        }
        System.out.println("Список всех ребер:");
        for (var e : allEdges) {
            System.out.print(e.getStartNode() + "-----" + e.getEndNode());
            if (graph.isWeighted()) {
                System.out.println(" вес: " + e.getWeight());
            } else {
                System.out.println();
            }
        }
    }

    private void showAllNodes() {
        System.out.println("Все узлы в графе: ");
        for (var node : graph.getNodes()) {
            System.out.println(node);
        }
    }
    private void changeGraph(){
        if (graphs.keySet().isEmpty()){
            System.out.println("Нет доступных графов");
            return;
        }
        System.out.println("Доступные графы");
        for (String name: graphs.keySet()){
            System.out.println(name);
        }
        System.out.println("Введите название графа для переключения или exit для выхода");
        String name = scanner.nextLine().trim();
        while (true) {
            if (name.equals("exit")) {
                return;
            } else {
                if (graphs.containsKey(name)) {
                    graph = graphs.get(name);
                    return;
                } else {
                    System.out.println("Такого графа не существует");
                }
            }
            name = scanner.nextLine().trim();
        }
    }
    private void setCurrentGraphName(String name){
        currentGraphName = name;
    }
}



