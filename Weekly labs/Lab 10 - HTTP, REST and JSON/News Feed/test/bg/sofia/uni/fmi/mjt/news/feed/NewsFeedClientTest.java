package bg.sofia.uni.fmi.mjt.news.feed;

import bg.sofia.uni.fmi.mjt.news.feed.categories.Category;
import bg.sofia.uni.fmi.mjt.news.feed.countries.Country;
import bg.sofia.uni.fmi.mjt.news.feed.entities.NewsFeed;
import bg.sofia.uni.fmi.mjt.news.feed.entities.NewsFeedResponseBody;
import bg.sofia.uni.fmi.mjt.news.feed.entities.NewsFeedResponseErrorBody;
import bg.sofia.uni.fmi.mjt.news.feed.entities.Source;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageNumberException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageSizeException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.ApiKeyDisabledException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.ApiKeyExhaustedException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.ApiKeyInvalidException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.ApiKeyMissingException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.ParameterInvalidException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.ParametersMissingException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.RateLimitedException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.SourceDoesNotExistException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.SourcesTooManyException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.UnexpectedErrorException;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class NewsFeedClientTest {
    @Mock
    private static HttpClient httpClientMock;
    @Mock
    private static HttpResponse<String> httpNewsFeedResponseMock;
    private static String returnedJson1;
    private static String returnedJson2;
    private static String returnedJson3;
    private static String returnedJson4;
    private static String returnedJson5;
    private static NewsFeedResponseBody responseBody1;
    private static NewsFeedResponseBody responseBody2;
    private static NewsFeedResponseBody responseBody3;
    private static NewsFeedResponseBody responseBody4;
    private static NewsFeedResponseBody responseBody5;

    private static NewsFeedResponseErrorBody responseErrorBody1;
    private static NewsFeedResponseErrorBody responseErrorBody2;
    private static NewsFeedResponseErrorBody responseErrorBody3;
    private static NewsFeedResponseErrorBody responseErrorBody4;
    private static NewsFeedResponseErrorBody responseErrorBody5;
    private static NewsFeedResponseErrorBody responseErrorBody6;
    private static NewsFeedResponseErrorBody responseErrorBody7;
    private static NewsFeedResponseErrorBody responseErrorBody8;
    private static NewsFeedResponseErrorBody responseErrorBody9;
    private static NewsFeedResponseErrorBody responseErrorBody10;
    private static NewsFeedResponseErrorBody responseErrorBody11;

    private static String returnedErrorJson1;
    private static String returnedErrorJson2;
    private static String returnedErrorJson3;
    private static String returnedErrorJson4;
    private static String returnedErrorJson5;
    private static String returnedErrorJson6;
    private static String returnedErrorJson7;
    private static String returnedErrorJson8;
    private static String returnedErrorJson9;
    private static String returnedErrorJson10;
    private static String returnedErrorJson11;

    private static NewsFeedClient newsFeedClient;


    @BeforeAll
    static void setAll() {
        Gson gson = new Gson();

        Source source = new Source(null, "CNET");
        NewsFeed newsFeed = new NewsFeed(source, "Amanda Kooser",
            "Sweeping New Milky Way Portrait Captures More Than 3 Billion Stars - CNET",
            "Behold a \"gargantuan astronomical data tapestry\" of our glorious galaxy.",
            "https://www.cnet.com/science/space/sweeping-new-milky-way-portrait-captures-more-than-3-billion-stars/",
            "https://www.cnet.com/a/img/resize/80a9a5e41cb05ffa1aec0686c4960ce26433d4a6/hub/2023/01/19/4219393c-cbb2-4e32-afd3-403a8e145580/noirlab2301a.jpg?auto=webp&fit=crop&height=630&width=1200",
            "2023-01-22T18:14:00Z",
            "How many stars can you count when you look up into the clear night sky? Not nearly as many as the Dark Energy Camera in Chile. Scientists released a survey of a portion of our home Milky Way galaxy...");
        NewsFeed[] newsFeeds = new NewsFeed[1];
        newsFeeds[0] = newsFeed;

        responseBody1 = new NewsFeedResponseBody("ok", 1, newsFeeds);
        responseBody2 = new NewsFeedResponseBody("ok", 0, new NewsFeed[0]);

        source = new Source(null, "Tradearabia.com");
        newsFeed = new NewsFeed(source, null,
            "Samsung unveils 200-MP image sensor for ultimate high-res - Trade Arabia",
            "Samsung Electronics has introduced its latest 200-megapixel (MP) image sensor, the Isocell HP2, with improved pixel technology and full-well capacity for stunning mobile images in tomorrow’s premium smartphones.",
            "http://tradearabia.com/news/IT_405534.html",
            null,
            "2023-01-22T18:14:00Z",
            "The Samsung Isocell HP2 Samsung unveils 200-MP image sensor for ultimate high-res\\r\\nRIYADH, 1 hours, 53 minutes\\r\\n ago\\r\\nSamsung Electronics has introduced its latest 200-megapixel (MP) image sensor ....");
        newsFeeds = new NewsFeed[1];
        newsFeeds[0] = newsFeed;

        responseBody3 = new NewsFeedResponseBody("ok", 1, newsFeeds);

        source = new Source(null, "Tradearabia.com");
        newsFeed = new NewsFeed(source, null,
            "Samsung unveils 200-MP image sensor for ultimate high-res - Trade Arabia",
            "Samsung Electronics has introduced its latest 200-megapixel (MP) image sensor, the Isocell HP2, with improved pixel technology and full-well capacity for stunning mobile images in tomorrow’s premium smartphones.",
            "http://tradearabia.com/news/IT_405534.html",
            null,
            "2023-01-22T18:14:00Z",
            "The Samsung Isocell HP2 Samsung unveils 200-MP image sensor for ultimate high-res\\r\\nRIYADH, 1 hours, 53 minutes\\r\\n ago\\r\\nSamsung Electronics has introduced its latest 200-megapixel (MP) image sensor ....");
        newsFeeds = new NewsFeed[1];
        newsFeeds[0] = newsFeed;

        responseBody4 = gson.fromJson(
            "{\"status\":\"ok\",\"totalResults\":2,\"articles\":[{\"source\":{\"id\":null,\"name\":\"Sports Illustrated\"},\"author\"" +
                ":\"Zach Koons\",\"title\":\"Snowfall in Forecast for Bengals-Bills Divisional Playoff Game - " +
                "Sports Illustrated\",\"description\":\"The elements could play in a role in the emotional rematch " +
                "between the two teams.\",\"url\":\"https://www.si.com/nfl/2023/01/22/forecast-bengals-bills-divisional-playoff-game-snowfall\",\"urlToImage\":" +
                "\"https://www.si.com/.image/t_share/MTk1MzQ1NDYxODgyNjYwMTU0/bills-snow.jpg\",\"publishedAt\":\"2023-01-22T16:36:21Z\",\"content\":\"For yet another time this year," +
                " inclement weather could impact a Bills home game.\\r\\nSnow is in the forecast in Buffalo for the divisional round playoff game against the Bengals on Sunday afternoon." +
                " Th… [+1246 chars]\"},{\"source\":{\"id\":null,\"name\":\"CBS Sports\"},\"author\":\"Jared Dubin\",\"title\":\"Chiefs vs. Jaguars score, takeaways: Patrick Mahomes shakes " +
                "off ankle injury, leads K.C. to AFC Championship - CBS Sports\",\"description\":\"Kansas City makes the" +
                " conference championship game for the fifth straight season\",\"url\":\"https://www.cbssports.com/nfl/news/chiefs-vs-jaguars-score-takeaways-patrick-mahomes-shakes-off-ankle-injury-leads-kansas-city-afc-title-game/live/\"" +
                ",\"urlToImage\":\"https://sportshub.cbsistatic.com/i/r/2023/01/21/46c9e1cb-502c-4165-840c-af0be7651359/thumbnail/1200x675/417daca455b909e35a417d506495602a/mahomes-jags-us.jpg\",\"publishedAt\":\"2023-01-22T15:41:00Z\",\"content\"" +
                ":\"The Kansas City Chiefs have punched" +
                " their ticket to the AFC Championship Game for the fifth straight season, as Andy Reid's squad defeated Doug Pederson and the Jacksonville Jaguars, 27-20. Patrick M… [+4984 chars]\"}]}",
            NewsFeedResponseBody.class);

        responseBody5 = gson.fromJson(
            "{\"status\":\"ok\",\"totalResults\":1,\"articles\":[{\"source\":{\"id\":null,\"name\":\"Videocardz.com\"}," +
                "\"author\":null,\"title\":\"NVIDIA RTX 4070 Ti dominates weekly GPU sales at German retailer, " +
                "more cards sold than RX7000 & Arc combined - VideoCardz.com\",\"description\":\"Last week NVIDIA" +
                " sold more RTX 4070 Ti cards than all Radeon RX 7000 & Arc Alchemist combined According to the most" +
                " recent sales data from TechEpiphany, NVIDIA has sold over 500 units of GeForce RTX 4070 Ti graphics cards." +
                " In the third week of January, NVIDIA…\",\"url\":\"https://videocardz.com/newz/nvidia-rtx-4070-ti-dominates-weekly-gpu-sales-at-german-retailer-more-cards-sold-than-rx7000-arc-combined\"," +
                "\"urlToImage\":\"https://cdn.videocardz.com/1/2023/01/NVIDIA-RTX-4070TI-SALES-PODIUM-HERO.jpg\"" +
                ",\"publishedAt\":\"2023-01-22T14:16:00Z\",\"content\":\"According to the most recent sales data from TechEpiphany," +
                " NVIDIA has sold over 500 units of GeForce RTX 4070 Ti graphics cards.\\r\\nIn the third week of January, NVIDIA sold 545 models of RTX 4070 Ti. … [+2469 chars]\"}]}",
            NewsFeedResponseBody.class);

        responseErrorBody1 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"apiKeyDisabled\",\"message\":\"Your API key has been disabled\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody2 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"apiKeyExhausted\",\"message\":\"Your API key has no more requests available.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody3 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"apiKeyInvalid\",\"message\":\"Your API key hasn't been entered correctly. Double check it and try again.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody4 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"apiKeyMissing\",\"message\":\"Your API key is missing from the request.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody5 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"parameterInvalid\",\"message\":\"You've included a parameter in your request which is currently not supported.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody6 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"parametersMissing\",\"message\":\"Required parameters are missing from the request and it cannot be completed.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody7 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"rateLimited\",\"message\":\"You have been rate limited. Back off for a while before trying the request again.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody8 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"sourcesTooMany\",\"message\":\"You have requested too many sources in a single request. Try splitting the request into 2 smaller requests.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody9 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"sourceDoesNotExist\",\"message\":\"You have requested a source which does not exist.\"}",
            NewsFeedResponseErrorBody.class);
        responseErrorBody10 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"unexpectedError\",\"message\":\"This shouldn't happen, and if it does then it's our fault, not yours. Try the request again shortly.\"}",
            NewsFeedResponseErrorBody.class);

        responseErrorBody11 = gson.fromJson(
            "{\"status\":\"error\",\"code\":\"a\",\"message\":\"This shouldn't happen, and if it does then it's our fault, not yours. Try the request again shortly.\"}",
            NewsFeedResponseErrorBody.class);

        returnedJson1 = gson.toJson(responseBody1);
        returnedJson2 = gson.toJson(responseBody2);
        returnedJson3 = gson.toJson(responseBody3);
        returnedJson4 = gson.toJson(responseBody4);
        returnedJson5 = gson.toJson(responseBody5);

        returnedErrorJson1 = gson.toJson(responseErrorBody1);
        returnedErrorJson2 = gson.toJson(responseErrorBody2);
        returnedErrorJson3 = gson.toJson(responseErrorBody3);
        returnedErrorJson4 = gson.toJson(responseErrorBody4);
        returnedErrorJson5 = gson.toJson(responseErrorBody5);
        returnedErrorJson6 = gson.toJson(responseErrorBody6);
        returnedErrorJson7 = gson.toJson(responseErrorBody7);
        returnedErrorJson8 = gson.toJson(responseErrorBody8);
        returnedErrorJson9 = gson.toJson(responseErrorBody9);
        returnedErrorJson10 = gson.toJson(responseErrorBody10);
        returnedErrorJson11 = gson.toJson(responseErrorBody11);
    }


    @BeforeAll
    static void setUp() throws IOException, InterruptedException {
        newsFeedClient = NewsFeedClient.getNewsFeedClient();
        httpClientMock = Mockito.mock(HttpClient.class);
        httpNewsFeedResponseMock = Mockito.mock(HttpResponse.class);

        when(httpClientMock.send(Mockito.any(HttpRequest.class),
            ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
            .thenReturn(httpNewsFeedResponseMock);

        newsFeedClient.setNewsFeedHttpClient(httpClientMock);
    }


    @Test
    void testSetPageNumberForNegativeNumber() {
        assertThrows(PageNumberException.class, () -> newsFeedClient.setPageNumber(-1),
            "The page number cannot be negative!");
    }

    @Test
    void testSetPageNumberForGreaterThan3Number() {
        assertThrows(PageNumberException.class, () -> newsFeedClient.setPageNumber(4),
            "The page number cannot be above 3!");
    }

    @Test
    void testSetPageSizeNumberForNegativeNumber() {
        assertThrows(PageSizeException.class, () -> newsFeedClient.setPageSize(-1),
            "The page size number cannot be negative!");
    }

    @Test
    void testSetPageSizeNumberForGreaterThan50Number() {
        assertThrows(PageSizeException.class, () -> newsFeedClient.setPageSize(51),
            "The page size number cannot be above 50!");
    }

    @Test
    void testSearchKeywordsWithNullArray() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(null),
            "The keywords array cannot be null!");
    }

    @Test
    void testSearchKeywordsWithNullWordInArray() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds("a", null),
            "The keywords array cannot have null words!");
    }

    @Test
    void testSearchKeywordsWithNoWords() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(),
            "The keywords array cannot have no words!");
    }

    @Test
    void testSearchKeywordsAndCategoryWithNoWords() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(Category.TECHNOLOGY),
            "The keywords array cannot have no words!");
    }

    @Test
    void testSearchKeywordsAndCategoryWithNullArray() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(Category.TECHNOLOGY, null),
            "The keywords array cannot be null!");
    }

    @Test
    void testSearchKeywordsAndCategoryWithNullWordInArray() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(Category.TECHNOLOGY, "a", null),
            "The keywords array cannot have null words!");
    }

    @Test
    void testSearchKeywordsAndCategoryWithCategoryNull() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds((Category) null, "a"),
            "Null category is not allowed!");
    }

    @Test
    void testSearchKeywordsAndCountryWithNoWords() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(Country.BG),
            "The keywords array cannot have no words!");
    }

    @Test
    void testSearchKeywordsAndCountryWithNullArray() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(Country.BG, null),
            "The keywords array cannot be null!");
    }

    @Test
    void testSearchKeywordsAndCountryWithNullWordInArray() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(Country.BG, "a", null),
            "The keywords array cannot have null words!");
    }

    @Test
    void testSearchKeywordsAndCountryWithCategoryNull() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds((Country) null, "a"),
            "Null country is not allowed!");
    }


    @Test
    void testSearchKeywordsCategoryAndCountryWithNoWords() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds(Category.TECHNOLOGY, Country.BG),
            "The keywords array cannot have no words!");
    }

    @Test
    void testSearchKeywordsCategoryAndCountryWithNullArray() {
        assertThrows(IllegalArgumentException.class,
            () -> newsFeedClient.searchFeeds(Category.TECHNOLOGY, Country.BG, null),
            "The keywords array cannot be null!");
    }

    @Test
    void testSearchKeywordsCategoryAndCountryWithNullWordInArray() {
        assertThrows(IllegalArgumentException.class,
            () -> newsFeedClient.searchFeeds(Category.TECHNOLOGY, Country.BG, "a", null),
            "The keywords array cannot have null words!");
    }

    @Test
    void testSearchKeywordsCategoryAndCountryWithCategoryNull() {
        assertThrows(IllegalArgumentException.class, () -> newsFeedClient.searchFeeds((Category) null, Country.BG, "a"),
            "Null category is not allowed!");
    }

    @Test
    void testSearchKeywordsCategoryAndCountryWithCountryNull() {
        assertThrows(IllegalArgumentException.class,
            () -> newsFeedClient.searchFeeds(Category.TECHNOLOGY, (Country) null, "a"),
            "Null category is not allowed!");
    }


    @Test
    void testSearchFeedByOnlyOneKeyword() throws NewsFeedException {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedJson1);

        NewsFeedResponseBody responseBodyFromMock = newsFeedClient.searchFeeds("CNET");

        assertEquals(responseBody1.totalResults(), responseBodyFromMock.totalResults(),
            "The system should return correct response body when status code is 200!");
    }

    @Test
    void testSearchFeedByOnlyMoreKeywords() throws NewsFeedException {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedJson2);

        NewsFeedResponseBody responseBodyFromMock = newsFeedClient.searchFeeds("CNET", "q");

        assertEquals(responseBody2.totalResults(), responseBodyFromMock.totalResults(),
            "The system should return correct response body when status code is 200!");
    }

    @Test
    void testSearchFeedByKeywordsAndCategory() throws NewsFeedException {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedJson3);

        NewsFeedResponseBody responseBodyFromMock = newsFeedClient.searchFeeds(Category.TECHNOLOGY, "technology");

        assertEquals(responseBody3.articles()[0].author(), responseBodyFromMock.articles()[0].author(),
            "The system should return correct response body when status code is 200!");
    }

    @Test
    void testSearchFeedByKeywordsAndCountry() throws NewsFeedException {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedJson4);

        NewsFeedResponseBody responseBodyFromMock = newsFeedClient.searchFeeds(Country.US, "game");

        assertEquals(responseBody4.articles()[1].author(), responseBodyFromMock.articles()[1].author(),
            "The system should return correct response body when status code is 200!");
    }

    @Test
    void testSearchFeedByKeywordsCategoryAndCountry() throws NewsFeedException {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedJson5);

        NewsFeedResponseBody responseBodyFromMock =
            newsFeedClient.searchFeeds(Category.TECHNOLOGY, Country.US, "graphics");

        assertEquals(responseBody5.articles()[0].content(), responseBodyFromMock.articles()[0].content(),
            "The system should return correct response body when status code is 200!");
    }

    @Test
    void testThrowingApiKeyDisabledException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson1);

        assertThrows(ApiKeyDisabledException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw ApiKeyDisabledException!");
    }

    @Test
    void testThrowingApiKeyExhaustedException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(429);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson2);

        assertThrows(ApiKeyExhaustedException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw ApiKeyExhaustedException!");
    }

    @Test
    void testThrowingApiKeyInvalidException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson3);

        assertThrows(ApiKeyInvalidException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw ApiKeyInvalidException!");
    }

    @Test
    void testThrowingApiKeyMissingException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_UNAUTHORIZED);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson4);

        assertThrows(ApiKeyMissingException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw ApiKeyMissingException!");
    }

    @Test
    void testThrowingParameterInvalidException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson5);

        assertThrows(ParameterInvalidException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw ParameterInvalidException!");
    }

    @Test
    void testThrowingParametersMissingException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson6);

        assertThrows(ParametersMissingException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw ParametersMissingException!");
    }

    @Test
    void testThrowingRateLimitedException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(429);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson7);

        assertThrows(RateLimitedException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw RateLimitedException!");
    }

    @Test
    void testThrowingSourcesTooManyException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson8);

        assertThrows(SourcesTooManyException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw SourcesTooManyException!");
    }

    @Test
    void testThrowingSourceDoesNotExistException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson9);

        assertThrows(SourceDoesNotExistException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw SourceDoesNotExistException!");
    }

    @Test
    void testThrowingUnexpectedErrorException() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(500);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson10);

        assertThrows(UnexpectedErrorException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw UnexpectedErrorException!");
    }

    @Test
    void testThrowingNewsFeedExceptionFromNoMatchingCode() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(500);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson11);

        assertThrows(NewsFeedException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw NewsFeedException when the code is not matching any of the documented!");
    }

    @Test
    void testThrowingNewsFeedExceptionFromDifferentHttpError() {
        when(httpNewsFeedResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
        when(httpNewsFeedResponseMock.body()).thenReturn(returnedErrorJson11);

        assertThrows(NewsFeedException.class, () -> newsFeedClient.searchFeeds("CNET", "q"),
            "The system should correctly throw NewsFeedException when the http error code is not matching any of the documented!");
    }
}