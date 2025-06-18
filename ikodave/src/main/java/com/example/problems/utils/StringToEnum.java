package com.example.problems.utils;

import com.example.problems.DTO.ProblemDifficulty;

public class StringToEnum {

    public static ProblemDifficulty stringToEnum(String difficulty) {
        switch (difficulty) {
            case "EASY":
                return ProblemDifficulty.EASY;
            case "MEDIUM":
                return ProblemDifficulty.MEDIUM;
            case "HARD":
                return ProblemDifficulty.HARD;
        }
        return null;
    }

}
