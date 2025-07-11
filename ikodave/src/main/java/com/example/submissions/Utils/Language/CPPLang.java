
package com.example.submissions.Utils.Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.example.submissions.CodeRunner.DockerCodeRunner.SANDBOX_USER;

public class CPPLang implements CodeLang {

    private static final String CPP_FILE_NAME = "Solution";

    @Override
    public void createFiles(Path path, String solutionCode) {
        try {
            Files.writeString(path.resolve(CPP_FILE_NAME + ".cpp"), solutionCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> compileCommand(String containerName) {
        return List.of(
                "docker", "exec", "-u", SANDBOX_USER, containerName,
                "g++", CPP_FILE_NAME + ".cpp",
                "-std=c++17", "-O2",
                "-o", CPP_FILE_NAME
        );
    }

    @Override
    public List<String> executeCommand(String containerName) {
        return List.of(
                "docker", "exec", "-u", SANDBOX_USER, "-i", containerName,
                "/app/" + CPP_FILE_NAME
        );
    }
}
