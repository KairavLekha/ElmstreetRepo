package Backend.working;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class ChatGPTAPI {

    private static final String API_KEY = "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"; // Replace with your OpenAI API key

    public ChatGPTAPI() {
    }

    public String GenQuote() {
        // Create a request body for the API call
        String requestBody = "{\n"
                + "  \"model\": \"gpt-4o-mini\",\n"
                + "  \"messages\": [\n"
                + "    {\n"
                + "      \"role\": \"user\",\n"
                + "      \"content\": \"Give me a motivational quote.\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"temperature\": 1.5,\n"
                + "  \"max_tokens\": 50,\n"
                + "  \"top_p\": 1,\n"
                + "  \"frequency_penalty\": 0,\n"
                + "  \"presence_penalty\": 0\n"
                + "}";

        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response to get the generated quote
            String responseBody = response.body();
            // Extract the quote from the response (assuming the structure is correct)
            return parseQuote(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "An error occurred";
    }

    // A simple method to extract the quote from the JSON response manually
   private String parseQuote(String responseBody) {
    // The response is in JSON format with choices -> message -> content
           JSONObject jsonResponse = new JSONObject(responseBody);

        // Extract the quote from the choices -> message -> content
        String quote = jsonResponse
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        return quote;
}


}
