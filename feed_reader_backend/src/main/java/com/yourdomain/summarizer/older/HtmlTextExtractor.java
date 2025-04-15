package com.yourdomain.summarizer.older;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class HtmlTextExtractor {

    /**
     * Extracts clean text from raw HTML content.
     *
     * @param htmlContent The raw HTML content as a string.
     * @return The visible, readable text extracted from the HTML.
     */
    public static String extractText(String htmlContent) {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            return "Error: HTML content is empty.";
        }

        try {
            // Parse the HTML
            Document doc = Jsoup.parse(htmlContent);

            // Remove script, style, and noscript tags
            doc.select("script, style, noscript").remove();

            // Extract and return the visible body text
            return doc.body().text();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Failed to extract text from HTML.";
        }
    }
}
