package com.example.user_profile.dao;

import com.example.problems.DTO.Difficulty;
import com.example.problems.DTO.Problem;
import com.example.util.DatabaseConstants;

public interface ProblemStatsDAO {

    int getProblemCountByDifficulty(Difficulty difficulty);

}
