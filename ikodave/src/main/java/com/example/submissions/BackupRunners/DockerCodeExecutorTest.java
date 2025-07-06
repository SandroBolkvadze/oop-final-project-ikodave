//package com.example.submission.tests;
//
//import com.example.submission.CodeRunner.DockerCodeExecutor;
//import com.example.submission.DTO.TestCase;
//import com.example.submission.Utils.Language.JavaLanguage;
//import com.example.submission.Utils.TestCaseResult.TestCaseResult;
//import com.example.submission.Utils.TestCaseResult.TestCaseSuccess;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class DockerCodeExecutorTest {
//    private static DockerCodeExecutor dockerCodeExecutor;
//
//    @BeforeAll
//    public static void setUp() {
//        dockerCodeExecutor = new DockerCodeExecutor();
//        dockerCodeExecutor.startContainers();
//        try {
//            System.out.println("wait docker");
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        dockerCodeExecutor.destroyContainers();
//    }
//
//    @Test
//    public void testSimpleJavaSum() throws IOException, InterruptedException {
//        String solutionCode = """
//            import java.util.*;
//            public class Solution {
//                public static void main(String[] args) {
//                    Scanner sc = new Scanner(System.in);
//                    int a = sc.nextInt();
//                    int b = sc.nextInt();
//                    System.out.println(a + b);
//                }
//            }
//            """;
//        TestCase testCase = new TestCase(1, 1, 1, "5", "2 3\n");
//        List<TestCase> testCases = List.of(testCase);
//        JavaLanguage javaLanguage = new JavaLanguage();
//        long timeout = 2000;
//        var result = dockerCodeExecutor
//            .testCodeMultipleTests(javaLanguage, solutionCode, timeout, testCases);
//        assertTrue(result.isSuccess());
//    }
//
//    @Test
//    public void testWrongAnswer() throws IOException, InterruptedException {
//        String solutionCode = """
//            public class Solution {
//                public static void main(String[] args) {
//                    System.out.println(42); // Always wrong
//                }
//            }
//            """;
//        TestCase testCase = new TestCase(2, 1, 1, "100", "");
//        List<TestCase> testCases = List.of(testCase);
//        JavaLanguage javaLanguage = new JavaLanguage();
//        long timeout = 2000;
//        var result = dockerCodeExecutor
//            .testCodeMultipleTests(javaLanguage, solutionCode, timeout, testCases);
//        assertFalse(result.isSuccess());
//        assertTrue(result.toString().contains("Wrong answer") || result.getClass().getSimpleName().contains("WrongAnswer"));
//    }
//
//    @Test
//    public void testRuntimeError() throws IOException, InterruptedException {
//        String solutionCode = """
//            public class Solution {
//                public static void main(String[] args) {
//                    int a = 1 / 0; // Division by zero
//                }
//            }
//            """;
//        TestCase testCase = new TestCase(3, 1, 1, "", "");
//        List<TestCase> testCases = List.of(testCase);
//        JavaLanguage javaLanguage = new JavaLanguage();
//        long timeout = 2000;
//        var result = dockerCodeExecutor
//            .testCodeMultipleTests(javaLanguage, solutionCode, timeout, testCases);
//        assertFalse(result.isSuccess());
//        assertTrue(result.toString().toLowerCase().contains("runtime") || result.getClass().getSimpleName().toLowerCase().contains("runtime"));
//    }
//
//    @Test
//    public void testTimeLimitExceeded() throws IOException, InterruptedException {
//        String solutionCode = """
//            public class Solution {
//                public static void main(String[] args) {
//                    while (true) {} // Infinite loop
//                }
//            }
//            """;
//        TestCase testCase = new TestCase(4, 1, 1, "", "");
//        List<TestCase> testCases = List.of(testCase);
//        JavaLanguage javaLanguage = new JavaLanguage();
//        long timeout = 1000;
//        var result = dockerCodeExecutor
//            .testCodeMultipleTests(javaLanguage, solutionCode, timeout, testCases);
//        assertFalse(result.isSuccess());
//        assertTrue(result.toString().toLowerCase().contains("time limit") || result.getClass().getSimpleName().toLowerCase().contains("time"));
//    }
//
//    @Test
//    public void testParallelSubmissions() throws InterruptedException {
//        int numThreads = 10;
//        CountDownLatch latch = new CountDownLatch(numThreads);
//        AtomicInteger successCount = new AtomicInteger(0);
//        for (int i = 0; i < numThreads; i++) {
//            new Thread(() -> {
//                try {
//                    String solutionCode = """
//                        import java.util.*;
//                        public class Solution {
//                            public static void main(String[] args) {
//                                Scanner sc = new Scanner(System.in);
//                                int a = sc.nextInt();
//                                int b = sc.nextInt();
//                                System.out.println(a + b);
//                            }
//                        }
//                        """;
//                    TestCase testCase = new TestCase(1, 1, 1, "5", "2 3\n");
//                    List<TestCase> testCases = List.of(testCase);
//                    JavaLanguage javaLanguage = new JavaLanguage();
//                    long timeout = 2000;
//                    var result = dockerCodeExecutor
//                        .testCodeMultipleTests(javaLanguage, solutionCode, timeout, testCases);
//                    if (result.isSuccess()) {
//                        successCount.incrementAndGet();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    latch.countDown();
//                }
//            }).start();
//        }
//        latch.await();
//        assertEquals(numThreads, successCount.get(), "All parallel submissions should succeed");
//    }
//}