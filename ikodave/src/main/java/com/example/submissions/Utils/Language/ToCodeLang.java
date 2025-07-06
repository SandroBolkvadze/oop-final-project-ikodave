package com.example.submissions.Utils.Language;

public class ToCodeLang {

    public static CodeLang toCodeLang(String codeLanguage) {
        switch (codeLanguage.toLowerCase()) {
            case "java" -> new JavaLang();
            case "c" -> new CLang();
            case "cpp" -> new CPPLang();
            case "python" -> new PythonLang();
        }
        return null;
    }

}
