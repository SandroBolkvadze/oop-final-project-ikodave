package com.example.submission.runners;

import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.Command;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DockerCodeRunner implements CodeRunner {

    private ExecutorService pool;

    private static final String IMAGE = "openjdk:21-slim";

    private static final String WORKDIR_PREFIX = "Runner";

    private static final String JAVA_FILE_NAME = "Solution.java";

    private static final long COMPILE_TIMEOUT_MILLIS = 5000;

    private String getOutput(String solutionCode, String input, long executionTimeoutMillis) {
        try {
            Path workDir = Files.createTempDirectory(WORKDIR_PREFIX + "-");
            Files.writeString(workDir.resolve(JAVA_FILE_NAME), solutionCode);
            compileUserCode(workDir);
            String output = executeUserCode(workDir, input, executionTimeoutMillis);
            cleanUp(workDir);
            return output;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void compileUserCode(Path workDir) throws IOException, InterruptedException {
        List<String> command = List.of(
                "docker",
                "run",
                "--rm",
                "-v", workDir + ":/app",
                "-w", "/app",
                IMAGE,
                "javac", "Solution.java"
        );

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        boolean finished = process.waitFor(COMPILE_TIMEOUT_MILLIS, TimeUnit.SECONDS);

        if (!finished) {
            System.out.println("error compiling");
            return;
        }

        int code = process.exitValue();

        if (code == 0) {
            System.out.println("success compiling");
        } else {
            String out = new String(process.getInputStream().readAllBytes());
            System.out.println("error running:\n" + out);
        }
    }

    private String executeUserCode(Path workDir, String input, long executeTimeoutMillis) throws IOException, InterruptedException {
        List<String> command = List.of(
                "docker",
                "run",
                "-i",
                "--rm",
                "--read-only",
                "--network=none",
                "--memory=256m",
                "--cpus=0.5",
                "--cap-drop=ALL",
                "--cap-add=DAC_READ_SEARCH",
                "--security-opt", "no-new-privileges",
                "-v", workDir + ":/app:ro",
                "-w", "/app",
                IMAGE,
                "java", "-cp", ".", "Solution"
        );

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        try (OutputStream os = process.getOutputStream()) {
            os.write(input.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        boolean finished = process.waitFor(2, TimeUnit.SECONDS);

        if (!finished) {
            System.out.println("time limit exceeded");
            return "";
        }

        int code = process.exitValue();

        if (code == 0) {
            System.out.println("success Running");
        } else {
            String out = new String(process.getInputStream().readAllBytes());
            System.out.println("error running:\n" + out);
            return "";
        }

        return readInputStream(process.getInputStream());
    }

    private String readInputStream(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();
            try {
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    output.append(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return output.toString();
    }

    private void cleanUp(Path workDir) {
        try {
            FileUtils.deleteDirectory(workDir.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean testCodeMultipleTests(String solutionCode, long timeoutMillis, List<TestCase> testcases) {
        for (int i = 0; i < testcases.size(); i++) {
            if (!testCodeSingleTest(solutionCode, timeoutMillis, testcases.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean testCodeSingleTest(String solutionCode, long timeoutMillis, TestCase test) {
        String output = getOutput(solutionCode, test.getProblemInput(), timeoutMillis);
        System.out.println(output);
        return test.checkOutput(output);
    }
}
