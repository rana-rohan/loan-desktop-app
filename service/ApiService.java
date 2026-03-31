package service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    public static String login(String email, String password) {
        try {
            URL url = new URL("http://localhost:8080/users/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInput = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

            OutputStream os = con.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();

            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );

                String line;
                StringBuilder response = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                in.close();
                return response.toString().trim(); // token

            } else {
                return "ERROR";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public static String getLoans(String token) {
        try {
            URL url = new URL("http://localhost:8080/loan/all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            BufferedReader reader;

            int responseCode = conn.getResponseCode();

            if (responseCode == 401) {
                return "UNAUTHORIZED";
            }

            if (responseCode == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            return response.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public static String applyLoan(String token, String amount) {
        try {
            URL url = new URL("http://localhost:8080/loan/apply");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setDoOutput(true);

            String jsonInput = "{ \"amount\": " + amount + ", \"userId\": 1 }";

            OutputStream os = con.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();

            BufferedReader reader;

            if (con.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}