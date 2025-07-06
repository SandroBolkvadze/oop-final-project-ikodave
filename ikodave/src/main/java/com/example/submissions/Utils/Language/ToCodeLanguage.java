package com.example.submissions.Utils.Language;

public class ToCodeLanguage {

    public static CodeLanguage toCodeLanguage(String codeLanguage) {
        switch (codeLanguage.toLowerCase()) {
            case "java" -> new JavaLanguage();
            case "c" -> new CLanguage();
            case "cpp" -> new CppLanguage();
            case "python" -> new PythonLanguage();
        }
        return null;
    }

}
