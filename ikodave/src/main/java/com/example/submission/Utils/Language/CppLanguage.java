package com.example.submission.Utils.Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CppLanguage implements CodeLanguage {

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
                "docker", "exec", containerName,
                "g++", CPP_FILE_NAME + ".cpp",
                "-std=c++17", "-O2",
                "-o", CPP_FILE_NAME
        );
    }

    @Override
    public List<String> executeCommand(String containerName) {
        return List.of(
                "docker", "exec", "-i", containerName,
                "/app/" + CPP_FILE_NAME
        );
    }
}
