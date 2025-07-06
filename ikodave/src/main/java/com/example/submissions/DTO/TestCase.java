package com.example.submissions.DTO;

public class TestCase {

    private int id;
    private int problemId;
    private int orderNum;
    private String problemOutput;
    private String problemInput;

    public TestCase(int id, int problemId, int orderNum, String problemOutput, String problemInput) {
        this.id = id;
        this.problemId = problemId;
        this.orderNum = orderNum;
        this.problemOutput = problemOutput;
        this.problemInput = problemInput;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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

    public int getOrderNum() {
        return orderNum;
    }

    public String getProblemOutput() {
        return problemOutput;
    }

    public String getProblemInput() {
        return problemInput;
    }

}
