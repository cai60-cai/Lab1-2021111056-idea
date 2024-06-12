package com.lab.graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;


import com.lab.graph.*;
import com.lab.processing.TextFileProcessor;
import java.io.BufferedReader;

import static org.junit.Assert.assertEquals;

public class TextGeneratorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testGenerateNewText_case1() throws IOException {
        String inputText = "seek to hkj hjkyiu new worlds out new life";
        String expectedOutput = "seek to hkj hjkyiu new worlds out new life";

        ByteArrayInputStream in = new ByteArrayInputStream(inputText.getBytes());
        System.setIn(in);

        TextGenerator.main(new String[]{});

        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testGenerateNewText_case2() throws IOException {
        String inputText = "seek to explore new and exciting synergies";
        String expectedOutput = "seek to explore strange new life and exciting synergies";
        ByteArrayInputStream in = new ByteArrayInputStream(inputText.getBytes());
        System.setIn(in);

        TextGenerator.main(new String[]{});

        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testGenerateNewText_case3() throws IOException {
        String inputText = "new to out life and";
        String[] expectedOutputs = {
                "new civilizations to seek out new life and",
                "new worlds to seek out new life and"
        };

        ByteArrayInputStream in = new ByteArrayInputStream(inputText.getBytes());
        System.setIn(in);

        TextGenerator.main(new String[]{});

        String output = outContent.toString().trim();
        boolean matches = output.equals(expectedOutputs[0]) || output.equals(expectedOutputs[1]);
        assertEquals(true, matches);
    }

    @Test
    public void testGenerateNewText_case4() throws IOException {
        String inputText = "";
        String expectedOutput = "输入不能为空，请输入有效的文本。";

        ByteArrayInputStream in = new ByteArrayInputStream(inputText.getBytes());
        System.setIn(in);

        TextGenerator.main(new String[]{});

        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testGenerateNewText_case5() throws IOException {
        String inputText = "@@#%￥";
        String expectedOutput = "输入无效，请输入有效的文本（只允许字母和空格）。";

        ByteArrayInputStream in = new ByteArrayInputStream(inputText.getBytes());
        System.setIn(in);

        TextGenerator.main(new String[]{});

        assertEquals(expectedOutput, outContent.toString().trim());
    }
}
