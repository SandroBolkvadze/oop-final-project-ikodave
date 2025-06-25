package com.example.submission.Utils.CompileResult;

public class CompileErrorResult implements CompileResult {

    private final String log;

    public CompileErrorResult(String log) {
        this.log = log;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
