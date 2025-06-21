package com.example.submission.tests;

import com.example.submission.runners.CodeRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeRunnerTest {


    private static final String SOURCE_CODE_EASY = """
            package user;
                public class Solution {
                    public static int solve(int a, int b) {
                        return a + b;
                    }
                }
            """;

    private static final String SOURCE_CODE_HARD = """
                package user;
            
                import java.util.Arrays;
            
                public class Solution {
                    /**
                     * Returns the length of the longest strictly increasing subsequence in nums.
                     *
                     * Example:
                     *  nums = [10,9,2,5,3,7,101,18]
                     *  LIS = [2,3,7,101] → returns 4
                     */
                    public int solve(int[] nums) {
                        int n = nums.length;
                        if (n == 0) return 0;
            
                        // dp[i] = length of LIS ending at i
                        int[] dp = new int[n];
                        Arrays.fill(dp, 1);
            
                        int maxLen = 1;
                        for (int i = 1; i < n; i++) {
                            for (int j = 0; j < i; j++) {
                                if (nums[j] < nums[i]) {
                                    dp[i] = Math.max(dp[i], dp[j] + 1);
                                }
                            }
                            maxLen = Math.max(maxLen, dp[i]);
                        }
            
                        return maxLen;
                    }
                }
            """;

    private CodeRunner codeRunner;

    @BeforeEach
    public void setUp() {
        codeRunner = new CodeRunner();
    }

    @Test
    public void SingleCodeTestEasy() {
        Object[] args = new Object[] {1, 2};
        Class<?>[] paramTypes = new Class<?>[] {int.class, int.class};
        int expected = 3;
        long timeoutMillis = 100;

        try {
            Object actual = codeRunner.runWithTimeout(
                    "user.Solution",
                    SOURCE_CODE_EASY,
                    "solve",
                    args,
                    paramTypes,
                    timeoutMillis
             );

            assertEquals(expected, (Integer) actual);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void SingleCodeTestHard() {
        Object[] args = new Object[] {new int[] {10, 9, 2, 5, 3, 7, 101, 18}};
        Class<?>[] paramTypes = new Class<?>[] {int[].class};
        int expected = 4;
        long timeoutMillis = 100;

        try {
            Object actual = codeRunner.runWithTimeout(
                    "user.Solution",
                    SOURCE_CODE_HARD,
                    "solve",
                    args,
                    paramTypes,
                    timeoutMillis
            );

            assertEquals(expected, (Integer) actual);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void MultipleCodeTest() {
        int numIters = 20;
        Random random = new Random();
        for (int i = 0; i < numIters; i++) {
            int x = random.nextInt(), y = random.nextInt();
            Object[] args = new Object[] {x, y};
            Class<?>[] paramTypes = new Class<?>[] {int.class, int.class};
            int expected = x + y;
            long timeoutMillis = 1000;

            try {
                Object actual = codeRunner.runWithTimeout(
                        "user.Solution",
                        SOURCE_CODE_EASY,
                        "solve",
                        args,
                        paramTypes,
                        timeoutMillis
                );

                assertEquals(expected, (Integer) actual);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
