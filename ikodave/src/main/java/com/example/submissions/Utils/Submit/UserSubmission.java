package com.example.submissions.Utils.Submit;

public class UserSubmission {

    private final String problemTitle;

    private final String codeLanguage;

    private final String solutionCode;


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
