package com.example.submission.runners;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class DockerCodeRunner {

    private static final String solutionCode = """
            import java.util.*;
            public class Solution {
                public static void main(String[] args) {
                    Scanner sc = new Scanner(System.in);
                    int n = sc.nextInt(); // Read the length of the array
                    int sum = 0;
                    for (int i = 0; i < n; i++) {
                        sum += sc.nextInt(); // Read each number and add to sum
                    }
                    System.out.println(sum); // Output the sum
                }
            }
        """;

    private Path WORKDIR;

    private final String IMAGE = "openjdk:21-slim";

    public void runDockerProcess(String solutionCode) throws IOException, InterruptedException {
        WORKDIR = Files.createTempDirectory("runner-");
        Files.writeString(WORKDIR.resolve("Solution.java"), solutionCode);

        compileDocker();
        runDocker("3\n1 2 3\n", 2);
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
                "javac", "Solution.java"
        );

        Process process = new ProcessBuilder(cmd)
                .redirectErrorStream(true)
                .start();

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);

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

    private void runDocker(String input, long timeoutMillis) throws IOException, InterruptedException {
        List<String> cmd = List.of(
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
                "-v", WORKDIR + ":/app:ro",
                "-w", "/app",
                IMAGE,
                "java", "-cp", ".", "Solution"
        );

        Process process = new ProcessBuilder(cmd)
                .redirectErrorStream(true)
                .start();

        try (OutputStream os = process.getOutputStream()) {
            os.write(input.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        boolean finished = process.waitFor(2, TimeUnit.SECONDS);

        if (!finished) {
            System.out.println("time limit exceeded");
            return;
        }

        int code = process.exitValue();

        if (code == 0) {
            System.out.println("success Running");
        } else {
            String out = new String(process.getInputStream().readAllBytes());
            System.out.println("error running:\n" + out);
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
        dockerCodeRunner.runDockerProcess(solutionCode);
    }

}
