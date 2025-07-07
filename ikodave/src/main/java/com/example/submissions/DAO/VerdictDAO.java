package com.example.submissions.DAO;

import com.example.submissions.DTO.SubmissionVerdict;

public interface VerdictDAO {

    SubmissionVerdict getVerdictByName(String verdictName);

}
