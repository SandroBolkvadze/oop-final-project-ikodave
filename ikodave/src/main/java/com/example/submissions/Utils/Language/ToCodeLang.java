package com.example.submissions.Utils.Language;

public class ToCodeLang {

    public static CodeLang toCodeLang(String codeLanguage) {
        if (codeLanguage.equalsIgnoreCase("java")) {
            return new JavaLang();
        }
        if (codeLanguage.equalsIgnoreCase("cpp")) {
            return new CPPLang();
        }
        if (codeLanguage.equalsIgnoreCase("c")) {
            return new CLang();
        }
        if (codeLanguage.equalsIgnoreCase("python")) {
            return new PythonLang();
        }
        return null;
    }

}
