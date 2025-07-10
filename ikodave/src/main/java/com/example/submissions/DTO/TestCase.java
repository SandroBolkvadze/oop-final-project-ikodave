package com.example.submissions.DTO;

public class TestCase {

    private int id;
    private int problemId;
    private int testNumber;
    private String problemOutput;
    private String problemInput;

    public TestCase(int id, int problemId, int testNumber, String problemOutput, String problemInput) {
        this.id = id;
        this.problemId = problemId;
        this.testNumber = testNumber;
        this.problemOutput = problemOutput;
        this.problemInput = problemInput;
    }

    public TestCase(int problemId, int testNumber, String problemOutput, String problemInput) {
        this.problemId = problemId;
        this.testNumber = testNumber;
        this.problemOutput = problemOutput;
        this.problemInput = problemInput;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public void setProblemOutput(String problemOutput) {
        this.problemOutput = problemOutput;
    }

    public void setProblemInput(String problemInput) {
        this.problemInput = problemInput;
    }

    public boolean checkOutput(String output) {
        return problemOutput.equals(output);
    }

    public int getId() {
        return id;
    }

    public int getProblemId() {
        return problemId;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public String getProblemOutput() {
        return problemOutput;
    }

    public String getProblemInput() {
        return problemInput;
    }

}
