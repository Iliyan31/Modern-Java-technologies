package bg.sofia.uni.fmi.mjt.news.feed.parameters;

public enum QueryParameters {
    APIKEY,
    Q,
    COUNTRY,
    CATEGORY,
    PAGE,
    PAGE_SIZE;

    public static String getQueryParamsInStringFormat(QueryParameters parameters) {
        if (parameters == QueryParameters.APIKEY) {
            return "apiKey";
        } else if (parameters == QueryParameters.PAGE_SIZE) {
            return "pageSize";
        }

        return parameters.toString().toLowerCase();
    }
}