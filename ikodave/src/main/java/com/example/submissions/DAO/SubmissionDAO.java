package com.example.submissions.DAO;

import com.example.submissions.DTO.Submission;

import java.util.List;

public interface SubmissionDAO {

    int insertSubmission(Submission submission);

    void updateSubmission(Submission submission);

    List<Submission> getSubmissionsBy(int userId, int problemId);

    List<Submission> getSubmissionsByOrder(int userId, int problemId);

    List<Submission> getSubmissionsByDateByOrder(int userId, int day, int month, int year);

}
