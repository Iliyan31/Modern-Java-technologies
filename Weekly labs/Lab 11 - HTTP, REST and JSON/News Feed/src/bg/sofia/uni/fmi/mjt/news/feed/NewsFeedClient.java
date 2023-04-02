package bg.sofia.uni.fmi.mjt.news.feed;

import bg.sofia.uni.fmi.mjt.news.feed.categories.Category;
import bg.sofia.uni.fmi.mjt.news.feed.countries.Country;
import bg.sofia.uni.fmi.mjt.news.feed.entities.NewsFeedResponseBody;
import bg.sofia.uni.fmi.mjt.news.feed.entities.NewsFeedResponseErrorBody;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties.SystemExceptionsHandler;
import bg.sofia.uni.fmi.mjt.news.feed.queries.Query;
import bg.sofia.uni.fmi.mjt.news.feed.validators.NewsFeedClientValidator;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsFeedClient extends NewsFeedClientValidator implements NewsFeedAPI {
    private static final String API_KEY = "fed17db9c5d9495ca1aa1b029e7b2cb9";
    private static final String API_PROTOCOL = "http";
    private static final String DOMAIN = "newsapi.org";
    private static final String TOP_HEADLINES_ENDPOINT = "/v2/top-headlines";
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final NewsFeedClient NEWS_FEED_CLIENT_INSTANCE = new NewsFeedClient();
    private static final Gson GSON = new Gson();
    private HttpClient newsFeedHttpClient;
    private int page;
    private int pageSize;

    private NewsFeedClient() {
        this.newsFeedHttpClient = HttpClient.newBuilder().build();
        this.page = DEFAULT_PAGE_NUMBER;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public static NewsFeedClient getNewsFeedClient() {
        return NEWS_FEED_CLIENT_INSTANCE;
    }

    @Override
    public NewsFeedResponseBody searchFeeds(String... keywords) throws NewsFeedException {
        validateKeywordsArray(keywords);
        validateKeywordsArrayLength(keywords);
        validateKeywordsInArray(keywords);

        Query query = Query.builder(API_KEY, keywords)
            .setPage(page)
            .setPageSize(pageSize)
            .build();

        setPageNumber(DEFAULT_PAGE_NUMBER);
        setPageSize(DEFAULT_PAGE_SIZE);

        String queryParams = query.getQueryString();
        HttpResponse<String> httpResponse = getResponseFromQuery(queryParams);

        return getResponseBody(httpResponse);
    }

    @Override
    public NewsFeedResponseBody searchFeeds(Category category, String... keywords) throws NewsFeedException {
        validateKeywordsArray(keywords);
        validateKeywordsArrayLength(keywords);
        validateKeywordsInArray(keywords);
        validateCategory(category);

        Query query = Query.builder(API_KEY, keywords)
            .setCategory(Category.getCategoryName(category))
            .setPage(page)
            .setPageSize(pageSize)
            .build();

        setPageNumber(DEFAULT_PAGE_NUMBER);
        setPageSize(DEFAULT_PAGE_SIZE);

        String queryParams = query.getQueryString();
        HttpResponse<String> httpResponse = getResponseFromQuery(queryParams);

        return getResponseBody(httpResponse);
    }

    @Override
    public NewsFeedResponseBody searchFeeds(Country country, String... keywords) throws NewsFeedException {
        validateKeywordsArray(keywords);
        validateKeywordsArrayLength(keywords);
        validateKeywordsInArray(keywords);
        validateCountry(country);

        Query query = Query.builder(API_KEY, keywords)
            .setCountry(Country.getCountry(country))
            .setPage(page)
            .setPageSize(pageSize)
            .build();

        setPageNumber(DEFAULT_PAGE_NUMBER);
        setPageSize(DEFAULT_PAGE_SIZE);

        String queryParams = query.getQueryString();
        HttpResponse<String> httpResponse = getResponseFromQuery(queryParams);

        return getResponseBody(httpResponse);
    }

    @Override
    public NewsFeedResponseBody searchFeeds(Category category, Country country, String... keywords)
        throws NewsFeedException {
        validateKeywordsArray(keywords);
        validateKeywordsArrayLength(keywords);
        validateKeywordsInArray(keywords);
        validateCategory(category);
        validateCountry(country);

        Query query = Query.builder(API_KEY, keywords)
            .setCategory(Category.getCategoryName(category))
            .setCountry(Country.getCountry(country))
            .setPage(page)
            .setPageSize(pageSize)
            .build();

        setPageNumber(DEFAULT_PAGE_NUMBER);
        setPageSize(DEFAULT_PAGE_SIZE);

        String queryParams = query.getQueryString();
        HttpResponse<String> httpResponse = getResponseFromQuery(queryParams);

        return getResponseBody(httpResponse);
    }

    @Override
    public void setPageNumber(int page) {
        validatePageNumber(page);

        this.page = page;
    }

    @Override
    public void setPageSize(int pageSize) {
        validatePageSizeNumber(pageSize);

        this.pageSize = pageSize;
    }

    /**
     * This is only for testing purposes
     */
    public void setNewsFeedHttpClient(HttpClient httpClient) {
        newsFeedHttpClient = httpClient;
    }

    private URI createURI(String query) throws URISyntaxException {
        return new URI(API_PROTOCOL, DOMAIN, TOP_HEADLINES_ENDPOINT, query, null);
    }

    private HttpResponse<String> getResponseFromQuery(String query) throws NewsFeedException {
        try {
            URI uri = createURI(query);
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri).build();

            return newsFeedHttpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new NewsFeedException("We could not receive a the news feed you search for", e);
        }
    }

    private NewsFeedResponseBody getResponseBody(HttpResponse<String> httpResponse) throws NewsFeedException {
        final int httpTooManyRequestsStatusCode = 429;
        final int httpServerError = 500;

        if (httpResponse.statusCode() == HttpURLConnection.HTTP_OK) {
            return getByRequestBodyStatusCode(httpResponse);
        } else if (httpResponse.statusCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
            throwCorrectPropertiesHttpException(httpResponse);
        } else if (httpResponse.statusCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            throwCorrectPropertiesHttpException(httpResponse);
        } else if (httpResponse.statusCode() == httpTooManyRequestsStatusCode) {
            throwCorrectPropertiesHttpException(httpResponse);
        } else if (httpResponse.statusCode() == httpServerError) {
            throwCorrectPropertiesHttpException(httpResponse);
        }

        throw new NewsFeedException("There was unidentified cause of error!");
    }

    private NewsFeedResponseBody getByRequestBodyStatusCode(HttpResponse<String> httpResponse) {
        return GSON.fromJson(httpResponse.body(), NewsFeedResponseBody.class);
    }

    private NewsFeedResponseErrorBody getByErrorRequestBody(HttpResponse<String> httpResponse) {
        return GSON.fromJson(httpResponse.body(), NewsFeedResponseErrorBody.class);
    }

    private void throwCorrectPropertiesHttpException(HttpResponse<String> httpResponse) throws NewsFeedException {
        NewsFeedResponseErrorBody newsFeedResponseErrorBody = getByErrorRequestBody(httpResponse);

        SystemExceptionsHandler systemExceptionsHandler = new SystemExceptionsHandler(newsFeedResponseErrorBody);
        systemExceptionsHandler.throwCorrectSystemException();
    }
}