package com.example.problems.Filters;

import com.example.problems.DTO.Difficulty;
import static com.example.util.DatabaseConstants.*;
import static java.lang.String.format;

public class FilterDifficulty implements Filter {

    private final Difficulty difficulty;

    public FilterDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    private String generateJoinStatement() {
        return String.format(
                "JOIN %s ON %s.%s = %s.%s",
                ProblemDifficulty.TABLE_NAME,
                Problems.TABLE_NAME,
                Problems.COL_DIFFICULTY_ID,
                ProblemDifficulty.TABLE_NAME,
                ProblemDifficulty.COL_ID
        );
    }

    private String generateWhereStatement() {
        return String.format(
                "%s.%s = '%s'",
                ProblemDifficulty.TABLE_NAME,
                ProblemDifficulty.COL_DIFFICULTY,
                difficulty.getDifficulty()
        );
    }

    public String joinStatement() {
       return generateJoinStatement();
    }

    public String whereStatement() {
        return generateWhereStatement();
    }

    @Override
    public String toString() {
        return format("SELECT * FROM %s %s WHERE %s;",
                Problems.TABLE_NAME,
                joinStatement(),
                whereStatement());
    }
}
