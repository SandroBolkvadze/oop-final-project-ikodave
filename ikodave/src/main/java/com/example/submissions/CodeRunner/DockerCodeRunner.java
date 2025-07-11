package com.example.submissions.CodeRunner;

import com.example.submissions.DTO.TestCase;
import com.example.submissions.Utils.CompileResult.CompileError;
import com.example.submissions.Utils.CompileResult.CompileResult;
import com.example.submissions.Utils.CompileResult.CompileSuccess;
import com.example.submissions.Utils.Container.Container;
import com.example.submissions.Utils.Language.CodeLang;
import com.example.submissions.Utils.SubmissionResult.*;
import com.example.submissions.Utils.TestCaseResult.*;
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

import static java.lang.String.format;

public class DockerCodeRunner implements CodeRunner {

    public static final String SANDBOX_USER = "sandboxuser";

    private static final int NUM_CONTAINERS = 5;

    private static final long PROCESS_TIMEOUT_MILLIS = 5000;

    private static final String WORKDIR_PREFIX = "Runner";

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

    private SubmissionResult executeAllTestCases(CodeLang codeLang, String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        Container container = containersPool.take();
        codeLang.createFiles(container.getWorkDir(), solutionCode);
        try {
            CompileResult compileResult = compileUserCode(codeLang, container.getContainerName());

            if (!compileResult.isAccept()) {
                return compileResult;
            }

            long maxTime = 0;
            long maxMemory = 0;
            for (TestCase testCase : testCases) {
                TestCaseResult testCaseResult = executeUserCode(codeLang, container.getContainerName(), executionTimeoutMillis, testCase);
                maxTime = Math.max(maxTime, testCaseResult.getTime());
                maxMemory = Math.max(maxMemory, testCaseResult.getMemory());
                if (!testCaseResult.isAccept()) {
                    return new TestCaseReject(maxTime, maxMemory, testCaseResult.getVerdict(), testCaseResult.getLog());
                }
            }
            return new SubmissionAccept(maxTime, maxMemory);
        }
        finally {
            container.cleanContainer();
            containersPool.put(container);
        }
    }

    private CompileResult compileUserCode(CodeLang codeLang, String containerName) throws InterruptedException, IOException {
        List<String> command = codeLang.compileCommand(containerName);

        if (command.isEmpty()) {
            return new CompileSuccess();
        }

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start();

        boolean finished = process.waitFor(PROCESS_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        if (!finished || process.exitValue() != 0) {
            return new CompileError(readInputStream(process.getErrorStream()));
        }
        else {
            return new CompileSuccess();
        }
    }


    private TestCaseResult executeUserCode(CodeLang codeLang, String containerName, long executeTimeoutMillis, TestCase testCase) throws IOException, InterruptedException {
        List<String> command = codeLang.executeCommand(containerName);

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start();

        ProcessHandle processHandle = process.toHandle();

        try (OutputStream os = process.getOutputStream()) {
            os.write(testCase.getProblemInput().getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        boolean finished = process.waitFor(executeTimeoutMillis + 1000, TimeUnit.MILLISECONDS);

        if (!finished) {
            process.destroy();
            return new TestCaseTimeLimitExceeded(executeTimeoutMillis, 0, format("Time Limit Exceeded On Test %d", testCase.getTestNumber()));
        }

        if (process.exitValue() != 0) {
            return new TestCaseRuntimeError(executeTimeoutMillis, 0, readInputStream(process.getErrorStream()));
        }

        ProcessHandle.Info info = processHandle.info();
        Optional<Duration> cpuDuration = info.totalCpuDuration();
        long cpuTimeMillis = cpuDuration.orElse(Duration.ZERO).toMillis();

        if (cpuTimeMillis > executeTimeoutMillis) {
            return new TestCaseTimeLimitExceeded(executeTimeoutMillis, 0, format("Time Limit Exceeded On Test %d", testCase.getTestNumber()));
        }
        String output = readInputStream(process.getInputStream());

        if (output.strip().equals(testCase.getProblemOutput())) {
            return new TestCaseAccept(cpuTimeMillis, 0, format("Test Case %d Passed", testCase.getTestNumber()));
        }
        else {
            return new TestCaseWrongAnswer(cpuTimeMillis, 0, format("Wrong Answer On Test %d", testCase.getTestNumber()));
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
    public SubmissionResult testCodeMultipleTests(CodeLang codeLang, String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
        return executeAllTestCases(codeLang, solutionCode, executionTimeoutMillis, testCases);
    }

}
