package com.example.user_profile.servlets;

import com.example.problems.DAO.DifficultyDAO;
import com.example.problems.DTO.Difficulty;
import com.example.registration.dao.UserDAO;
import com.example.registration.model.User;
import com.example.submissions.DAO.VerdictDAO;
import com.example.submissions.DTO.SubmissionVerdict;
import com.example.user_profile.Response.UserStats;
import com.example.user_profile.Response.UsernameBody;
import com.example.user_profile.dao.ProblemStatsDAO;
import com.example.user_profile.dao.UserStatsDAO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.example.util.AttributeConstants.*;
import static com.example.util.DatabaseConstants.DifficultyElements.*;
import static com.example.util.DatabaseConstants.ProblemVerdictElements.VERDICT_ACCEPTED;

public class ProfileStatsServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute(USER_DAO_KEY);
        UserStatsDAO userStatsDAO = (UserStatsDAO) getServletContext().getAttribute(USER_STATS_DAO);
        ProblemStatsDAO problemStatsDAO = (ProblemStatsDAO) getServletContext().getAttribute(PROBLEM_STATS_DAO);
        DifficultyDAO difficultyDAO = (DifficultyDAO) getServletContext().getAttribute(DIFFICULTY_DAO_KEY);
        VerdictDAO verdictDAO = (VerdictDAO) getServletContext().getAttribute(VERDICT_DAO_KEY);
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);

        UsernameBody usernameBody = gson.fromJson(request.getReader(), UsernameBody.class);
        String username = usernameBody.getUsername();
        User user = userDAO.getUserByUsername(username);


        UserStats userStats = new UserStats();

        Difficulty difficultyEasy = difficultyDAO.getDifficultyByName(DIFFICULTY_EASY);
        Difficulty difficultyMedium = difficultyDAO.getDifficultyByName(DIFFICULTY_MEDIUM);
        Difficulty difficultyHard = difficultyDAO.getDifficultyByName(DIFFICULTY_HARD);
        SubmissionVerdict verdictAccepted = verdictDAO.getVerdictByName(VERDICT_ACCEPTED);

        int easySolvedCount = userStatsDAO.getSolvedProblemCountByDifficulty(user, difficultyEasy);
        int easyTotalCount = problemStatsDAO.getProblemCountByDifficulty(difficultyEasy);
        userStats.setEasySolvedProblemsCount(easySolvedCount);
        userStats.setEasyNotSolvedProblemsCount(easyTotalCount - easySolvedCount);

        int mediumSolvedCount = userStatsDAO.getSolvedProblemCountByDifficulty(user, difficultyMedium);
        int mediumTotalCount = problemStatsDAO.getProblemCountByDifficulty(difficultyMedium);
        userStats.setMediumSolvedProblemsCount(mediumTotalCount);
        userStats.setMediumNotSolvedProblemsCount(mediumTotalCount - mediumSolvedCount);

        int hardSolvedCount = userStatsDAO.getSolvedProblemCountByDifficulty(user, difficultyHard);
        int hardTotalCount = problemStatsDAO.getProblemCountByDifficulty(difficultyHard);
        userStats.setHardSolvedProblemsCount(hardSolvedCount);
        userStats.setHardNotSolvedProblemsCount(hardTotalCount - hardSolvedCount);

        int submissionsTotalCount = userStatsDAO.getSubmissionsCount(user);
        int acceptedSubmissionsCount = userStatsDAO.getSubmittedProblemCountByVerdict(user, verdictAccepted);
        userStats.setNotAcceptedSubmissionsCount(submissionsTotalCount - acceptedSubmissionsCount);

        int submissionsToday = userStatsDAO.getSubmissionsCountByDays(user, 1);
        userStats.setAcceptedProblemsCountToday(submissionsToday);

        int userRank = userStats.getUserRank();
        userStats.setUserRank(userRank);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(userStats));
    }

}

