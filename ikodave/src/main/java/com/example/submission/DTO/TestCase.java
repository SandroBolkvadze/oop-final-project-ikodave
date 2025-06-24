package com.example.submission.DTO;

public class TestCase {

    private final int id;
    private final int problemId;
    private final int orderNum;
    private final String problemOutput;
    private final String problemInput;

    public TestCase(int id, int problemId, int orderNum, String problemOutput, String problemInput) {
        this.id = id;
        this.problemId = problemId;
        this.orderNum = orderNum;
        this.problemOutput = problemOutput;
        this.problemInput = problemInput;
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
