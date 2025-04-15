package com.yourdomain.summarizer.older;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GenerateSummary {

    // OpenRouter API URL and API Key
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "Bearer <YOUR-API-KEY>"; // Replace with actual key

    public String generateSummary(String blogContent) {
        // Clean and format blog content
        String plainText = "Generate summary of this blog in 50 words: " + blogContent;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Setup connection properties
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", API_KEY);
            connection.setDoOutput(true);

            // Prepare request body with formatted blog content
            String requestBody = "{\n" +
                    "  \"model\": \"arliai/qwq-32b-arliai-rpr-v1:free\",\n" +
                    "  \"messages\": [\n" +
                    "    {\n" +
                    "      \"role\": \"user\",\n" +
                    "      \"content\": \"" + plainText.replace("\"", "\\\"").replace("\n", "\\n") + "\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // Send request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read response
            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Parse the response JSON
                String jsonResponse = response.toString();
                System.out.println("RAW responce" + jsonResponse);
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                String summary = jsonObject.getAsJsonArray("choices")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content")
                        .getAsString();

                return summary; // Return the extracted summary content
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while ((line = errorReader.readLine()) != null) {
                    response.append(line);
                }
                errorReader.close();
                System.err.println("Error from OpenRouter: " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Exception occurred while generating summary.";
        }

        return response.toString();
    }
}
