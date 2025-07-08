package com.example.problems.FrontResponse;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Topic;
import com.example.submissions.DTO.TestCase;
import java.util.List;

public class ProblemResponse {

    private final String problemTitle;
    private final String problemDescription;
    private final String problemStatus;
    private final List<String> problemTopics;
    private final String problemDifficulty;
    private final List<TestCase> problemTestCases;
    private final String problemInputSpec;
    private final String problemOutputSpec;
    private final long problemTime;
    private final long problemMemory;


    public ProblemResponse(String problemTitle, String problemDescription, String problemStatus, List<String> problemTopics, String problemDifficulty, List<TestCase> problemTestCases, String problemInputSpec, String problemOutputSpec, long problemTime, long problemMemory) {
        this.problemTitle = problemTitle;
        this.problemDescription = problemDescription;
        this.problemStatus = problemStatus;
        this.problemTopics = problemTopics;
        this.problemDifficulty = problemDifficulty;
        this.problemTestCases = problemTestCases;
        this.problemInputSpec = problemInputSpec;
        this.problemOutputSpec = problemOutputSpec;
        this.problemTime = problemTime;
        this.problemMemory = problemMemory;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public String getProblemStatus() {
        return problemStatus;
    }

    public List<String> getProblemTopics() {
        return problemTopics;
    }

    public String getProblemDifficulty() {
        return problemDifficulty;
    }

    public List<TestCase> getProblemTestCases() {
        return problemTestCases;
    }

    public String getProblemInputSpec() {
        return problemInputSpec;
    }

    public String getProblemOutputSpec() {
        return problemOutputSpec;
    }

    public long getProblemTime() {
        return problemTime;
    }

    public long getProblemMemory() {
        return problemMemory;
    }
}
