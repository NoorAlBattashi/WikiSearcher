package com.wikisearcher;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * The RequestManager class handles HTTP requests to the Wikipedia API and
 * <p>
 * returns search results based on user input.
 */
public class RequestManager {
    /**
     * Creates a new instance of the RequestManager class.
     */
    public RequestManager() {

    }

    /**
     * Sends an HTTP request to the Wikipedia API with the user input as the search query,
     * and returns up to three search results as title and snippet pairs.
     * If no results are found, prints a message indicating so.
     *
     * @param userInput the user's search query as a string
     * @throws IOException if an I/O error occurs while sending the request
     */
    public void userRequest(String userInput) throws IOException {
        String encodedUserInput = URLEncoder.encode(userInput);
        String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=" + encodedUserInput;

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            JsonObject json = new Gson().fromJson(responseBody, JsonObject.class);
            JsonObject query = json.getAsJsonObject("query");
            JsonArray searchResults = query.getAsJsonArray("search");

            if (searchResults.isEmpty()) {
                System.out.println("No results found for '" + userInput + "'");
            } else {
                int maxResults = Math.min(searchResults.size(), 3);
                for (int i = 0; i < maxResults; i++) {
                    JsonObject result = searchResults.get(i).getAsJsonObject();
                    String title = result.get("title").getAsString();
                    String snippet = result.get("snippet").getAsString();
                    System.out.println(title);
                    System.out.println(snippet);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

    }

}
