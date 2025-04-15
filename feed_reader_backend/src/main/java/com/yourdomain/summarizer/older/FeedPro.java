package com.yourdomain.summarizer.older;


import java.util.*;

public class FeedPro {

    static FetchBlogContent fetchblogbontent;


    public static void main(String[] args) {

        fetchData fetchData=new fetchData();
        parseData parseData=new parseData();
        saveInFile saveInFile=new saveInFile();
        GenerateSummary gs=new GenerateSummary();
        FetchContentFromResponse fetchContentFromResponse=new FetchContentFromResponse();
        try {
            // Step 1: Take user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the feed URL (XML format):");
            String feedUrl = scanner.nextLine();

            // Step 2: Fetch XML feed data
            System.out.println("Fetching feed data...");
            String xmlData = fetchData.fetchFeedData(feedUrl);

            // Step 3: Parse XML to extract blog links
            System.out.println("Parsing feed data...");
            List<String> blogLinks =parseData.parseXmlFeed_link(xmlData);
            List<String> blogTitles = parseData.parseXmlFeed_title(xmlData);

            String blogcontent=fetchblogbontent.fetchBlogContent(blogLinks.get(0));
//            System.out.println("content:" + blogcontent);
            String filtered = HtmlTextExtractor.extractText(blogcontent);
            System.out.println("filtered text :==" + filtered);
            String summary=gs.generateSummary(filtered);
            System.out.println("summary :==" + summary);
//            String content=fetchContentFromResponse.fetchContentfromResesponce(summary);
//          System.out.println(content);
//            System.out.println(summary);


//          Step 4: Save blog links to file (with duplicate check)
            System.out.println("Saving blog links to file...");
            saveInFile.saveUniqueToFile(blogLinks,blogTitles, "blogs.txt");

            // Step 5: Notify user
            System.out.println("Blog links saved successfully to blogs.txt!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}
