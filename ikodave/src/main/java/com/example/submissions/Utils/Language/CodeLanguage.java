package com.example.submissions.Utils.Language;

import java.nio.file.Path;
import java.util.List;

public interface CodeLanguage {

    void createFiles(Path path, String solutionCode);

    List<String> compileCommand(String containerName);

    List<String> executeCommand(String containerName);

}