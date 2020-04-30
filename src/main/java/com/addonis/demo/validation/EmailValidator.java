package com.addonis.demo.validation;

import java.util.regex.Pattern;

/**
 * Validates the email provided from the user
 */
public class EmailValidator {

    public static boolean isValidEmailAddress(String email) {
        String emailRegex = "([a-zA-Z0-9/_/./-]+@[a-zA-Z]+.[a-z]+)";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return !pat.matcher(email).matches();
    }
}
