package com.example.submission.tests;

import com.example.submission.DTO.TestCase;
import com.example.submission.runners.DockerCodeRunner;
import com.example.submission.runners.InMemoryCodeRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CodeRunnerTest {

    private DockerCodeRunner dockerCodeRunner;

    @BeforeEach
    public void setUp() {
        dockerCodeRunner = new DockerCodeRunner();
    }

    @Test
    public void testBasicArraySum() throws IOException, InterruptedException {
        String solutionCode = """
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    int sum = 0;
                    for (int i = 0; i < n; i++) {
                        sum += sc.nextInt();
                    }
                    System.out.println(sum);
                }
            }
            """;
        
        TestCase testCase = new TestCase(1, 1, 1, "6", "3\n1 2 3\n");
        boolean result = dockerCodeRunner.testCodeSingleTest(solutionCode, 5000, testCase);
        assertTrue(result);
    }

    @Test
    public void testEdgeCaseZeroArray() throws IOException, InterruptedException {
        String solutionCode = """
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    int sum = 0;
                    for (int i = 0; i < n; i++) {
                        sum += sc.nextInt();
                    }
                    System.out.println(sum);
                }
            }
            """;
        
        TestCase testCase = new TestCase(2, 1, 2, "0", "0\n");
        boolean result = dockerCodeRunner.testCodeSingleTest(solutionCode, 5000, testCase);
        assertTrue(result, "Empty array test should pass");
    }

    @Test
    public void testComplexInputWithNegativeNumbers() throws IOException, InterruptedException {
        String solutionCode = """
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    int sum = 0;
                    for (int i = 0; i < n; i++) {
                        sum += sc.nextInt();
                    }
                    System.out.println(sum);
                }
            }
            """;
        
        TestCase testCase = new TestCase(3, 1, 3, "-3", "6\n0 -1 2 -3 4 -5\n");
        boolean result = dockerCodeRunner.testCodeSingleTest(solutionCode, 5000, testCase);
        assertTrue(result, "Negative numbers test should pass");
    }


    @Test
    public void testFindMaximumElementMultipleThreads() throws IOException, InterruptedException {
        String solutionCode = """
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    int max = Integer.MIN_VALUE;
                    for (int i = 0; i < n; i++) {
                        int num = sc.nextInt();
                        if (num > max) {
                            max = num;
                        }
                    }
                    System.out.println(max);
                }
            }
            """;

        int numThreads = 5;

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                TestCase testCase = new TestCase(4, 1, 4, "42", "5\n10 5 42 8 15\n");
                boolean result = dockerCodeRunner.testCodeSingleTest(solutionCode, 5000, testCase);
                System.out.println(result ? "True" : "False");
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }


}
