package bg.sofia.uni.fmi.mjt.news.feed.entities;

public record NewsFeed(Source source, String author, String title, String description, String url, String urlToImage,
                       String publishedAt, String content) {
}