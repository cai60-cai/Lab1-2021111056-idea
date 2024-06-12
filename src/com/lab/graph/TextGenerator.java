package com.lab.graph;

import com.lab.graph.*;
import com.lab.processing.TextFileProcessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TextGenerator {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Ĭ���ļ�·��
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
//        System.out.println("������һ���ı������򽫸����ŽӴ��������ı�:");

        // ��ȡ����
        String inputText = reader.readLine();

        // ��������Ƿ�Ϊ��
        if (inputText == null || inputText.trim().isEmpty()) {
            System.out.println("���벻��Ϊ�գ���������Ч���ı���");
            return;
        }

        // ��������Ƿ�������ı��ַ�
        // ʹ��������ʽ��ƥ���Ƿ��������ĸ��ո���ַ�
        if (!inputText.matches("[a-zA-Z\\s]+")) {
            System.out.println("������Ч����������Ч���ı���ֻ������ĸ�Ϳո񣩡�");
            return;
        }

        // ������תΪСд�����Ϊ����
        String[] words = inputText.toLowerCase().split("\\s+");
        StringBuilder newText = new StringBuilder();

        // �����µ��ı�
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

//        System.out.println("���ɵ����ı�:");
        System.out.println(newText.toString());
    }


}
