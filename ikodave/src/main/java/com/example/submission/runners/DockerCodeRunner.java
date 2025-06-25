package com.example.submission.runners;

import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.Container;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

public class DockerCodeRunner implements CodeRunner {

    private BlockingQueue<Container> containersPool;

    private static final int NUM_CONTAINERS = 5;

    private static final String JAVA_FILE_NAME = "Solution.java";

    private static final String WORKDIR_PREFIX = "Runner";

    private static final long COMPILE_TIMEOUT_MILLIS = 5000;

    public void startContainers() {
        containersPool = new ArrayBlockingQueue<>(NUM_CONTAINERS);
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            try {
                Path workDir = Files.createTempDirectory(WORKDIR_PREFIX + "-");
//                Path workDir = Files.createDirectories(Path.of("C:\\Users\\User\\Desktop\\runner" + UUID.randomUUID()));
                Container container = new Container(workDir);
                container.startContainer();
                containersPool.put(container);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void destroyContainers() {
        for (int i = 0; i < NUM_CONTAINERS; i++) {
            try {
                Container container = containersPool.take();
                container.destroyContainer();
                FileUtils.deleteDirectory(container.getWorkDir().toFile());
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean executeAllTestCases(String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        Container container = containersPool.take();
        Files.writeString(container.getWorkDir().resolve(JAVA_FILE_NAME), solutionCode);
        compileUserCode(container.getContainerName());

        for (TestCase testCase : testCases) {
            if (!executeUserCode(container.getContainerName(), executionTimeoutMillis, testCase)) {
                container.cleanContainer();
                containersPool.put(container);
                return false;
            }
        }

        container.cleanContainer();
        containersPool.put(container);
        return true;
    }


    private void compileUserCode(String containerName) throws IOException, InterruptedException {
        List<String> command = List.of(
                "docker", "exec", containerName,
                "javac", JAVA_FILE_NAME
        );

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start();

        ProcessHandle processHandle = process.toHandle();

        boolean finished = process.waitFor(COMPILE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        if (!finished || process.exitValue() != 0) {
            System.out.println("error compiling");
        }
        else {

            ProcessHandle.Info info = processHandle.info();
            Optional<Duration> cpuDuration = info.totalCpuDuration();
            long cpuTimeMillis = cpuDuration.orElse(Duration.ZERO).toMillis();

            System.out.println("success Compile, execution time " + cpuTimeMillis);
            System.out.println("success compiling");
        }
    }


    private boolean executeUserCode(String containerName, long executeTimeoutMillis, TestCase testCase) throws IOException, InterruptedException {
        List<String> command = List.of(
                "docker", "exec", "-i", containerName,
                "java", "-cp", "/app", "Solution"
        );

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start();

        ProcessHandle processHandle = process.toHandle();

        try (OutputStream os = process.getOutputStream()) {
            os.write(testCase.getProblemInput().getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        boolean finished = process.waitFor(executeTimeoutMillis + 2000, TimeUnit.MILLISECONDS);

        if (!finished) {
            System.out.println("time limit exceeded");
            return false;
        }

        if (process.exitValue() != 0) {
            System.out.println("error running");
            return false;
        }

        ProcessHandle.Info info = processHandle.info();
        Optional<Duration> cpuDuration = info.totalCpuDuration();
        long cpuTimeMillis = cpuDuration.orElse(Duration.ZERO).toMillis();

        System.out.println("success Running, execution time " + cpuTimeMillis);
        if (cpuTimeMillis > executeTimeoutMillis) {
            System.out.println("time limit exceeded");
            return false;
        }
        String output = readInputStream(process.getInputStream());
        System.out.println("output: " + output);
        return output.equals(testCase.getProblemOutput());
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


    @Override
    public boolean testCodeMultipleTests(String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        return executeAllTestCases(solutionCode, executionTimeoutMillis, testCases);
    }

}
