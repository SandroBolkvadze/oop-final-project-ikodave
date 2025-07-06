package com.example.submissions.Utils.Submit;

public class UserSubmission {

    private final String solutionCode;

    private final String codeLanguage;

    private final String problemTitle;


    public UserSubmission(String solutionCode, String codeLanguage, String problemName) {
        this.solutionCode = solutionCode;
        this.codeLanguage = codeLanguage;
        this.problemTitle = problemName;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public String getProblemTitle() {
        return problemTitle;
    }
}
