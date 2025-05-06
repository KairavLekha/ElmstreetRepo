/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.working;

import java.util.regex.Pattern;

/**
 *
 * @author Kairav
 */
public class dataValidator {

    // Validate email format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }

    // Validate phone number (10 digits, starts with 0)
    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "0\\d{9}$";
        return Pattern.matches(phoneRegex, phone);
    }

    // Validate password (8-16 characters, must have letters and numbers)
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
        return Pattern.matches(passwordRegex, password);
    }

    // Validate first name and surname (minimum 2 letters, only letters allowed)
    public static boolean isValidName(String name) {
        String nameRegex = "^[A-Za-z]{2,}$";
        return Pattern.matches(nameRegex, name);
    }
    
    public static boolean isValidAddress(String address) {
        // Regex pattern: "<number> <words>, <number> <words>"
        String pattern = "^\\d+\\s+[A-Za-z ]+,\\s*\\d+\\s+[A-Za-z ]+$";
        
        // Compile and match the pattern
        return Pattern.matches(pattern, address);
    }
}
