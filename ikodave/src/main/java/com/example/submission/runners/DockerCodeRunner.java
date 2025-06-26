package com.example.submission.runners;

import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.CompileResult.CompileErrorResult;
import com.example.submission.Utils.CompileResult.CompileResult;
import com.example.submission.Utils.CompileResult.CompileSuccessResult;
import com.example.submission.Utils.Container;
import com.example.submission.Utils.SubmissionResult.*;
import com.example.submission.Utils.TestCaseResult.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class DockerCodeRunner implements CodeRunner {

    private static final int NUM_CONTAINERS = 5;

    private static final String JAVA_FILE_NAME = "Solution.java";

    private static final String WORKDIR_PREFIX = "Runner";

    private static final long COMPILE_TIMEOUT_MILLIS = 5000;

    private BlockingQueue<Container> containersPool;

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

    private SubmissionResult executeAllTestCases(String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        Container container = containersPool.take();
        Files.writeString(container.getWorkDir().resolve(JAVA_FILE_NAME), solutionCode);
        try {
            CompileResult compileResult = compileUserCode(container.getContainerName());
            if (!compileResult.isSuccess()) {
                return compileResult;
            }

            for (TestCase testCase : testCases) {
                TestCaseResult testCaseResult = executeUserCode(container.getContainerName(), executionTimeoutMillis, testCase);
                if (!testCaseResult.isSuccess()) {
                    return testCaseResult;
                }
            }

            return new SubmissionSuccess();
        } finally {
            container.cleanContainer();
            containersPool.put(container);
        }
    }


    private CompileResult compileUserCode(String containerName) throws InterruptedException, IOException {
        List<String> command = List.of(
                "docker", "exec", containerName,
                "javac", JAVA_FILE_NAME
        );

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start();

//        ProcessHandle processHandle = process.toHandle();

        boolean finished = process.waitFor(COMPILE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        if (!finished || process.exitValue() != 0) {
            return new CompileErrorResult(readInputStream(process.getErrorStream()));
        }
        else {
//            ProcessHandle.Info info = processHandle.info();
//            Optional<Duration> cpuDuration = info.totalCpuDuration();
//            long cpuTimeMillis = cpuDuration.orElse(Duration.ZERO).toMillis();
//            System.out.println("success Compile, execution time " + cpuTimeMillis);
//            System.out.println("success compiling");
            return new CompileSuccessResult();
        }
    }


    private TestCaseResult executeUserCode(String containerName, long executeTimeoutMillis, TestCase testCase) throws IOException, InterruptedException {
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

        boolean finished = process.waitFor(executeTimeoutMillis, TimeUnit.MILLISECONDS);

        if (!finished) {
            return new TestCaseTimeLimitExceeded(testCase.getOrderNum(), executeTimeoutMillis, "Time limit exceeded");
        }

        if (process.exitValue() != 0) {
            return new TestCaseRuntimeError(testCase.getOrderNum(), readInputStream(process.getErrorStream()));
        }

        ProcessHandle.Info info = processHandle.info();
        Optional<Duration> cpuDuration = info.totalCpuDuration();
        long cpuTimeMillis = cpuDuration.orElse(Duration.ZERO).toMillis();

        if (cpuTimeMillis > executeTimeoutMillis) {
            return new TestCaseTimeLimitExceeded(testCase.getOrderNum(), executeTimeoutMillis, "Time limit exceeded");
        }
        String output = readInputStream(process.getInputStream());

        if (output.equals(testCase.getProblemOutput())) {
            return new TestCaseSuccess(testCase.getOrderNum(), cpuTimeMillis);
        }
        else {
            return new TestCaseWrongAnswer(testCase.getOrderNum(), testCase.getProblemOutput(), output, "Wrong answer");
        }
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
    public SubmissionResult testCodeMultipleTests(String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        return executeAllTestCases(solutionCode, executionTimeoutMillis, testCases);
    }

}
