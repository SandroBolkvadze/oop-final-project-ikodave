package com.example.submissions.Utils.CompileResult;

public class CompileError extends CompileResult {

    private final String log;

    public CompileError(String log) {
        this.log = log;
    }

    @Override
    public boolean isAccept() {
        return false;
    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public long getMemory() {
        return 0;
    }

    @Override
    public String getVerdict() {
        return "Compilation Error";
    }

    @Override
    public String getLog() {
        return log;
    }
}
