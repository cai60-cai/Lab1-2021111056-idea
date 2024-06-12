package com.lab.graph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class GraphTest {
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
    public void testcase1() {
        String input = "aaa bbb\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 运行 main 方法
        Graph.main(new String[]{});

        String expected = "Error: Neither 'aaa' nor 'bbb' is in the graph.";
        String actual = outContent.toString().trim();
        assertEquals(expected, actual);

    }
    @Test
    public void testcase2() {
        String input = "aaa new\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 运行 main 方法
        Graph.main(new String[]{});

        String expected = "Error: 'aaa' is not in the graph.";
        String actual = outContent.toString().trim();
        assertEquals(expected, actual);

    }
    @Test
    public void testcase3() {
        String input = "new bbb\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 运行 main 方法
        Graph.main(new String[]{});

        String expected = "Error: 'bbb' is not in the graph.";
        String actual = outContent.toString().trim();
        assertEquals(expected, actual);

    }
    @Test
    public void testcase4() {
        String input = "tyu new\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 运行 main 方法
        Graph.main(new String[]{});

        String expected = "No bridge words from \"tyu\" to \"new\"!";
        String actual = outContent.toString().trim();
        assertEquals(expected, actual);

    }
    @Test
    public void testcase5() {
        String input = "tyu worlds\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 运行 main 方法
        Graph.main(new String[]{});

        String expected = "No bridge words from \"tyu\" to \"worlds\"!";
        String actual = outContent.toString().trim();
        assertEquals(expected, actual);

    }
    @Test
    public void testcase6() {
        String input = "to seek\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 运行 main 方法
        Graph.main(new String[]{});

        String expected = "No bridge words from \"to\" to \"seek\"!";
        String actual = outContent.toString().trim();
        assertEquals(expected, actual);

    }
    @Test
    public void testcase7() {
        String input = "new to\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 运行 main 方法
        Graph.main(new String[]{});

        String expected = "The bridge words from \"new\" to \"to\" are: worlds, civilizations";
        String actual = outContent.toString().trim();
        assertEquals(expected, actual);

    }

}
