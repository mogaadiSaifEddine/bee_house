package com.beehopuse.service;

import com.beehopuse.model.User;

public interface AuthService {
    /**
     * Authenticates a user with the given username and password
     * 
     * @param username the username
     * @param password the password
     * @return the authenticated user if successful, null otherwise
     */
    User authenticate(String username, String password);

    /**
     * Registers a new user
     * 
     * @param user the user to register
     * @return true if registration was successful, false otherwise
     */
    boolean register(User user);

    /**
     * Checks if a username is already taken
     * 
     * @param username the username to check
     * @return true if the username is available, false otherwise
     */
    boolean isUsernameAvailable(String username);

    /**
     * Checks if an email is already registered
     * 
     * @param email the email to check
     * @return true if the email is available, false otherwise
     */
    boolean isEmailAvailable(String email);
}