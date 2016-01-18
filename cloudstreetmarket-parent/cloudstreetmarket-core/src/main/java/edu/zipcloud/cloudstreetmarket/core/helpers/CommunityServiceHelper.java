package edu.zipcloud.cloudstreetmarket.core.helpers;

import org.springframework.stereotype.Component;

@Component
public class CommunityServiceHelper {

    private static final String RANDOM_PASSWORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_!$*";
    private static final int RANDOM_PASSWORD_LENGTH = 12;
    
    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < RANDOM_PASSWORD_LENGTH; i++) {
            int charIndex = (int) (Math.random() * RANDOM_PASSWORD_CHARS.length());
            char randomChar = RANDOM_PASSWORD_CHARS.charAt(charIndex);
            password.append(randomChar);
        }
        return password.toString();
    }
}
