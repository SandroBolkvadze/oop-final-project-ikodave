package com.example.submission.CodeRunner;

import com.example.submission.DTO.TestCase;
import com.example.submission.Utils.CompileResult.CompileErrorResult;
import com.example.submission.Utils.CompileResult.CompileResult;
import com.example.submission.Utils.CompileResult.CompileSuccessResult;
import com.example.submission.Utils.Container.Container;
import com.example.submission.Utils.Language.CodeLanguage;
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

    private static final String WORKDIR_PREFIX = "Runner";

    private static final long PROCESS_TIMEOUT_MILLIS = 5000;

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

    private SubmissionResult executeAllTestCases(CodeLanguage codeLanguage, String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        Container container = containersPool.take();
        codeLanguage.createFiles(container.getWorkDir(), solutionCode);

        try {
            CompileResult compileResult = compileUserCode(codeLanguage, container.getContainerName());
            if (!compileResult.isSuccess()) {
                System.out.println(compileResult.submissionInfo());
                return compileResult;
            }

            for (TestCase testCase : testCases) {
                TestCaseResult testCaseResult = executeUserCode(codeLanguage, container.getContainerName(), executionTimeoutMillis, testCase);
                if (!testCaseResult.isSuccess()) {
                    System.out.println(testCaseResult.submissionInfo());
                    return testCaseResult;
                }
            }
            SubmissionSuccess submissionSuccess = new SubmissionSuccess();
            System.out.println(submissionSuccess.submissionInfo());
            return submissionSuccess;
        }
        finally {
            container.cleanContainer();
            containersPool.put(container);
        }
    }

    private CompileResult compileUserCode(CodeLanguage codeLanguage, String containerName) throws InterruptedException, IOException {
        List<String> command = codeLanguage.compileCommand(containerName);

        if (command.isEmpty()) {
            return new CompileSuccessResult();
        }

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start();

        boolean finished = process.waitFor(PROCESS_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        if (!finished || process.exitValue() != 0) {
            return new CompileErrorResult(readInputStream(process.getErrorStream()));
        }
        else {
            return new CompileSuccessResult();
        }
    }


    private TestCaseResult executeUserCode(CodeLanguage codeLanguage, String containerName, long executeTimeoutMillis, TestCase testCase) throws IOException, InterruptedException {
        List<String> command = codeLanguage.executeCommand(containerName);

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start();

        ProcessHandle processHandle = process.toHandle();

        try (OutputStream os = process.getOutputStream()) {
            os.write(testCase.getProblemInput().getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        boolean finished = process.waitFor(executeTimeoutMillis + 500, TimeUnit.MILLISECONDS);

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

        if (output.strip().equals(testCase.getProblemOutput())) {
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
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }


    @Override
    public SubmissionResult testCodeMultipleTests(CodeLanguage codeLanguage, String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        return executeAllTestCases(codeLanguage, solutionCode, executionTimeoutMillis, testCases);
    }

}
