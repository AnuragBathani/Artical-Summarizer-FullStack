package com.yourdomain.summarizer.service;

import com.yourdomain.summarizer.older.*;
import org.springframework.stereotype.Service;
import com.yourdomain.summarizer.model.Article;

import java.util.*;

@Service
public class SummarizeService {
    // Integrate your existing logic here
    public List<Article> summarizeFeed(String feedUrl) throws Exception {
        fetchData fetchData = new fetchData();
        parseData parseData = new parseData();
        GenerateSummary gs = new GenerateSummary();
        FetchContentFromResponse fetchContentFromResponse = new FetchContentFromResponse();

        // Step 1: Fetch the RSS feed XML
        String xml = fetchData.fetchFeedData(feedUrl);
        // Step 2: Parse the XML to get article links and titles
        // We'll assume parseData has a method to parse XML and return links/titles
        // You may need to adapt this to your actual parseData API
        List<String> blogLinks = parseData.parseXmlFeed_link(xml);
        List<String> blogTitles = parseData.parseXmlFeed_title(xml);

        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String link = blogLinks.get(i);
            String title = blogTitles.size() > i ? blogTitles.get(i) : "";
            // Step 3: Fetch content for each article (if needed)
             String content = FetchBlogContent.fetchBlogContent(link); // If needed
            String filtered = HtmlTextExtractor.extractText(content);
            System.out.println(" filtered data ==>" + filtered);
            // Step 4: Generate summary
            String summary = gs.generateSummary(filtered); // Or pass content if needed
//            System.out.println("this is summary:-->" + summaryResponse);
//            String summary = fetchContentFromResponse.fetchContentfromResesponce(summaryResponse);
            articles.add(new Article(title, link, summary));
        }
        return articles;
    }
}
