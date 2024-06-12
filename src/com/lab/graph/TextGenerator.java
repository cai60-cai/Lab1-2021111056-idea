package com.lab.graph;

import com.lab.graph.*;
import com.lab.processing.TextFileProcessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TextGenerator {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 默认文件路径
        String filePath = "test.txt";

        Graph graph = TextFileProcessor.processFileToGraph(filePath);
        boolean running = true;

        try {
            generateNewText(reader, graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateNewText(BufferedReader reader, Graph graph) throws IOException {
//        System.out.println("请输入一行文本，程序将根据桥接词生成新文本:");

        // 读取输入
        String inputText = reader.readLine();

        // 检查输入是否为空
        if (inputText == null || inputText.trim().isEmpty()) {
            System.out.println("输入不能为空，请输入有效的文本。");
            return;
        }

        // 检查输入是否包含非文本字符
        // 使用正则表达式来匹配是否包含非字母或空格的字符
        if (!inputText.matches("[a-zA-Z\\s]+")) {
            System.out.println("输入无效，请输入有效的文本（只允许字母和空格）。");
            return;
        }

        // 将输入转为小写并拆分为单词
        String[] words = inputText.toLowerCase().split("\\s+");
        StringBuilder newText = new StringBuilder();

        // 生成新的文本
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

//        System.out.println("生成的新文本:");
        System.out.println(newText.toString());
    }


}
