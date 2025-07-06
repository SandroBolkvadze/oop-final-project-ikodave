//package com.example.submission.CodeRunner;
//
//import com.example.submission.DTO.TestCase;
//import com.example.submission.Utils.CompileResult.CompileError;
//import com.example.submission.Utils.CompileResult.CompileResult;
//import com.example.submission.Utils.CompileResult.CompileSuccess;
//import com.example.submission.Utils.Container.CodeExecutorContainer;
//import com.example.submission.Utils.Container.Container;
//import com.example.submission.Utils.Language.CodeLang;
//import com.example.submission.Utils.SubmissionResult.SubmissionResult;
//import com.example.submission.Utils.SubmissionResult.SubmissionAccept;
//import com.example.submission.Utils.TestCaseResult.TestCaseResult;
//import com.example.submission.Utils.TestCaseResult.TestCaseRuntimeError;
//import com.example.submission.Utils.TestCaseResult.TestCaseSuccess;
//import com.example.submission.Utils.TestCaseResult.TestCaseWrongAnswer;
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.async.ResultCallback;
//import com.github.dockerjava.api.command.ExecCreateCmd;
//import com.github.dockerjava.api.command.ExecCreateCmdResponse;
//import com.github.dockerjava.api.command.InspectExecResponse;
//import com.github.dockerjava.api.model.Frame;
//import com.github.dockerjava.api.model.Statistics;
//import com.github.dockerjava.api.model.StreamType;
//import com.github.dockerjava.core.DefaultDockerClientConfig;
//import com.github.dockerjava.core.DockerClientBuilder;
//import com.github.dockerjava.core.command.ExecStartResultCallback;
//import com.github.dockerjava.netty.NettyDockerCmdExecFactory;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.List;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//public class DockerCodeExecutor {
//
//    private static final int NUM_CONTAINERS = 5;
//
//    private static final String WORKDIR_PREFIX = "Runner";
//
//    private BlockingQueue<CodeExecutorContainer> containersPool;
//
//    private final DockerClient dockerClient;
//
//
//    public DockerCodeExecutor() {
//        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("tcp://localhost:2375")
//                .build();
//        dockerClient = DockerClientBuilder
//                .getInstance(config)
//                .withDockerCmdExecFactory(new NettyDockerCmdExecFactory())
//                .build();
//    }
//
//    public void startContainers() {
//        containersPool = new ArrayBlockingQueue<>(NUM_CONTAINERS);
//        for (int i = 0; i < NUM_CONTAINERS; i++) {
//            //  Path workDir = Files.createDirectories(Path.of("C:\\Users\\User\\Desktop\\runner" + UUID.randomUUID()));
//            try {
//                Path hostPath = Files.createTempDirectory(WORKDIR_PREFIX + "-");
//                CodeExecutorContainer container = new CodeExecutorContainer(hostPath);
//                container.createContainer(dockerClient);
//                containersPool.put(container);
//            } catch (IOException | InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public void destroyContainers() {
//        for (int i = 0; i < NUM_CONTAINERS; i++) {
//            try {
//                CodeExecutorContainer container = containersPool.take();
//                container.destroyContainer(dockerClient);
//                FileUtils.deleteDirectory(container.getHostPath().toFile());
//            } catch (InterruptedException | IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//
//    private SubmissionResult executeAllTestCases(CodeLang codeLanguage, String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
//        CodeExecutorContainer container = containersPool.take();
//        codeLanguage.createFiles(container.getHostPath(), solutionCode);
//
//        try {
//            CompileResult compileResult = compileUserCode(container.getContainerId(), codeLanguage);
//            if (!compileResult.isSuccess()) {
//                System.out.println(compileResult.submissionInfo());
//                return compileResult;
//            }
//            for (TestCase testCase : testCases) {
//                TestCaseResult testCaseResult = executeUserCode(container.getContainerId(), codeLanguage, executionTimeoutMillis, testCase);
//                if (!testCaseResult.isSuccess()) {
//                    System.out.println(testCaseResult.submissionInfo());
//                    return testCaseResult;
//                }
//            }
//            SubmissionAccept submissionSuccess = new SubmissionAccept();
//            System.out.println(submissionSuccess.submissionInfo());
//            return submissionSuccess;
//        }
//        finally {
//            container.cleanContainer(dockerClient);
//            containersPool.put(container);
//        }
//    }
//
//
//    private CompileResult compileUserCode(String containerId, CodeLang codeLanguage) throws InterruptedException {
//        List<String> compileCmd = codeLanguage.compileCommand();
//
//        if (compileCmd.isEmpty()) {
//            return new CompileSuccess();
//        }
//
//        ExecCreateCmdResponse response = dockerClient.execCreateCmd(containerId)
//                .withAttachStdout(true)
//                .withAttachStderr(true)
//                .withCmd(compileCmd.toArray(new String[0]))
//                .exec();
//
//
//        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
//
//        ResultCallback.Adapter<Frame> callback = new ResultCallback.Adapter<>() {
//            @Override
//            public void onNext(Frame frame) {
//                if (frame.getStreamType() == StreamType.STDERR) {
//                    try { stderr.write(frame.getPayload()); } catch (IOException ignore) { }
//                }
//            }
//        };
//
//        dockerClient.execStartCmd(response.getId())
//            .exec(callback)
//                .awaitCompletion(2000, TimeUnit.MILLISECONDS);
//
//        InspectExecResponse inspect = dockerClient.inspectExecCmd(response.getId()).exec();
//        Long exitCode = inspect.getExitCodeLong();
//
//        if (exitCode == null || exitCode != 0) {
//            return new CompileError(stderr.toString());
//        } else {
//            System.out.println("success compiling!!!");
//            return new CompileSuccess();
//        }
//    }
//
//
//    private TestCaseResult executeUserCode(String containerId,
//                                           CodeLang codeLanguage,
//                                           long executeTimeoutMillis,
//                                           TestCase testCase) throws InterruptedException, IOException {
//
//        List<String> executeCmd = codeLanguage.executeCommand();
//
//        ExecCreateCmdResponse execCreate = dockerClient.execCreateCmd(containerId)
//                .withCmd(executeCmd.toArray(new String[0]))
//                .withAttachStdout(true)
//                .withAttachStdin(true)
//                .withAttachStderr(true)
//                .exec();
//
////        System.out.println("here1");
//
//        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
//        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
//
//        ResultCallback.Adapter<Frame> callback = new ResultCallback.Adapter<>() {
//            @Override
//            public void onNext(Frame frame) {
//                if (frame.getStreamType() == StreamType.STDOUT) {
//                    try { stdout.write(frame.getPayload()); } catch (IOException _) {}
//                } else if (frame.getStreamType() == StreamType.STDERR) {
//                    try { stderr.write(frame.getPayload()); } catch (IOException _) {}
//                }
//            }
//        };
//
////        System.out.println("here2");
//
//        byte[] inputBytes = testCase.getProblemInput().getBytes(StandardCharsets.UTF_8);
//        InputStream inputStream = new ByteArrayInputStream(inputBytes);
//
//        long cpuBefore = getContainerCpuUsage(containerId);
//
//        dockerClient.execStartCmd(execCreate.getId())
//                .withStdIn(inputStream)
//                .withDetach(false)
//                .withTty(false)
//                .exec(callback)
//                .awaitCompletion(executeTimeoutMillis + 500, TimeUnit.MILLISECONDS);
//
//        long cpuAfter = getContainerCpuUsage(containerId);
//        long cpuUsedMillis = (cpuAfter - cpuBefore) / 1_000_000;
//
////        System.out.println("here3");
//
//        InspectExecResponse inspect = dockerClient.inspectExecCmd(execCreate.getId()).exec();
//        Long exitCode = inspect.getExitCodeLong();
//
//        if (exitCode == null) {
//            return new TestCaseRuntimeError(testCase.getOrderNum(), "process may have been killed");
//        }
//        if (exitCode != 0) {
//            return new TestCaseRuntimeError(testCase.getOrderNum(), stderr.toString(StandardCharsets.UTF_8));
//        }
//
//        String output = stdout.toString().strip();
//        System.out.println("output: " + output);
//        if (output.equals(testCase.getProblemOutput())) {
//            return new TestCaseSuccess(testCase.getOrderNum(), cpuUsedMillis);
//        } else {
//            return new TestCaseWrongAnswer(testCase.getOrderNum(), testCase.getProblemOutput(), output, "Wrong answer");
//        }
//    }
//
//
//    private long getContainerCpuUsage(String containerId) throws InterruptedException {
//        final long[] cpuUsage = {0};
//        dockerClient.statsCmd(containerId)
//            .exec(new ResultCallback.Adapter<Statistics>() {
//                @Override
//                public void onNext(Statistics stats) {
//                    cpuUsage[0] = stats.getCpuStats().getCpuUsage().getTotalUsage();
//                }
//            }).awaitCompletion(5000, TimeUnit.MILLISECONDS);
//        return cpuUsage[0];
//    }
//
//    public SubmissionResult testCodeMultipleTests(CodeLang codeLanguage, String solutionCode, long executionTimeoutMillis, List<TestCase> testCases) throws IOException, InterruptedException {
//        return executeAllTestCases(codeLanguage, solutionCode, executionTimeoutMillis, testCases);
//    }
//}
