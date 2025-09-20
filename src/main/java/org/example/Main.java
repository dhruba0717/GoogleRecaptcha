package org.example;

import static org.example.ReCaptchaValidator.validateReCaptcha;


public class Main {
    public static void main(String[] args) {
        String exampleToken = "testToken";

        boolean isValid = validateReCaptcha(exampleToken);

        System.out.println("Is reCAPTCHA valid? " + isValid);
    }
}
