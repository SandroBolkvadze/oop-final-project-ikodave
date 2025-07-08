package com.example.user_profile.dao;

import com.example.problems.DTO.Status;
import com.example.registration.model.User;
import com.example.submissions.DTO.SubmissionVerdict;

public interface UserDetails {
    public int getVerdictProblemsCount(User user, SubmissionVerdict verdict);
}