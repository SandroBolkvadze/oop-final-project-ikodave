package com.example.problems.servlets;

import com.example.problems.Filters.Filter;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProblemsServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {


        Filter filter;
        request.getAttribute("");
        Filter DifficultFilter;
        Filter StatusFilter;

//        if(difficultFilter != null){
//            view ((select * from
//                    problems join dificulties
//            ) + difficultFilter)
//        }
//        if(statusFilter != null){
//            view ((select * from
//                    problems join statusFilter
//            ) + statusFitler)
//        }
    }

}
