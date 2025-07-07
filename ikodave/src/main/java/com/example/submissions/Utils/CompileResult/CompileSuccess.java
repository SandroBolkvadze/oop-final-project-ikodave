package com.example.submissions.Utils.CompileResult;

public class CompileSuccess extends CompileResult {


    @Override
    public boolean isAccept() {
        return true;
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
        return "Compilation Success";
    }

    @Override
    public String getLog() {
        return "Compilation Success";
    }
}
