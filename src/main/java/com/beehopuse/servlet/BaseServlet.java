package com.beehopuse.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected static final String VIEW_PATH = "/WEB-INF/views/";
    protected static final String REDIRECT_PREFIX = "redirect:";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    protected void forward(String viewName, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_PATH + viewName + ".jsp").forward(request, response);
    }

    protected void redirect(String url, HttpServletResponse response) throws IOException {
        response.sendRedirect(getServletContext().getContextPath() + url);
    }

    protected String getViewName(String viewPath) {
        if (viewPath.startsWith(REDIRECT_PREFIX)) {
            return viewPath.substring(REDIRECT_PREFIX.length());
        }
        return viewPath;
    }

    protected boolean isRedirect(String viewPath) {
        return viewPath.startsWith(REDIRECT_PREFIX);
    }

    protected void setUserSession(HttpServletRequest request, Object user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

    protected Object getUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? session.getAttribute("user") : null;
    }

    protected void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
} 