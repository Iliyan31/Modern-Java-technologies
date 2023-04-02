package bg.sofia.uni.fmi.mjt.news.feed.categories;

public enum Category {
    BUSINESS,
    ENTERTAINMENT,
    GENERAL,
    HEALTH,
    SCIENCE,
    SPORTS,
    TECHNOLOGY;

    public static String getCategoryName(Category category) {
        return category.toString().toLowerCase();
    }
}