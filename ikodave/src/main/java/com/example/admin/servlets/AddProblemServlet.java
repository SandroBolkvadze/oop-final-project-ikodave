package com.example.admin.servlets;

import com.example.admin.dto.ProblemWithTests;
import com.example.problems.DAO.DifficultyDAO;
import com.example.problems.DAO.ProblemDAO;
import com.example.problems.DTO.Problem;
import com.example.submissions.DTO.Submission;
import com.example.submissions.DTO.TestCase;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static com.example.util.AttributeConstants.DIFFICULTY_DAO_KEY;
import static com.example.util.AttributeConstants.GSON_KEY;

@WebServlet(name = "AddProblemServlet", value = "/api/AddProblemServlet")
public class AddProblemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = (Gson) getServletContext().getAttribute(GSON_KEY);
        ProblemDAO problemDAO =
        DifficultyDAO difficultyDAO = (DifficultyDAO) getServletContext().getAttribute(DIFFICULTY_DAO_KEY);

        ProblemWithTests problemWithTests = gson.fromJson(request.getReader(), ProblemWithTests.class);
        // Build the Problem object
        Problem problem = new Problem();

        problem.setTitle(problemWithTests.getTitle());

        problem.setDescription(problemWithTests.getDescription());

        int difficultyId = difficultyDAO.getDifficultyByName(problemWithTests.getDifficulty()).getId();
        problem.setDifficultyId(difficultyId);

        problem.setCreateDate(new Timestamp(System.currentTimeMillis()));

        problem.setInputSpec(problemWithTests.getInputSpec());

        problem.setOutputSpec(problemWithTests.getOutputSpec());

        problem.setTimeLimit(problemWithTests.getTimeLimit());

        problem.setMemoryLimit(128 * 1024 * 1024); // default 128MB or any value you want





        // Convert TestCaseDTO to TestCase entity (for DB)
        List<TestCase> testCases = problemWithTests.getTestCases().stream()
                .map(dto -> new TestCase(dto.getInput(), dto.getOutput()))
                .collect(Collectors.toList());

    }
}
