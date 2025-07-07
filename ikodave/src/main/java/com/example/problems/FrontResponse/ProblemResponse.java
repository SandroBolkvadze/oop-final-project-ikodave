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
    private final List<TestCase> testCases;

    public ProblemResponse(String problemTitle, String problemDescription, String problemStatus, List<Topic> problemTopics, Difficulty problemDifficulty, List<TestCase> testCases) {
        this.problemTitle = problemTitle;
        this.problemDescription = problemDescription;
        this.problemStatus = problemStatus;
        this.problemTopics = problemTopics;
        this.problemDifficulty = problemDifficulty;
        this.testCases = testCases;
    }

    public List<TestCase> getTestCases() {
        return testCases;
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
