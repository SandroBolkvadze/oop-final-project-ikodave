package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;
import com.example.problems.FrontResponse.ProblemListResponse;

import java.util.List;

public interface ProblemDAO {

    void insertProblem(Problem problem);
    List<Problem> getProblemsByFilter(Filter filter);

    List<ProblemListResponse> getProblemResponsesByFilterLoggedOut(Filter filter);

    List<ProblemListResponse> getProblemResponsesByFilterLoggedIn(Filter filter);

    List<Topic> getProblemTopics(int problemId);

    Difficulty getProblemDifficulty(int problemId);

    String getProblemStatus(int problemId, int userId);

    String getProblemTitle(int problemId);

    int getProblemId(String problemTitle);

    Problem getProblemByTitle(String problemTitle);

    int getDifficultyId(String difficulty);

    int getStatusId(String status);

    int getTopicId(String topic);
}
