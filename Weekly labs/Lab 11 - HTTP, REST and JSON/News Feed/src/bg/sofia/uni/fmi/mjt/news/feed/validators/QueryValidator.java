package bg.sofia.uni.fmi.mjt.news.feed.validators;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageNumberException;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.page.PageSizeException;

public abstract class QueryValidator {
    protected static void validateKeywordsArrayLength(String... keywords) {
        final int minimumArrayLength = 1;

        if (keywords.length < minimumArrayLength) {
            throw new IllegalArgumentException("You cannot search without a keyword!");
        }
    }

    protected static void validateKeywordsArray(String... keywords) {
        if (keywords == null) {
            throw new IllegalArgumentException("The keyword array cannot be null!");
        }
    }

    protected static void validateKeywords(String... keywords) {
        for (String keyword : keywords) {
            validateKeyword(keyword);
        }
    }

    protected static void validateKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty() || keyword.isBlank()) {
            throw new IllegalArgumentException("The string keyword cannot be null, empty or blank!");
        }
    }

    protected static void validateApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty() || apiKey.isBlank()) {
            throw new IllegalArgumentException("The apiKey cannot be null, empty or blank!");
        }
    }

    protected static void validateCategory(String category) {
        if (category == null || category.isEmpty() || category.isBlank()) {
            throw new IllegalArgumentException("The category cannot be null, empty or blank!");
        }
    }

    protected static void validateCountry(String country) {
        if (country == null || country.isEmpty() || country.isBlank()) {
            throw new IllegalArgumentException("The country cannot be null, empty or blank!");
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