package com.example.submission.Utils.Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PythonLanguage implements CodeLanguage {

    private static final String PYTHON_FILE_NAME = "Solution";

    @Override
    public void createFiles(Path path, String solutionCode) {
        try {
            Files.writeString(path.resolve(PYTHON_FILE_NAME + ".py"), solutionCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> compileCommand(String containerName) {
        return List.of();
    }

    @Override
    public List<String> executeCommand(String containerName) {
        return List.of(
                "docker", "exec", "-i", containerName,
                "python3", "-u", "/app/" + PYTHON_FILE_NAME + ".py"
        );
    }
}
