package com.beehopuse.servlet;

import com.beehopuse.model.User;
import com.beehopuse.service.AuthService;
import com.beehopuse.service.impl.AuthServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        // Get DataSource from ServletContext
        DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource not found in ServletContext");
        }
        authService = new AuthServiceImpl(dataSource);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("register".equals(action)) {
            handleRegistration(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate input
        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = authService.authenticate(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user);
                session.setAttribute("userRole", user.getRole());

                // Redirect based on user role
                String redirectPath = determineRedirectPath(user.getRole());
                response.sendRedirect(request.getContextPath() + "/farm");
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");

        // Validate input
        if (username == null || password == null || email == null || fullName == null ||
                username.trim().isEmpty() || password.trim().isEmpty() ||
                email.trim().isEmpty() || fullName.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            // Check if username or email is already taken
            if (!authService.isUsernameAvailable(username)) {
                request.setAttribute("error", "Username is already taken");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            if (!authService.isEmailAvailable(email)) {
                request.setAttribute("error", "Email is already registered");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            // Create and register user
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setFullName(fullName);
            newUser.setRole("USER"); // Default role for new registrations

            if (authService.register(newUser)) {
                // Registration successful, redirect to login
                request.setAttribute("success", "Registration successful. Please login.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private String determineRedirectPath(String role) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return "/admin/dashboard.jsp";
            case "FARMER":
                return "/farmer/dashboard.jsp";
            case "AGENT":
                return "/agent/dashboard.jsp";
            default:
                return "/index.jsp";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            User currentUser = (User) session.getAttribute("currentUser");
            String redirectPath = determineRedirectPath(currentUser.getRole());
            response.sendRedirect(request.getContextPath() + redirectPath);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}