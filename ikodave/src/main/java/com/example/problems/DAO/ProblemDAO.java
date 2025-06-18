package com.example.problems.DAO;

import com.example.problems.DTO.Problem;
import com.example.problems.Filters.Filter;

import java.util.List;

public interface ProblemDAO {

    List<Problem> getProblemsByFilter(Filter filter);

}
