package com.example.submissions.DAO;

import com.example.submissions.DTO.TestCase;

import java.util.List;

public interface TestDAO {

    List<TestCase> getTestCasesByProblemId(int problemId);

}
