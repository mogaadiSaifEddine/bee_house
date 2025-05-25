package com.beehopuse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    private static final Logger logger = LoggerFactory.getLogger(PasswordHasher.class);
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 16;
    private static final String ALGORITHM = "SHA-256";

    public String hashPassword(String password) {
        try {
            // Generate a random salt
            byte[] salt = new byte[SALT_LENGTH];
            RANDOM.nextBytes(salt);

            // Hash the password with the salt
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Combine salt and hashed password
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);

            // Convert to Base64 string
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error hashing password", e);
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        try {
            logger.info("hashedPassword: " + hashedPassword);
            logger.info("password: " + password);
            // Decode the stored password
            byte[] combined = Base64.getDecoder().decode(hashedPassword);

            // Extract the salt
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, salt.length);

            // Hash the input password with the same salt
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedInput = md.digest(password.getBytes());

            // Compare the hashed passwords
            int diff = combined.length - salt.length;
            if (diff != hashedInput.length) {
                logger.debug("Password verification failed: length mismatch");
                return false;
            }

            // Use constant-time comparison to prevent timing attacks
            int result = 0;
            for (int i = 0; i < hashedInput.length; i++) {
                result |= hashedInput[i] ^ combined[salt.length + i];
            }

            return result == 0;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid base64 string in hashed password", e);
            return false;
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error verifying password", e);
            throw new RuntimeException("Error verifying password", e);
        }
    }

    // Helper method to generate a test hash
    public static void main(String[] args) {
        PasswordHasher hasher = new PasswordHasher();
        String password = "password123";
        String hashed = hasher.hashPassword(password);
        System.out.println("Hashed password: " + hashed);
        System.out.println("Verification result: " + hasher.verifyPassword(password, hashed));
    }
}