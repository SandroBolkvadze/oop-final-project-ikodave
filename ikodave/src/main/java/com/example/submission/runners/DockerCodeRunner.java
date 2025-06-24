package com.example.submission.runners;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class DockerCodeRunner {

    private static final String userCode = """
        import java.util.List;
        public class Solution {
            public static int solve(List<Integer> nums) {
                int sum = 0;
                for (int n : nums) sum += n;
                return sum;
            }
        }
        """;

    private static final String testRunnerCode = """
            import java.util.*;
            public class TestRunner {
                public static void main(String[] args) {
                    List<Integer> input = Arrays.stream(args[0].split(","))                                               .map(Integer::parseInt)
                                               .toList();
                    System.out.println(Solution.solve(input));
                }
            }
        """;


    private static final Path WORKDIR = Paths.get(
            System.getProperty("java.io.tmpdir"), "runner");

    private final String IMAGE = "openjdk:21-slim";

    public void runDockerProcess(String userCode, String testRunnerCode) throws IOException, InterruptedException {
        Files.createDirectories(WORKDIR);
        Files.writeString(WORKDIR.resolve("Solution.java"), userCode);
        Files.writeString(WORKDIR.resolve("TestRunner.java"), testRunnerCode);

        compileDocker();
        runDocker("1,2,3", 2);
        FileUtils.deleteDirectory(WORKDIR.toFile());
    }

    private void compileDocker() throws IOException, InterruptedException {
        List<String> cmd = List.of(
                "docker",
                "run",
                "--rm",
                "-v", WORKDIR + ":/app",
                "-w", "/app",
                IMAGE,
                "javac", "Solution.java", "TestRunner.java"
        );

        Process process = new ProcessBuilder(cmd)
                .redirectErrorStream(true)
                .start();

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        int code = process.exitValue();

        if (!finished) {
            System.err.println("Compilation timed out");
        } else if (code == 0) {
            System.out.println("success compiling");
        } else {
            String out = new String(process.getInputStream().readAllBytes());
            System.err.println("error compiling:\n" + out);
        }
    }

    private void runDocker(String input, long timeoutMillis) throws IOException, InterruptedException {
        List<String> cmd = List.of(
                "docker",
                "run",
                "--rm",
                "--read-only",
                "--network=none",
                "--memory=256m",
                "--cpus=0.5",
                "--cap-drop=ALL",
                "--security-opt", "no-new-privileges",
                "-v", WORKDIR + ":/app:ro",
                "-w", "/app",
                IMAGE,
                "java", "-cp", ".", "TestRunner", input
        );

        Process process = new ProcessBuilder(cmd)
                .redirectErrorStream(true)
                .start();

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        int code = process.exitValue();

        if (!finished) {
            System.err.println("Running timed out");
        } else if (code == 0) {
            System.out.println("success Running");
        } else {
            String out = new String(process.getInputStream().readAllBytes());
            System.err.println("error running:\n" + out);
        }

        getRunResult(process.getInputStream());
    }

    private void getRunResult(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder current = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            current.append(line);
        }
        System.out.println(current);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DockerCodeRunner dockerCodeRunner = new DockerCodeRunner();
        dockerCodeRunner.runDockerProcess(userCode, testRunnerCode);

    }

}
