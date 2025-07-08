package com.example.problems.FrontResponse;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Topic;
import com.example.submissions.DTO.TestCase;
import java.util.List;

public class ProblemResponse {

    private final String problemTitle;
    private final String problemDescription;
    private final String problemStatus;
    private final List<Topic> problemTopics;
    private final Difficulty problemDifficulty;
    private final List<TestCase> problemTestCases;
    private final long problemTime;
    private final long problemMemory;

    public ProblemResponse(String problemTitle, String problemDescription, String problemStatus, List<Topic> problemTopics, Difficulty problemDifficulty, List<TestCase> problemTestCases, long problemTime, long problemMemory) {
        this.problemTitle = problemTitle;
        this.problemDescription = problemDescription;
        this.problemStatus = problemStatus;
        this.problemTopics = problemTopics;
        this.problemDifficulty = problemDifficulty;
        this.problemTestCases = problemTestCases;
        this.problemTime = problemTime;
        this.problemMemory = problemMemory;
    }

    public long getProblemTime() {
        return problemTime;
    }

    public long getProblemMemory() {
        return problemMemory;
    }

    public List<TestCase> getProblemTestCases() {
        return problemTestCases;
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

    public List<Topic> getProblemTopics() {
        return problemTopics;
    }

    public Difficulty getProblemDifficulty() {
        return problemDifficulty;
    }
}
