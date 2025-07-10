package com.example.user_profile.dao;

import com.example.problems.DTO.Difficulty;
import com.example.registration.model.User;
import com.example.submissions.DTO.SubmissionVerdict;

public interface UserStatsDAO {
    
    int getSubmittedProblemCountByVerdict(User user, SubmissionVerdict verdict);

    int getSolvedProblemCount(User user);

    int getSolvedProblemCountByDifficulty(User user, Difficulty difficulty);

    int getSubmissionsCount(User user);

    int getSubmissionsCountByDays(User user, int lastDays);

    int getUserRank(User user);
}