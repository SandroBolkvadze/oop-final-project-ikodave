package com.example.submissions.DAO;

import com.example.submissions.DTO.TestCase;

import java.util.List;

public interface TestDAO {
    void insertTestCase(TestCase testCase);

    List<TestCase> getTestCasesByProblemId(int problemId);

}
