package com.example.submission.tests;

import com.example.submission.runners.CodeRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeRunnerTest {

    private CodeRunner codeRunner;

    @BeforeEach
    public void setUp() {
        codeRunner = new CodeRunner();
    }

    @Test
    public void BasicCodeTest() {
        String sourceCode = """
            package user;
                public class Solution {
                    public static int solve(int a, int b) {
                        return a + b;
                    }
                }
            """;

        try {
            Object answer = codeRunner.runWithTimeout(
                    "user.Solution",
                    sourceCode,
                    "solve",
                    new Object[] {1, 2},
                    new Class<?>[] {int.class, int.class},
                    1000
             );

            assertEquals(3, (Integer) answer);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
