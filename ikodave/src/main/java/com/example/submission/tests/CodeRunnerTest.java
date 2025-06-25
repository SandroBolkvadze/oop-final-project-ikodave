package com.example.submission.tests;

import com.example.submission.DTO.TestCase;
import com.example.submission.runners.DockerCodeRunner;
import com.example.submission.Utils.SubmissionResult.SubmissionResult;
import com.example.submission.Utils.SubmissionResult.SubmissionSuccess;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class CodeRunnerTest {

    private static DockerCodeRunner dockerCodeRunner;

    @BeforeAll
    public static void setUp() {
        dockerCodeRunner = new DockerCodeRunner();
        dockerCodeRunner.startContainers();
        System.out.println("docker initialized!!!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        dockerCodeRunner.destroyContainers();
    }

    @Test
    public void testBasicArraySum() throws IOException, InterruptedException {
        System.out.println("start test basic");
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
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "6", "3\n1 2 3\n"));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(solutionCode, 2000, testCases);
        assertTrue(result.isSuccess());
        assertInstanceOf(SubmissionSuccess.class, result);
        System.out.println("end test basic");
    }

    @Test
    public void testCompileError() throws IOException, InterruptedException {
        System.out.println("start compile error test");
        String solutionCode = "public class Solution { public static void main(String[] args) { int x = ; } }"; // Syntax error
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "0", "1\n0\n"));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(solutionCode, 2000, testCases);
        assertFalse(result.isSuccess());
        System.out.println("end compile error test");
    }

    @Test
    public void testMultipleSubmissionsWithThreads() throws InterruptedException {
        System.out.println("start threads test");
        int numberOfSubmissions = 10;
        AtomicInteger successfulSubmissions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();

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
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "6", "3\n1 2 3\n"));

        for (int i = 0; i < numberOfSubmissions; i++) {
            Thread thread = new Thread(() -> {
                try {
                    SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(solutionCode, 2000, testCases);
                    if (result.isSuccess()) {
                        successfulSubmissions.incrementAndGet();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(numberOfSubmissions, successfulSubmissions.get());
        System.out.println("end threads test");
    }

    @Test
    public void testMultipleUsersDifferentProblems() throws InterruptedException {
        System.out.println("start different codes");
        List<String> solutionCodes = new ArrayList<>();
        List<List<TestCase>> testCasesList = new ArrayList<>();

        // Submission 1: Array Sum
        solutionCodes.add("""
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
            """);
        testCasesList.add(List.of(new TestCase(1, 1, 1, "6", "3\n1 2 3\n"),
                new TestCase(2, 2, 2, "15", "5\n1 2 3 4 5\n")));

        // Submission 2: Find Max
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    int max = Integer.MIN_VALUE;
                    if (n > 0) {
                        for (int i = 0; i < n; i++) {
                            int current = sc.nextInt();
                            if (current > max) {
                                max = current;
                            }
                        }
                    }
                    System.out.println(max);
                }
            }
            """);
        testCasesList.add(List.of(new TestCase(2, 1, 1, "99", "5\n1 5 99 2 4\n"),
                new TestCase(6, 2, 1, "10", "5\n-5 -1 10 0 4\n")));

        // Submission 3: Echo String
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();
                    System.out.println(line);
                }
            }
            """);
        testCasesList.add(List.of(new TestCase(3, 1, 1, "hello world", "hello world\n"),
                new TestCase(7, 2, 1, "another test", "another test\n")));

        // Submission 4: Simple Addition
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int a = sc.nextInt();
                    int b = sc.nextInt();
                    System.out.println(a + b);
                }
            }
            """);
        testCasesList.add(List.of(new TestCase(4, 1, 1, "5", "2 3\n"),
                new TestCase(8, 2, 1, "-1", "5 -6\n")));

        // Submission 5: Hello World
        solutionCodes.add("""
            public class Solution {
                public static void main(String[] args) {
                    System.out.println("Hello World!");
                }
            }
            """);
        testCasesList.add(List.of(new TestCase(5, 1, 1, "Hello World!", ""),
                new TestCase(9, 2, 1, "Hello World!", "dummy input")));

        AtomicInteger successfulSubmissions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < solutionCodes.size(); i++) {
            final int index = i;
            Thread thread = new Thread(() -> {
                try {
                    String solutionCode = solutionCodes.get(index);
                    List<TestCase> testCases = testCasesList.get(index);
                    SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(solutionCode, 2000, testCases);
                    if (result.isSuccess()) {
                        successfulSubmissions.incrementAndGet();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(solutionCodes.size(), successfulSubmissions.get());
        System.out.println("end different codes");
    }
}
