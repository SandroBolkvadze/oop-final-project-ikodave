package com.example.submissions.DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class Submission {

    private int id;
    private int userId;
    private int problemId;
    private int verdictId;
    private String solutionCode;
    private int codeLanguageId;
    private long time;
    private long memory;
    private Timestamp submitDate;
    private String log;

    public Submission(int id, int userId, int problemId, int verdictId, String solutionCode, int codeLanguageId, long time, long memory, Timestamp submitDate, String log) {
        this.id = id;
        this.userId = userId;
        this.problemId = problemId;
        this.verdictId = verdictId;
        this.solutionCode = solutionCode;
        this.codeLanguageId = codeLanguageId;
        this.time = time;
        this.memory = memory;
        this.submitDate = submitDate;
        this.log = log;
    }


    public Submission() {

    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public long getTime() {
        return time;
    }

    public long getMemory() {
        return memory;
    }

    public void setCodeLanguageId(int codeLanguageId) {
        this.codeLanguageId = codeLanguageId;
    }

    public int getCodeLanguageId() {
        return codeLanguageId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public void setVerdictId(int verdictId) {
        this.verdictId = verdictId;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public void setSubmitDate(Timestamp submitDate) {
        this.submitDate = submitDate;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProblemId() {
        return problemId;
    }

    public int getVerdictId() {
        return verdictId;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public Timestamp getSubmitDate() {
        return submitDate;
    }

    public String getLog() {
        return log;
    }
}
