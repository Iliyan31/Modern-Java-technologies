package bg.sofia.uni.fmi.mjt.news.feed.validators;

import bg.sofia.uni.fmi.mjt.news.feed.categories.Category;
import bg.sofia.uni.fmi.mjt.news.feed.countries.Country;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageNumberException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageSizeException;

public abstract class NewsFeedClientValidator {
    protected void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("The category cannot be null!");
        }
    }

    protected void validateCountry(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("The country cannot be null!");
        }
    }

    protected void validateKeywordsArray(String... keywords) {
        if (keywords == null) {
            throw new IllegalArgumentException("The keyword array cannot be null!");
        }
    }

    protected void validateKeywordsInArray(String... keywords) {
        for (String keyword : keywords) {
            validateKeywordString(keyword);
        }
    }

    protected static void validateKeywordsArrayLength(String... keywords) {
        final int minimumArrayLength = 1;

        if (keywords.length < minimumArrayLength) {
            throw new IllegalArgumentException("You cannot search without a keyword!");
        }
    }

    protected void validateKeywordString(String keyword) {
        if (keyword == null || keyword.isEmpty() || keyword.isBlank()) {
            throw new IllegalArgumentException("The keyword cannot be null, empty ot blank!");
        }
    }

    protected static void validatePageNumber(int page) throws PageNumberException {
        final int maxPageNumber = 3;
        final int minPageNumber = 0;

        if (page < minPageNumber || page > maxPageNumber) {
            throw new PageNumberException("The page number cannot be below 0 or higher than 3!");
        }
    }

    protected static void validatePageSizeNumber(int page) throws PageSizeException {
        final int maxPageSize = 50;
        final int minPageSize = 0;

        if (page < minPageSize || page > maxPageSize) {
            throw new PageSizeException("The page size cannot be below 0 or higher than 50!");
        }
    }
}