package com.example.submission.Utils.CompileResult;

public class CompileSuccessResult implements CompileResult {
    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public String submissionInfo() {
        return "Compilation Success";
    }
}
