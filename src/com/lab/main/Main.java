package com.lab.main;

import com.lab.graph.*;
import com.lab.processing.TextFileProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//主函数
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filePath = getFilePath(args, reader);

        Graph graph = TextFileProcessor.processFileToGraph(filePath);
        boolean running = true;
        try {
            while (running) {
                showMenu();
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        showDirectedGraph(graph);
                        break;
                    case "2":
                        queryBridgeWords(reader, graph);
                        break;
                    case "3":
                        generateNewText(reader, graph);
                        break;
                    case "4":
                        calculateShortestPath(reader, graph);
                        break;
                    case "5":
                        performRandomWalk(graph, reader);
                        break;
                    case "6":
                        running = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private static String getFilePath(String[] args, BufferedReader reader) throws IOException {
        if (args.length < 1) {
            System.out.println("请输入文本文件路径:");
            return reader.readLine(); // 从标准输入获取文件路径
        } else {
            return args[0]; // 使用命令行参数提供的文件路径
        }
    }

    private static void showMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Show graph");
        System.out.println("2. Find bridge words");
        System.out.println("3. Generate new text");
        System.out.println("4. Calculate shortest path");
        System.out.println("5. Perform random walk");
        System.out.println("6. Exit");
    }
//展示图
    private static void showDirectedGraph(Graph graph) throws Exception {
        GraphVisualizer.showDirectedGraph(graph, "outputGraph.dot");
        System.out.println("图已成功输出到 outputGraph.dot 文件中");
        GraphVisualizer.renderGraph("outputGraph.dot", "outputGraph.png");
        System.out.println("Graph visualized and saved as outputGraph.png");
    }

    private static void queryBridgeWords(BufferedReader reader, Graph graph) throws IOException {
        System.out.println("请输入两个单词，以查找桥接词（word1 word2）:");
        String[] words = reader.readLine().split("\\s+");
        if (words.length >= 2) {
            String word1 = words[0];
            String word2 = words[1];
            BridgeWordsResult result = graph.queryBridgeWords(word1, word2);
            if (result == null || result.hasError() || result.getBridgeWords() == null || result.getBridgeWords().isEmpty()) {
                if (result == null || result.hasError()) {
                    System.out.println("Error: " + (result == null ? "Result is null" : result.getErrorMessage()));
                } else {
                    System.out.println("No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!");
                }
            } else {
                List<String> bridgeWords = result.getBridgeWords();
                System.out.println("The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + String.join(", ", bridgeWords));
            }
        } else {
            System.out.println("请输入两个有效的单词。");
        }
    }

    private static void generateNewText(BufferedReader reader, Graph graph) throws IOException {
        System.out.println("请输入一行文本，程序将根据桥接词生成新文本:");
        String inputText = reader.readLine();
        String[] words = inputText.split("\\s+");
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            newText.append(words[i]);
            if (i < words.length - 1) {
                String bridgeWord = BridgeWordsResult.getRandomBridgeWord(graph, words[i], words[i + 1]);
                if (bridgeWord != null) {
                    newText.append(" ").append(bridgeWord);
                }
                newText.append(" ");
            }
        }
        System.out.println("生成的新文本:");
        System.out.println(newText.toString());
    }




    private static void calculateShortestPath(BufferedReader reader, Graph graph) throws IOException {
        System.out.println("请输入一个或两个单词，以计算最短路径：");
        String line = reader.readLine();
        String[] words = line.trim().split("\\s+");

        PathFinder pathFinder = new PathFinder(graph);

        if (words.length == 1) {
            String start = words[0];
            Map<String, List<List<String>>> allPaths = pathFinder.findAllShortestPaths(start);
            allPaths.forEach((end, paths) -> {
                String folderName = "path" + File.separator + start + "_" + end;
                File folder = new File(folderName);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                if (paths.isEmpty()) {
                    System.out.println("没有从 " + start + " 到 " + end + " 的路径。");
                } else {
                    System.out.println("所有从 " + start + " 到 " + end + " 的最短路径:");
                    paths.forEach(path -> {
                        int totalWeight = PathFinder.calculatePathWeight(graph, path);
                        System.out.println("路径: " + String.join(" -> ", path) + "，路径权重和：" + totalWeight);
                    });
                }
                try {
                    pathFinder.generateGraphvizFile(folderName, "paths.dot", paths);
                    System.out.println("已生成 Graphviz DOT 文件：" + folderName + "/paths.dot");
                } catch (IOException e) {
                    System.err.println("生成Graphviz DOT文件时出错：" + e.getMessage());
                }
            });
        } else if (words.length == 2) {
            String start = words[0];
            String end = words[1];
            List<List<String>> paths = pathFinder.findShortestPath(start, end);

            String folderName = "path" + File.separator + start + "_" + end;
            File folder = new File(folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            if (paths.isEmpty()) {
                System.out.println("没有从 " + start + " 到 " + end + " 的路径。");
            } else {
                System.out.println("所有从 " + start + " 到 " + end + " 的最短路径:");
                paths.forEach(path -> {
                    int totalWeight = PathFinder.calculatePathWeight(graph, path);
                    System.out.println("路径: " + String.join(" -> ", path) + "，路径权重和：" + totalWeight);
                });
            }

            try {
                pathFinder.generateGraphvizFile(folderName, "paths.dot", paths);
                System.out.println("已生成 Graphviz DOT 文件：" + folderName + "/paths.dot");
            } catch (IOException e) {
                System.err.println("生成Graphviz DOT文件时出错：" + e.getMessage());
            }
        } else {
            System.out.println("输入格式错误，请输入一个或两个单词。");
        }
    }


    private static void performRandomWalk(Graph graph, BufferedReader reader) throws IOException {
        RandomWalk randomWalk = new RandomWalk(graph);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> walkFuture = executor.submit(() -> {
            try {
                randomWalk.performRandomWalk();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Type 's' to stop the walk.");
        while (true) {
            if (reader.ready()) {  // 检查是否有输入
                String input = reader.readLine().trim();
                if ("s".equalsIgnoreCase(input)) {
                    randomWalk.requestStop();
                    break;
                }
            }
            if (walkFuture.isDone()) {  // 检查游走是否已结束
                break;
            }
            try {
                Thread.sleep(100);  // 避免过于频繁的轮询
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        executor.shutdownNow();  // 关闭线程池并中断所有正在执行的任务

    }

}