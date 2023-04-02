package bg.sofia.uni.fmi.mjt.news.feed.queries;

import bg.sofia.uni.fmi.mjt.news.feed.parameters.QueryParameters;
import bg.sofia.uni.fmi.mjt.news.feed.validators.QueryValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query extends QueryValidator {
    private final List<String> keywords;
    private final String category;
    private final String country;
    private final int page;
    private final int pageSize;
    private final String apiKey;

    public String getQueryString() {
        StringBuilder query = new StringBuilder();

        addKeywordsToQuery(query);
        addCategoryToQuery(query);
        addCountryToQuery(query);
        addPageNumberToQuery(query);
        addPageSizeToQuery(query);
        addApiKeyToQuery(query);

        return query.toString();
    }

    private void addKeywordsToQuery(StringBuilder query) {
        final String equals = "=";
        final String plus = "+";

        int keywordsSize = keywords.size();
        int finalKeywordElementIndex = keywordsSize - 1;

        if (keywordsSize > 0) {
            query.append(QueryParameters.getQueryParamsInStringFormat(QueryParameters.Q))
                .append(equals);
        }
        for (int i = 0; i < keywordsSize; i++) {
            query.append(keywords.get(i));

            if (keywordsSize > 1 && i != finalKeywordElementIndex) {
                query.append(plus);
            }
        }
    }

    private void addCategoryToQuery(StringBuilder query) {
        final String equals = "=";
        final String and = "&";

        if (category != null) {
            query.append(and)
                .append(QueryParameters.getQueryParamsInStringFormat(QueryParameters.CATEGORY))
                .append(equals)
                .append(category);
        }
    }

    private void addCountryToQuery(StringBuilder query) {
        final String equals = "=";
        final String and = "&";

        if (country != null) {
            query.append(and)
                .append(QueryParameters.getQueryParamsInStringFormat(QueryParameters.COUNTRY))
                .append(equals)
                .append(country);
        }
    }

    private void addApiKeyToQuery(StringBuilder query) {
        final String equals = "=";
        final String and = "&";

        query.append(and)
            .append(QueryParameters.getQueryParamsInStringFormat(QueryParameters.APIKEY))
            .append(equals)
            .append(apiKey);
    }

    private void addPageNumberToQuery(StringBuilder query) {
        final String equals = "=";
        final String and = "&";

        final int defaultPageNumber = Integer.MIN_VALUE;

        if (page != defaultPageNumber) {
            query.append(and)
                .append(QueryParameters.getQueryParamsInStringFormat(QueryParameters.PAGE))
                .append(equals)
                .append(page);
        }
    }

    private void addPageSizeToQuery(StringBuilder query) {
        final String equals = "=";
        final String and = "&";

        final int defaultPageSize = Integer.MIN_VALUE;

        if (pageSize != defaultPageSize) {
            query.append(and)
                .append(QueryParameters.getQueryParamsInStringFormat(QueryParameters.PAGE_SIZE))
                .append(equals)
                .append(pageSize);
        }
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public static QueryBuilder builder(String apiKey, String... keywords) {
        return new QueryBuilder(apiKey, keywords);
    }

    private Query(QueryBuilder queryBuilder) {
        this.keywords = queryBuilder.keywords;
        this.category = queryBuilder.category;
        this.country = queryBuilder.country;
        this.page = queryBuilder.page;
        this.pageSize = queryBuilder.pageSize;
        this.apiKey = queryBuilder.apiKey;
    }

    public static class QueryBuilder {
        private final List<String> keywords;
        private final String apiKey;
        private String category;
        private String country;
        private int page;
        private int pageSize;

        private QueryBuilder(String apiKey, String... keywords) {
            validateKeywordsArray(keywords);
            validateKeywordsArrayLength(keywords);
            validateKeywords(keywords);
            validateApiKey(apiKey);

            final int defaultPageNumber = Integer.MIN_VALUE;
            final int defaultPageSize = Integer.MIN_VALUE;

            this.keywords = new ArrayList<>();
            this.keywords.addAll(Arrays.stream(keywords).toList());
            this.page = defaultPageNumber;
            this.pageSize = defaultPageSize;
            this.apiKey = apiKey;
        }

        public QueryBuilder setKeyword(String keyword) {
            validateKeyword(keyword);

            this.keywords.add(keyword);
            return this;
        }

        public QueryBuilder setCategory(String category) {
            validateCategory(category);

            this.category = category;
            return this;
        }

        public QueryBuilder setCountry(String country) {
            validateCountry(country);

            this.country = country;
            return this;
        }

        public QueryBuilder setPage(int page) {
            validatePageNumber(page);

            this.page = page;
            return this;
        }

        public QueryBuilder setPageSize(int pageSize) {
            validatePageSizeNumber(pageSize);

            this.pageSize = pageSize;
            return this;
        }

        public Query build() {
            return new Query(this);
        }
    }
}