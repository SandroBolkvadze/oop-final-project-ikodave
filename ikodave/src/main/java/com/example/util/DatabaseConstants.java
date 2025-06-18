package com.example.util;

public class DatabaseConstants {
    public static final class Users {
        public static final String TABLE_NAME = "users";
        public static final String COL_ID = "id";
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
    }

    public static final class Problems {
        public static final String TABLE_NAME = "problems";
        public static final String COL_ID = "id";
        public static final String COL_NAME = "problem_name";
        public static final String COL_DESCRIPTION = "problem_description";
        public static final String COL_DIFFICULTY_ID = "difficulty_id";
    }

    public static final class ProblemDifficulty {
        public static final String TABLE_NAME = "problem_difficulty";
        public static final String COL_ID = "id";
        public static final String COL_DIFFICULTY = "difficulty";
    }

    public static final class ProblemTopic {
        public static final String TABLE_NAME = "problem_topic";
        public static final String COL_ID = "id";
        public static final String COL_TOPIC = "topic";
    }

    public static final class ProblemManyToManyTopic {
        public static final String TABLE_NAME = "problem_many_to_many_topic";
        public static final String COL_ID = "id";
        public static final String COL_PROBLEM_ID = "problem_id";
        public static final String COL_TOPIC_ID = "topic_id";
    }

    public static final class Submissions {
        public static final String TABLE_NAME = "submissions";
        public static final String COL_ID = "id";
        public static final String COL_USER_ID = "user_id";
        public static final String COL_PROBLEM_ID = "problem_id";
        public static final String COL_STATUS_ID = "status_id";
        public static final String COL_SOLUTION_CODE = "solution_code";
    }

    public static final class ProblemStatus {
        public static final String TABLE_NAME = "problem_status";
        public static final String COL_ID = "id";
        public static final String COL_STATUS_ID = "status";
    }

}
