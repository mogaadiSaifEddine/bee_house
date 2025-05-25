package com.beehopuse.servlet;

import com.beehopuse.model.Farm;
import com.beehopuse.model.User;
import com.beehopuse.service.FarmService;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/farms")
public class FarmServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(FarmServlet.class.getName());

    @EJB
    private FarmService farmService;

    @Override
    public void init() throws ServletException {
        super.init();
        if (farmService == null) {
            throw new ServletException("FarmService EJB injection failed");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        // if (session == null || session.getAttribute("currentUser") == null) {
        // response.sendRedirect(request.getContextPath() + "/login");
        // return;
        // }

        // User currentUser = (User) session.getAttribute("currentUser");
        try {
            if (farmService == null) {
                throw new ServletException("FarmService is not properly initialized");
            }

            // Get farms for the current user
            List<Farm> farms = farmService.getAllFarms();

            // Set the farms in request attribute
            request.setAttribute("farms", farms);

            // Forward to the farms JSP page
            request.getRequestDispatcher("/WEB-INF/views/farm/list.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            logger.severe("Error in FarmServlet: " + e.getMessage());
            e.printStackTrace();

            // Set error message
            request.setAttribute("error", "Error loading farms: " + e.getMessage());

            // Forward to error page
            request.getRequestDispatcher("/WEB-INF/views/error.jsp")
                    .forward(request, response);
        }
    }
}