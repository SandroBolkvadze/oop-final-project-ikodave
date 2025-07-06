package com.example.submission.DAO;

import com.example.submission.DTO.TestCase;

import java.util.List;

public interface TestDAO {

    List<TestCase> getTestCasesByProblemId(int problemId);

}
