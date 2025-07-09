package com.example.problems.DAO;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.problems.DTO.Status;
import com.example.problems.DTO.Topic;
import com.example.problems.Filters.Filter;
import com.example.problems.FrontResponse.ProblemListResponse;

import java.util.List;

public interface ProblemDAO {

    List<Problem> getProblemsByFilter(Filter filter);

    List<ProblemListResponse> getProblemResponsesByFilter(Filter filter);

    List<Topic> getProblemTopics(int problemId);

    Difficulty getProblemDifficulty(int problemId);

    Status getProblemStatus(int problemId, int userId);

    String getProblemTitle(int problemId);

    int getProblemId(String problemTitle);

    Problem getProblemByTitle(String problemTitle);

    int getDifficultyId(String difficulty);

    int getStatusId(String status);

    int getTopicId(String topic);
}
