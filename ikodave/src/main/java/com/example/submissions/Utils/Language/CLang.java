package com.example.submissions.Utils.Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.example.submissions.CodeRunner.DockerCodeRunner.SANDBOX_USER;

public class CLang implements CodeLang {

    private static final String C_FILE_NAME = "Solution";

    @Override
    public void createFiles(Path path, String solutionCode) {
        try {
            Files.writeString(path.resolve(C_FILE_NAME + ".c"), solutionCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> compileCommand(String containerName) {
        return List.of(
                "docker", "exec", "-u", SANDBOX_USER, containerName,
                "gcc", C_FILE_NAME + ".c",
                "-std=c17", "-O2",
                "-o", C_FILE_NAME
        );
    }

    @Override
    public List<String> executeCommand(String containerName) {
        return List.of(
                "docker", "exec", "-u", SANDBOX_USER, "-i", containerName,
                "/app/" + C_FILE_NAME
        );
    }
}