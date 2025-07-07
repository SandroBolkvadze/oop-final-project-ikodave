package com.example.general_servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*.html")
public class HtmlAccessBlockerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // Allow shared HTML files (like navbar) and index.html
        if (uri.contains("/shared_html/") || uri.endsWith("/index.html")) {
            chain.doFilter(request, response);
            return;
        }

        // Block direct access to other .html files
        res.sendRedirect("/home");
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
