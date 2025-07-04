package com.example.submission.tests;

import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.TestCaseResult.TestCaseRuntimeError;
import com.example.submission.Utils.TestCaseResult.TestCaseTimeLimitExceeded;
import com.example.submission.Utils.TestCaseResult.TestCaseWrongAnswer;
import com.example.submission.runners.DockerCodeRunner;
import com.example.submission.Utils.SubmissionResult.SubmissionResult;
import com.example.submission.Utils.SubmissionResult.SubmissionSuccess;
import com.example.submission.Utils.Language.CodeLanguage;
import com.example.submission.Utils.Language.CppLanguage;
import com.example.submission.Utils.Language.JavaLanguage;
import com.example.submission.Utils.Language.PythonLanguage;
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
            Thread.sleep(10000);
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
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new JavaLanguage(), solutionCode, 2000, testCases);
        assertTrue(result.isSuccess());
        assertInstanceOf(SubmissionSuccess.class, result);
        System.out.println("end test basic");
    }

    @Test
    public void testCompileError() throws IOException, InterruptedException {
        System.out.println("start compile error test");
        String solutionCode = "public class Solution { public static void main(String[] args) { int x = ; } }"; // Syntax error
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "0", "1\n0\n"));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new JavaLanguage(), solutionCode, 2000, testCases);
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
                    SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new JavaLanguage(), solutionCode, 2000, testCases);
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

        // Submission 1: Array Sum (intentionally incorrect)
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    //Scanner sc = new Scanner(System.in);
                    //int n = sc.nextInt();
                    int sum = 0;
                    for (int i = 0; i < n; i++) {
                        //sum += sc.nextInt();
                    }
                    System.out.println(0);
                }
            }
            """);
        testCasesList.add(List.of(
                new TestCase(1, 1, 1, "6", "3\n1 2 3\n"),
                new TestCase(2, 2, 2, "15", "5\n1 2 3 4 5\n"),
                new TestCase(3, 3, 3, "0", "0\n\n"),
                new TestCase(4, 4, 4, "-6", "3\n-1 -2 -3\n"),
                new TestCase(5, 5, 5, "100", "1\n100\n"),
                new TestCase(6, 6, 6, "10", "4\n1 2 3 4\n"),
                new TestCase(7, 7, 7, "-10", "2\n-5 -5\n"),
                new TestCase(8, 8, 8, "1", "1\n1\n"),
                new TestCase(9, 9, 9, "55", "10\n1 2 3 4 5 6 7 8 9 10\n"),
                new TestCase(10, 10, 10, "5050", "100\n" + "1 ".repeat(99) + "1\n")
        ));

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
        testCasesList.add(List.of(
                new TestCase(2, 1, 1, "99", "5\n1 5 99 2 4\n"),
                new TestCase(6, 2, 1, "10", "5\n-5 -1 10 0 4\n"),
                new TestCase(11, 3, 1, "-1", "3\n-1 -2 -3\n"),
                new TestCase(12, 4, 1, "100", "2\n100 99\n"),
                new TestCase(13, 5, 1, "0", "1\n0\n"),
                new TestCase(14, 6, 1, "-5", "4\n-5 -5 -5 -5\n"),
                new TestCase(15, 7, 1, "7", "7\n1 2 3 4 5 6 7\n"),
                new TestCase(16, 8, 1, "1000", "10\n1000 1 2 3 4 5 6 7 8 9\n"),
                new TestCase(17, 9, 1, "-100", "3\n-100 -200 -300\n"),
                new TestCase(18, 10, 1, "5", "5\n5 4 3 2 1\n")
        ));

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
        testCasesList.add(List.of(
                new TestCase(3, 1, 1, "hello world", "hello world\n"),
                new TestCase(7, 2, 1, "another test", "another test\n"),
                new TestCase(19, 3, 1, "", "\n"),
                new TestCase(20, 4, 1, "12345", "12345\n"),
                new TestCase(21, 5, 1, "!@#$%", "!@#$%\n"),
                new TestCase(22, 6, 1, "A B C", "A B C\n"),
                new TestCase(23, 7, 1, "test", "test\n"),
                new TestCase(24, 8, 1, "long string with spaces", "long string with spaces\n"),
                new TestCase(25, 9, 1, "UPPER lower", "UPPER lower\n"),
                new TestCase(26, 10, 1, "123 456 789", "123 456 789\n")
        ));

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
        testCasesList.add(List.of(
                new TestCase(4, 1, 1, "5", "2 3\n"),
                new TestCase(8, 2, 1, "-1", "5 -6\n"),
                new TestCase(27, 3, 1, "0", "0 0\n"),
                new TestCase(28, 4, 1, "100", "50 50\n"),
                new TestCase(29, 5, 1, "-10", "-5 -5\n"),
                new TestCase(30, 6, 1, "1", "0 1\n"),
                new TestCase(31, 7, 1, "-100", "-50 -50\n"),
                new TestCase(32, 8, 1, "200", "100 100\n"),
                new TestCase(33, 9, 1, "2", "1 1\n"),
                new TestCase(34, 10, 1, "-2", "-1 -1\n")
        ));

        // Submission 5: Hello World
        solutionCodes.add("""
            public class Solution {
                public static void main(String[] args) {
                    System.out.println("Hello World!");
                }
            }
            """);
        testCasesList.add(List.of(
                new TestCase(5, 1, 1, "Hello World!", ""),
                new TestCase(9, 2, 1, "Hello World!", "dummy input"),
                new TestCase(35, 3, 1, "Hello World!", "another input"),
                new TestCase(36, 4, 1, "Hello World!", "123"),
                new TestCase(37, 5, 1, "Hello World!", "!@#"),
                new TestCase(38, 6, 1, "Hello World!", "test"),
                new TestCase(39, 7, 1, "Hello World!", " "),
                new TestCase(40, 8, 1, "Hello World!", "\n"),
                new TestCase(41, 9, 1, "Hello World!", "random"),
                new TestCase(42, 10, 1, "Hello World!", "input")
        ));

        // Submission 6: Product of Array
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    int prod = 1;
                    for (int i = 0; i < n; i++) {
                        prod *= sc.nextInt();
                    }
                    System.out.println(prod);
                }
            }
            """);
        testCasesList.add(List.of(
                new TestCase(43, 1, 1, "6", "3\n1 2 3\n"),
                new TestCase(44, 2, 1, "0", "2\n0 5\n"),
                new TestCase(45, 3, 1, "120", "5\n1 2 3 4 5\n"),
                new TestCase(46, 4, 1, "1", "0\n\n"),
                new TestCase(47, 5, 1, "-6", "3\n-1 2 3\n"),
                new TestCase(48, 6, 1, "100", "2\n10 10\n"),
                new TestCase(49, 7, 1, "1", "1\n1\n"),
                new TestCase(50, 8, 1, "-8", "3\n-2 2 2\n"),
                new TestCase(51, 9, 1, "3628800", "10\n1 2 3 4 5 6 7 8 9 10\n"),
                new TestCase(52, 10, 1, "1", "5\n1 1 1 1 1\n")
        ));

        // Submission 7: Print N times
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    for (int i = 0; i < n; i++) {
                        System.out.println("A");
                    }
                }
            }
            """);
        testCasesList.add(List.of(
                new TestCase(53, 1, 1, "A\nA\nA", "3\n"),
                new TestCase(54, 2, 1, "A", "1\n"),
                new TestCase(55, 3, 1, "A\nA", "2\n"),
                new TestCase(56, 4, 1, "", "0"),
                new TestCase(57, 5, 1, "A\nA\nA\nA\nA", "5\n")
        ));

        // Submission 8: Reverse String
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    String s = sc.nextLine();
                    System.out.println(new StringBuilder(s).reverse().toString());
                }
            }
            """);
        testCasesList.add(List.of(
                new TestCase(58, 1, 1, "dlrow olleh", "hello world\n"),
                new TestCase(59, 2, 1, "", "\n"),
                new TestCase(60, 3, 1, "cba", "abc\n"),
                new TestCase(61, 4, 1, "12345", "54321\n"),
                new TestCase(62, 5, 1, "!@#", "#@!\n")
        ));

        // Submission 9: Print Even or Odd
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    if (n % 2 == 0) {
                        System.out.println("Even");
                    } else {
                        System.out.println("Odd");
                    }
                }
            }
            """);
        testCasesList.add(List.of(
                new TestCase(63, 1, 1, "Even", "2\n"),
                new TestCase(64, 2, 1, "Odd", "3\n"),
                new TestCase(65, 3, 1, "Even", "0\n"),
                new TestCase(66, 4, 1, "Odd", "-1\n"),
                new TestCase(67, 5, 1, "Even", "100\n")
        ));

        // Submission 10: Fibonacci Nth
        solutionCodes.add("""
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt();
                    System.out.println(fib(n));
                }
                static int fib(int n) {
                    if (n <= 1) return n;
                    return fib(n-1) + fib(n-2);
                }
            }
            """);
        testCasesList.add(List.of(
                new TestCase(68, 1, 1, "0", "0\n"),
                new TestCase(69, 2, 1, "1", "1\n"),
                new TestCase(70, 3, 1, "1", "2\n"),
                new TestCase(71, 4, 1, "2", "3\n"),
                new TestCase(72, 5, 1, "3", "4\n"),
                new TestCase(73, 6, 1, "5", "5\n"),
                new TestCase(74, 7, 1, "8", "6\n"),
                new TestCase(75, 8, 1, "13", "7\n"),
                new TestCase(76, 9, 1, "21", "8\n"),
                new TestCase(77, 10, 1, "34", "9\n")
        ));

        AtomicInteger successfulSubmissions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < solutionCodes.size(); i++) {
            final int index = i;
            Thread thread = new Thread(() -> {
                try {
                    String solutionCode = solutionCodes.get(index);
                    List<TestCase> testCases = testCasesList.get(index);
                    SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new JavaLanguage(), solutionCode, 2000, testCases);
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

        // Only the first submission is intentionally incorrect, so expect 9 successes
        assertEquals(solutionCodes.size() - 1, successfulSubmissions.get());
        System.out.println("end different codes");
    }

    @Test
    public void testRuntimeError() throws IOException, InterruptedException {
        System.out.println("start runtime error test");
        String solutionCode = """
            public class Solution {
                public static void main(String[] args) {
                    int a = 1 / 0; // Division by zero
                }
            }
            """;
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "", ""));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new JavaLanguage(), solutionCode, 2000, testCases);
        assertFalse(result.isSuccess());
        assertInstanceOf(TestCaseRuntimeError.class, result);
        System.out.println("end runtime error test");
    }

    @Test
    public void testWrongAnswer() throws IOException, InterruptedException {
        System.out.println("start wrong answer test");
        String solutionCode = """
            public class Solution {
                public static void main(String[] args) {
                    System.out.println(42); // Always wrong
                }
            }
            """;
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "100", ""));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new JavaLanguage(), solutionCode, 2000, testCases);
        assertFalse(result.isSuccess());
        assertInstanceOf(TestCaseWrongAnswer.class, result);
        System.out.println("end wrong answer test");
    }

    @Test
    public void testTimeLimitExceeded() throws IOException, InterruptedException {
        System.out.println("start time limit exceeded test");
        String solutionCode = """
            public class Solution {
                public static void main(String[] args) {
                    while (true) {} // Infinite loop
                }
            }
            """;
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "", ""));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new JavaLanguage(), solutionCode, 1000, testCases);
        assertFalse(result.isSuccess());
        assertInstanceOf(TestCaseTimeLimitExceeded.class, result);
        System.out.println("end time limit exceeded test");
    }

    @Test
    public void testBasicCppSum() throws IOException, InterruptedException {
        System.out.println("start test basic cpp");
        String solutionCode = """
            #include <iostream>

            int main() {
                int a, b;
                std::cin >> a >> b;
                std::cout << ((a ^ b) + ((a & b) << 1));
                return 0;
            }
            """;
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "5", "2 3\\n"));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new CppLanguage(), solutionCode, 2000, testCases);
        assertTrue(result.isSuccess());
        assertInstanceOf(SubmissionSuccess.class, result);
        System.out.println("end test basic cpp");
    }

    @Test
    public void testBasicPythonSum() throws IOException, InterruptedException {
        System.out.println("start test basic python");
        String solutionCode = """
            def main():
                a, b = map(int, input().split())
                print(a + b)
            main()
            """;
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "5", "2 3\n"));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new PythonLanguage(), solutionCode, 2000, testCases);
        assertTrue(result.isSuccess());
        assertInstanceOf(SubmissionSuccess.class, result);
        System.out.println("end test basic python");
    }

    @Test
    public void testBasicCSum() throws IOException, InterruptedException {
        System.out.println("start test basic c");
        String solutionCode = """
            #include <stdio.h>
            int main() {
                int a, b;
                scanf("%d %d", &a, &b);
                printf("%d", a + b);
                return 0;
            }
            """;
        List<TestCase> testCases = List.of(new TestCase(1, 1, 1, "5", "2 3\n"));
        SubmissionResult result = dockerCodeRunner.testCodeMultipleTests(new com.example.submission.Utils.Language.CLanguage(), solutionCode, 2000, testCases);
        assertTrue(result.isSuccess());
        assertInstanceOf(SubmissionSuccess.class, result);
        System.out.println("end test basic c");
    }
}