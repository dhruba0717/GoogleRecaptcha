package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReCaptchaValidator {

    // Constants for the reCAPTCHA endpoint and secret key (replace with your own)
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    //Add Your secret here
    private static final String SECRET_KEY = "yourSecret";

    public static boolean validateReCaptcha(String token) {
        try {
            // Create the HTTP connection
            URL url = new URL(RECAPTCHA_VERIFY_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configure the HTTP request
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Construct the POST data
            String postData = "secret=" + SECRET_KEY + "&response=" + token;

            // Send the POST data
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData.getBytes());
                os.flush();
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode+"Res");
            System.out.println(connection.getResponseMessage()+"ResMess");
            if (responseCode != 200) {
                return false; // Failed to validate
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            // Process the response (check success and other fields as needed)
            // You could use a JSON parsing library like Jackson or Gson here
            String jsonResponse = response.toString();
            // Simple validation: check if "success" is true
            System.out.println(jsonResponse);
            return jsonResponse.contains("\"success\": true");

        } catch (Exception e) {
            e.printStackTrace();
            return false; // In case of error, return false
        }
    }
}
