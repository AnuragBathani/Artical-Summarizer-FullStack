package com.yourdomain.summarizer.older;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

public class FetchContentFromResponse {

    public String fetchContentfromResesponce(String response) {
        String content = null;

        try {
            System.out.println("Raw Response: " + response);

            // Parse the JSON string
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            // Check if "choices" exists and is not null
            if (!jsonObject.has("choices") || jsonObject.get("choices").isJsonNull()) {
                System.err.println("Error: 'choices' field is missing or null in the response.");
                return "Error: 'choices' field is missing.";
            }

            JsonArray choices = jsonObject.getAsJsonArray("choices");

            // Check if the array is not empty
            if (choices.size() == 0) {
                System.err.println("Error: 'choices' array is empty.");
                return "Error: 'choices' array is empty.";
            }

            JsonObject firstChoice = choices.get(0).getAsJsonObject();

            // Check if "message" exists
            if (!firstChoice.has("message") || firstChoice.get("message").isJsonNull()) {
                System.err.println("Error: 'message' object is missing in 'choices[0]'.");
                return "Error: 'message' object is missing.";
            }

            JsonObject message = firstChoice.getAsJsonObject("message");

            // Check if "content" exists
            if (!message.has("content") || message.get("content").isJsonNull()) {
                System.err.println("Error: 'content' field is missing in 'message'.");
                return "Error: 'content' field is missing.";
            }

            // Extract the content
            content = message.get("content").getAsString();
            System.out.println("Extracted Content: " + content);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Failed to parse response JSON.";
        }

        return content;
    }
}
