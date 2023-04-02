package bg.sofia.uni.fmi.mjt.news.feed.entities;

public record NewsFeedResponseBody(String status, int totalResults, NewsFeed[] articles) {
}