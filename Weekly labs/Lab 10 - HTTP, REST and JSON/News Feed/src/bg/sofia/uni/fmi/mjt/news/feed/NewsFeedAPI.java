package bg.sofia.uni.fmi.mjt.news.feed;

import bg.sofia.uni.fmi.mjt.news.feed.categories.Category;
import bg.sofia.uni.fmi.mjt.news.feed.countries.Country;
import bg.sofia.uni.fmi.mjt.news.feed.entities.NewsFeedResponseBody;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;

public interface NewsFeedAPI {
    /**
     * Set page number you want to get. The max page is 3 and min is 1 same as 0.
     * By default, the page number is 1.
     * Use this method to page through the results if the total results found is greater than the page size.
     *
     * @param page - The page number you want to see.
     */
    void setPageNumber(int page);

    /**
     * Set page size (the number of articles which will be returned)
     * Default is 20 articles per page (request).
     * The minimum is 0 and maximum is 50 per page
     *
     * @param pageSize - The number of articles you want to appear on the result.
     */
    void setPageSize(int pageSize);

    /**
     * Overloaded function of search feeds with only parameters, the keywords you want to search by.
     * Note if you don't set the page number and page size before calling the method it will use the
     * default values respectively 1 and 20.
     * The page number and page size will be automatically set to their default values after calling this method if you
     * have them set differently.
     *
     * @param keywords - The keywords you want to search for.
     * @return NewsFeedResponseBody - The body from the response.
     * @throws NewsFeedException - This method can throw multiple types of exceptions which are described in the api
     *                           from different types of errors.
     */
    NewsFeedResponseBody searchFeeds(String... keywords) throws NewsFeedException;

    /**
     * Overloaded function of search feeds with parameters the keywords you want to search by and the category.
     * Note if you don't set the page number and page size before calling the method it will use the
     * default values respectively 1 and 20.
     * The page number and page size will be automatically set to their default values after calling this method if you
     * have them set differently.
     *
     * @param category - Enum class for the specified category
     * @param keywords - The keywords you want to search for.
     * @return NewsFeedResponseBody - The body from the response.
     * @throws NewsFeedException - This method can throw multiple types of exceptions which are described in the api
     *                           from different types of errors.
     */
    NewsFeedResponseBody searchFeeds(Category category, String... keywords) throws NewsFeedException;

    /**
     * Overloaded function of search feeds with parameters, the keywords you want to search by and the country.
     * Note if you don't set the page number and page size before calling the method it will use the
     * default values respectively 1 and 20.
     * The page number and page size will be automatically set to their default values after calling this method if you
     * have them set differently.
     *
     * @param country  - Enum class for the specified country.
     * @param keywords - The keywords you want to search for.
     * @return NewsFeedResponseBody - The body from the response.
     * @throws NewsFeedException - This method can throw multiple types of exceptions which are described in the api
     *                           from different types of errors.
     */
    NewsFeedResponseBody searchFeeds(Country country, String... keywords) throws NewsFeedException;

    /**
     * Overloaded function of search feeds with parameters, the keywords you want to search by the category
     * and the country.
     * Note if you don't set the page number and page size before calling the method it will use the
     * default values respectively 1 and 20.
     * The page number and page size will be automatically set to their default values after calling this method if you
     * have them set differently.
     *
     * @param category - Enum class for the specified category
     * @param country  - Enum class for the specified country
     * @param keywords - The keywords you want to search for.
     * @return NewsFeedResponseBody - The body from the response.
     * @throws NewsFeedException - This method can throw multiple types of exceptions which are described in the api
     *                           from different types of errors.
     */
    NewsFeedResponseBody searchFeeds(Category category, Country country, String... keywords) throws NewsFeedException;
}